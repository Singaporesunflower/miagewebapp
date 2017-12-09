package com.miage.business.exception;

import java.util.Date;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 5991998170182040917L;
	private Date date;
    private String message;
     
    public ServiceException(String message) {
        super();
        this.date = new Date();
        this.message = message;
    }
 
    public Date getDate() {
        return date;
    }
 
    public String getMessage() {
        return message;
    }
 
    @Override
    public String toString() {
        return "ServiceException [date=" + date + ", message=" + message + "]";
    }
}