package com.banco.Saint_Patrik.Errors;

public class ServiceError extends Exception {

    public ServiceError(String msn) {
        super(msn);
    }
}
