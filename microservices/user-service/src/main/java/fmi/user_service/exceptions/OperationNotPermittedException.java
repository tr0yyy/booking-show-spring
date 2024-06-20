package fmi.user_service.exceptions;

public class OperationNotPermittedException extends Exception{
    public OperationNotPermittedException(String message) {
        super(String.format(ErrorMessage.OPERATION_NOT_PERMITTED + " : %s", message));
    }
}
