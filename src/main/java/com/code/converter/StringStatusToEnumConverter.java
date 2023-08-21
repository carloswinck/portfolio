package com.code.converter;

import com.code.enums.Status;
import org.springframework.core.convert.converter.Converter;

public class StringStatusToEnumConverter implements Converter<String, Status> {
    @Override
    public Status convert(String source) {
        return Status.valueOf(source.toUpperCase());
    }
}

