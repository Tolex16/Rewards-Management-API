package com.balancee.Balancee.ExpectionHandling;


public class RewardsNotFoundException extends RuntimeException{
    public RewardsNotFoundException(String message) {
        super(message);
    }
}
