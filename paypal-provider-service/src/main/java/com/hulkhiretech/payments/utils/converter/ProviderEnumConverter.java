package com.hulkhiretech.payments.utils.converter;

import org.modelmapper.AbstractConverter;

import com.hulkhiretech.payments.constants.ProviderEnum;

public class ProviderEnumConverter extends AbstractConverter<Integer, String> {
    @Override
    protected String convert(Integer source) {
        return ProviderEnum.fromId(source).getName();
    }
}
