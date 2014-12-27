package net.brotzeller.topeer.exception;

/**
 * Created by martin on 22.12.14.
 */
public class TopoException extends Exception{
    public TopoException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
    public TopoException(String detailMessage) {
        super(detailMessage);
    }
}
