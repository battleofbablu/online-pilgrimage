package com.hulkhiretech.payments.utils.converter;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.constants.PaymentTypeEnum;

public class PaymentTypeEnumConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return PaymentTypeEnum.fromId(source).getName();
    }
}
