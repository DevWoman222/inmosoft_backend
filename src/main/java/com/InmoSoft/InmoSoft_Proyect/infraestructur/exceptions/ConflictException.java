package com.InmoSoft.InmoSoft_Proyect.infraestructur.exceptions;

public class ConflictException extends Throwable{
    private static final long serialVersionUID = 1;

    public ConflictException(String message){
        super(message);
    }
}
