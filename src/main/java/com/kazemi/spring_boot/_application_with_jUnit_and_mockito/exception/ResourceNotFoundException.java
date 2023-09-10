package com.kazemi.spring_boot._application_with_jUnit_and_mockito.exception;

/**
 * @author fh.kazemi
 **/

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
