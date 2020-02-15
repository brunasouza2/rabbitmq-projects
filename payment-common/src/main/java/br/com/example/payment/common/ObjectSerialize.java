package br.com.example.payment.common;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;


public class ObjectSerialize<T> {

    private static ObjectSerialize INSTANCE = null;

    public static ObjectSerialize getInstance(){
        if(INSTANCE==null){
            INSTANCE = new ObjectSerialize();
        }
        return INSTANCE;
    }

    private Gson gson = new Gson();

    public byte[] serialize(Object object){
        if(object==null){
            return null;
        }
        String json = gson.toJson(object);
        return json.getBytes(StandardCharsets.UTF_8);
    }

    public Object deSerialize(byte[] bytes, Class<T> type){
        String json = new String(bytes, StandardCharsets.UTF_8);
        return gson.fromJson(json, type);
    }

    public String deSerialize(byte[] bytes){
        return  new String(bytes, StandardCharsets.UTF_8);
    }
}
