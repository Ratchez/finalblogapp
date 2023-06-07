package com.codewithratchez.blog.exceptions;

public class ApiException extends RuntimeException{
    public ApiException(String message){
        super(message);
    }

}
