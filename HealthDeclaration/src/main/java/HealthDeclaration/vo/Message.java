package HealthDeclaration.vo;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    /**
     * code
     */
    private String code;

    /**
     * Message
     */
    private String msg;
}
