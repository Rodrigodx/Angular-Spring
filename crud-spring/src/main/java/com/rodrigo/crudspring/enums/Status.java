package com.rodrigo.crudspring.enums;

import lombok.Getter;

@Getter
public enum Status {

    ACTIVE("Ativo"),
    INACTIVE("Inativo");

    public final String value;

    Status(String value){
        this.value = value;
    }
    @Override
    public String toString(){
        return value;
    }



}
