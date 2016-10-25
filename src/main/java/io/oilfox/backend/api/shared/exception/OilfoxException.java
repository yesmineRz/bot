package io.oilfox.backend.api.shared.exception;

/**
 * Created by ipusic on 1/21/16.
 */
public class OilfoxException extends Exception {
    public OilfoxException(String message) {
        super(message);
    }

    public OilfoxException(String message, Throwable cause) {
        super(message, cause);
    }
}
