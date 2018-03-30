package me.nettee.financial.error;

public class BadJsonDataException extends RuntimeException {

    public BadJsonDataException(Throwable e) {
        super(e);
    }
}
