package ua.polina.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.polina.entity.User;
import ua.polina.exception.CustomException;

@RestControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    protected Boolean handleAccessDeniedException(){
        return false;
    }

    @ExceptionHandler(CustomException.class)
    protected User handleUserNameExistException(){
        return null;
    }
}
