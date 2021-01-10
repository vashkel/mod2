package com.epam.esm.exception;

public class GiftCertificateNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1604672147363917448L;

    private long id;
    private String message;

    public GiftCertificateNotFoundException(long id) {
        this.id = id;
    }

    public GiftCertificateNotFoundException(String message) {
       super(message);
    }

    public long getId() {
        return id;
    }

}
