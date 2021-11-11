package HealthDeclaration.common.base.controller;

import HealthDeclaration.common.response.utils.ResponseUtils;
import HealthDeclaration.vo.Message;
import HealthDeclaration.vo.ResponseMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
//import sun.misc.MessageUtils;

import java.util.ArrayList;
import java.util.List;

//import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The type Base controller.
 */
@Component
public class BaseController {
    /**
     * The Message utils.
     */
//    private MessageUtils messageUtils;
    /**
     * The Validator.
     */
    private Validator validator;
    /**
     * The Model mapper.
     */
    private ModelMapper modelMapper;
    /**
     * Forbidden.
     *
     * @return the response entity
     */
    public ResponseEntity forbidden() {
        List<Message> messages = new ArrayList<>();
        ResponseMessage responseMessage = new ResponseMessage();
        String msgKey = "common.forbidden";
        Message msg = new Message();
        msg.setCode("error");
        msg.setMsg("Forbidden");
        messages.add(msg);
        responseMessage.setSuccess(false);
        responseMessage.setMessage(messages);
        return ResponseUtils.buildResponseMessage(false,
                responseMessage, HttpStatus.FORBIDDEN);
    }
    /**
     * Gets logged in username.
     *
     * @return the logged in username
     */
    public String getLoggedInUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
//        return "phuongnv14";
    }
    /**
     * Gets message utils.
     *
     * @return the message utils
     */
//    public MessageUtils getMessageUtils() {
//        return messageUtils;
//    }
    /**
     * Sets message utils.
     *
     * @param pMessageUtils the p message utils
     */
//    @Autowired
//    public void setMessageUtils(final MessageUtils pMessageUtils) {
//        messageUtils = pMessageUtils;
//    }
    /**
     * Gets validator.
     *
     * @return the validator
     */
    public Validator getValidator() {
        return validator;
    }
//    /**
//     * Sets validator.
//     *
//     * @param pValidator the p validator
//     */
//    @Autowired
//    @Qualifier("AppValidator")
//    public void setValidator(final Validator pValidator) {
//        validator = pValidator;
//    }
    /**
     * Sets model mapper.
     *
     * @param pModelMapper the p model mapper
     */
    @Autowired
    public void setModelMapper(final ModelMapper pModelMapper) {
        modelMapper = pModelMapper;
    }
    /**
     * Gets model mapper.
     *
     * @return the model mapper
     */
    public ModelMapper getModelMapper() {
        return modelMapper;
    }
}
