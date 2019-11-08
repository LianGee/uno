package com.bigdata.uno.repository.excption;

public class RepositoryException extends RuntimeException {

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable e) {
        super(message, e);
    }
}
