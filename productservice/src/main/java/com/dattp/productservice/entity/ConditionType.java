package com.dattp.productservice.entity;

public enum ConditionType {
    NUMBER("NUMBER"),
    GROUP("GROUP"),
    PRICE("PRICE");
    private final String type;
    private ConditionType(String type){
        this.type = type;
    }

    public String value(){
        return this.type;
    }
}
