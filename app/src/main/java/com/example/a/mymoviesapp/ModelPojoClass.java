package com.example.a.mymoviesapp;

import java.io.Serializable;

public class ModelPojoClass implements Serializable{

String key;
    String name;


    public ModelPojoClass(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }


    public void setKey(String key) {
        this.key = key;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
