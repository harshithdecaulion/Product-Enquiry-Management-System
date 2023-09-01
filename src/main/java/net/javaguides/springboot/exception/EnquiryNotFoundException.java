package net.javaguides.springboot.exception;

public class EnquiryNotFoundException extends RuntimeException {




    public EnquiryNotFoundException(String message) {
        super(message);
    }

    public EnquiryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
