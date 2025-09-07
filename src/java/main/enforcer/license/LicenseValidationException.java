package com.gmail.bishoybasily.licensing.enforcer.license;

public class LicenseValidationException extends RuntimeException {

    public LicenseValidationException(Throwable cause) {
        super(cause);
    }

    public LicenseValidationException(String message) {
        super(message);
    }

    public LicenseValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
