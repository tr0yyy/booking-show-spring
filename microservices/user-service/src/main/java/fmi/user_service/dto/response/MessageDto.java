package fmi.user_service.dto.response;

// no type alias for me in java
public class MessageDto extends ResponseDto<String> {
    public MessageDto(String message) {
        super(true, message);
    }
}
