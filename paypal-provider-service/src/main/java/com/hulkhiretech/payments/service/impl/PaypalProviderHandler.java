package com.hulkhiretech.payments.service.impl;

import com.hulkhiretech.payments.utils.JsonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.hulkhiretech.payments.constants.Constants;
import com.hulkhiretech.payments.constants.ErrorCodeEnum;
import com.hulkhiretech.payments.constants.PaypalStatusEnum;
import com.hulkhiretech.payments.constants.TxnStatusEnum;
import com.hulkhiretech.payments.dao.interfaces.TransactionDAO;
import com.hulkhiretech.payments.dto.TransactionDTO;
import com.hulkhiretech.payments.http.HttpRequest;
import com.hulkhiretech.payments.http.HttpServiceEngine;
import com.hulkhiretech.payments.paypalprovider.PPOrder;
import com.hulkhiretech.payments.service.helper.PPCaptureOrderHelper;
import com.hulkhiretech.payments.service.helper.PPGetOrderHelper;
import com.hulkhiretech.payments.service.interfaces.ProviderHandler;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaypalProviderHandler implements ProviderHandler {

    private final HttpServiceEngine httpServiceEngine;

    private final JsonUtils jsonUtils;

    private final PPGetOrderHelper ppGetOrderHelper;

    private final PPCaptureOrderHelper ppCaptureOrderHelper;

    private final TransactionDAO transactionDAO;

    /**
     *  1. For each of the payment, we need to call Paypal GetOrder API
     PaypalStatus: PAYER_ACTION_REQUIRED
     PaypalStatus: APPROVED
     PaypalStatus: COMPLETED
     2. PaypalStatus: PAYER_ACTION_REQUIRED
     Then no change at our end. Just increment retry attempt	(1)

     3. PaypalStatus: APPROVED
     Call CaptureAPI
     SUCCESS

     4. PaypalStatus: COMPLETED
     Update our txn as SUCCESS
     */
    @Override
    public void reconTransaction(TransactionDTO txn) {
        log.info("PaypalProviderHandler.reconTransaction() called txn: {}", txn);

        txn.setRetryCount(txn.getRetryCount() + 1);
        String initialTxnStatus = txn.getTxnStatus();

        boolean isExceptionWileProcessing = false;

        try {
            PPOrder successObj = getOrderFromPP(txn);
            log.info("PaypalProviderHandler.reconTransaction() - " + "successObj: {}", successObj);

            PaypalStatusEnum statusEnum = PaypalStatusEnum.fromString(
                    successObj.getPaypalStatus());

            switch (statusEnum) {
                case PAYER_ACTION_REQUIRED:
                    log.info("PaypalProviderHandler.reconTransaction() - " + "PaypalStatus: PAYER_ACTION_REQUIRED");
                    // NO ACTION
                    break;

                case APPROVED:
                    log.info("PaypalProviderHandler.reconTransaction() - " + "PaypalStatus: APPROVED");
                    // Call CaptureAPI
                    PPOrder captureRes = ppCaptureOrder(txn);
                    if(captureRes.getPaypalStatus().equals(
                            PaypalStatusEnum.COMPLETED.getName())) {
                        // If CaptureAPI is success, then update our txn as SUCCESS
                        txn.setTxnStatus(TxnStatusEnum.SUCCESS.getName());
                    } else {
                        // If CaptureAPI is failed, then update our txn as FAILED
                        log.error("PaypalProviderHandler.reconTransaction() - " + "CaptureAPI failed, paypal status NOT COMPLETED");
                    }
                    break;

                case COMPLETED:
                    log.info("PaypalProviderHandler.reconTransaction() - " + "PaypalStatus: COMPLETED");
                    // Update our txn as SUCCESS
                    txn.setTxnStatus(TxnStatusEnum.SUCCESS.getName());
                    break;

                default:
                    log.error("PaypalProviderHandler.reconTransaction() - " + "Unknown PaypalStatus: {}", statusEnum);
            }

        } catch (Exception e) {
            log.error("PaypalProviderHandler.reconTransaction() - " + "Exception: {}", e);
            isExceptionWileProcessing = true;
        }


        // if initialTxnStatus is not equal to txn.getTxnStatus(), then call transactionDAO.updateTransactionForRecon()
        if (!initialTxnStatus.equals(txn.getTxnStatus())) {
            log.info("PaypalProviderHandler.reconTransaction() - " + "initialTxnStatus: {}, txn.getTxnStatus(): {}",
                    initialTxnStatus, txn.getTxnStatus());
            transactionDAO.updateTransactionForRecon(txn);
            return;
        }

        // if txn.getRetryCount() >= 3, then update txn as FAILED
        if (txn.getRetryCount() != null &&
                txn.getRetryCount() >= Constants.MAX_RETRY_ATTEMPT &&
                !isExceptionWileProcessing) {

            log.info("PaypalProviderHandler.reconTransaction() - txn.getRetryCount(): {}", txn.getRetryCount());
            txn.setTxnStatus(TxnStatusEnum.FAILED.getName());
            txn.setErrorCode(ErrorCodeEnum.RECON_PAYMEN_FAILED.getCode());
            txn.setErrorMessage(ErrorCodeEnum.RECON_PAYMEN_FAILED.getMessage());
        }



        transactionDAO.updateTransactionForRecon(txn); // Update retry Count in DB.
        log.info("Updated Txn in DB txn: {}", txn);
    }

    private PPOrder getOrderFromPP(TransactionDTO txn) {
        HttpRequest httpRequest = ppGetOrderHelper.prepareHttpRequest(txn);
        log.info("PaypalProviderHandler.reconTransaction() - " + "httpRequest: {}", httpRequest);

        ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);
        log.info("PaypalProviderHandler.reconTransaction() - " + "response: {}", response);

        PPOrder successObj = ppGetOrderHelper.processGetOrderResponse(response);
        log.info("PaypalProviderHandler.reconTransaction() - " + "successObj: {}", successObj);
        return successObj;
    }

    private PPOrder ppCaptureOrder(TransactionDTO txn) {
        HttpRequest httpRequest = ppCaptureOrderHelper.prepareHttpRequest(txn);
        log.info("PaypalProviderHandler.reconTransaction() - " + "httpRequest: {}", httpRequest);

        ResponseEntity<String> response = httpServiceEngine.makeHttpCall(httpRequest);
        log.info("PaypalProviderHandler.reconTransaction() - " + "response: {}", response);

        PPOrder successObj = ppCaptureOrderHelper.processResponse(response);
        log.info("PaypalProviderHandler.reconTransaction() - " + "successObj: {}", successObj);
        return successObj;
    }



}
