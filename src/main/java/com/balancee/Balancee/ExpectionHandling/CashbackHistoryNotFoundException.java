package com.balancee.Balancee.ExpectionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CashbackHistoryNotFoundException extends RuntimeException {
    public CashbackHistoryNotFoundException(String message) {
        super(message);
    }
}
