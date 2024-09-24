package com.mechoy.flowable.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private boolean flag;
    private String message;
    private Object data;

    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }
}