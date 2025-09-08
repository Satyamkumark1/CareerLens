package CareerLens.demo.exceptions;

public class BadRequestException extends RuntimeException{

    String message;

    public BadRequestException(String message){
        this.message = message;

    }

}
