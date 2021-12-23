package com.mk.tradelog.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum Account {
    ALL(null), INRADAY("730090030"), DAILY("730063102");

    private final String value;

    public String getValue() {
        return value;
    }
}
