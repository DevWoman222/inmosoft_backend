package com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions;

public class NotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1;

    public NotFoundException(String message){
        super(message);
    }
}