package com.model;


import java.util.ArrayList;

public class ObjectListResponse {

    private Boolean result;
    private ArrayList objects;

    public ObjectListResponse() {
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public ArrayList getObjects() {
        return objects;
    }

    public void setObjects(ArrayList objects) {
        this.objects = objects;
    }
}
