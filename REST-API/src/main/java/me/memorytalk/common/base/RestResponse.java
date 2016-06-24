package me.memorytalk.common.base;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestResponse implements Serializable {

    private static final long serialVersionUID = -5411770071892144105L;

    private Boolean success;

    private String message;

    private Object data;

    public RestResponse(Boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
