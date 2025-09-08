package CareerLens.demo.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> myGlobalExceptionHandler(MethodArgumentNotValidException exception){

        Map<String ,String> responce = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(err -> {
            String fieldName = ((FieldError)err).getField();
            String message = err.getDefaultMessage();
            responce.put(fieldName,message);

        });
        return new ResponseEntity<Map<String,String>>(responce, HttpStatus.BAD_REQUEST).getBody();
    }


    //Creating our own ResourceNotFoundException from ->  ResourceNotFoundException Class
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestException> myResourceNotFoundException(ResourceNotFoundException e){

        String message = e.getMessage();
        BadRequestException apiResponse = new BadRequestException(message);

        return  new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
     }

     //Creating our own ApiException   from -> ApiException Class
//     @ExceptionHandler(com.ecommerce.project.exception.ApiException.class)
//     public  ResponseEntity<String> myApiException(ApiException e){
//        String message = e.getMessage();
//        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
//     }



}