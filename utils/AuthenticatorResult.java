package utils;

import java.io.Serializable;

public class AuthenticatorResult implements Serializable{
    private static final long serialVersionUID = 123456789L;
    private boolean success;
    private String message;

    public AuthenticatorResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
