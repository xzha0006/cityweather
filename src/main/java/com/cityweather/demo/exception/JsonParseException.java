package com.cityweather.demo.exception;

/**
 * Created by xuanzhang on 5/8/18.
 */
public class JsonParseException extends Exception {
    public JsonParseException() {
        super();
    }

    public JsonParseException(String message){
        super(message);
    }

}
