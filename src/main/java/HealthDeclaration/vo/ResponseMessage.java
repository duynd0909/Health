package HealthDeclaration.vo;

import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    /**
     * Success flag
     */
    private boolean success;

    /**
     * List message
     */
    private List<Message> message;

    /**
     * Response data
     */
    private Object data;

}
