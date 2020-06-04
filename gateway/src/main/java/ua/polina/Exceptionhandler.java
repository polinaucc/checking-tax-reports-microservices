package ua.polina;

import com.netflix.client.ClientException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class Exceptionhandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ClientException.class)
    protected String handleAccessDeniedException(){
        return "wrong-page";
    }
}
