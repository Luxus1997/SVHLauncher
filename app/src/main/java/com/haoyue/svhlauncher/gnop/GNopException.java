package com.haoyue.svhlauncher.gnop;

/**
 * <pre>
 *   GNOP的异常。
 * </pre>
 *
 * @author Nstar
 * @version 1.0
 */
public class GNopException extends RuntimeException {
    public GNopException() {
    }

    public GNopException(String message) {
        super(message);
    }

    public GNopException(String message, Throwable cause) {
        super(message, cause);
    }

    public GNopException(Throwable cause) {
        super(cause);
    }
}

