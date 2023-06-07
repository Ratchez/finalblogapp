package com.codewithratchez.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
//    String fieldName;
//    long fieldValue;

    public ResourceNotFoundException(String resourceName){
        super(String.format("%s not found", resourceName));
        this.resourceName = resourceName;
//        this.fieldName = fieldName;
//        this.fieldValue = fieldValue;
    }
}
