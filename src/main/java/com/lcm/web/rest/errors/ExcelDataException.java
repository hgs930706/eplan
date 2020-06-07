package com.lcm.web.rest.errors;

public class ExcelDataException extends RuntimeException{

    public ExcelDataException() {
        super();
    };

    public ExcelDataException(String message) {
        super(message);
    }

    public ExcelDataException(Throwable cause) {
        super(cause);
    }

    public ExcelDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
