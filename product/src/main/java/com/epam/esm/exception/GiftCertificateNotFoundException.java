package com.epam.esm.exception;

public class GiftCertificateNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private long id;
    private String message;

    public GiftCertificateNotFoundException(long id) {
        this.id = id;
    }

    public GiftCertificateNotFoundException(String message) {
        this.message = message;
    }

    public long getId() {
        return id;
    }

}
