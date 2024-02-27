package com.rodrigo.crudspring.enums;

import lombok.Getter;

@Getter
public enum Category {

    BACK_END("Back-end"),
    FRONT_END("Front-end");

    private final String value;

    Category(String value){
        this.value = value;
    }

    @Override
    public String toString(){
        return value;
    }

}
