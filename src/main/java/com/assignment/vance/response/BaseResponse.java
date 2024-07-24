package com.assignment.vance.response;

import org.springframework.stereotype.Component;

@Component
public class BaseResponse {
    private Status status;
    private Object data;

    public BaseResponse(){

    }
    public BaseResponse(Status status){
        this.status = status;
    }

    public BaseResponse(Status status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
