package com.hulkhiretech.payments.utils.converter;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.constants.PaymentMethodEnum;

public class PaymentMethodEnumConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return PaymentMethodEnum.fromId(source).getName();
    }
}
