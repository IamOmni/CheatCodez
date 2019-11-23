package com.kroy.classes;

public class Landmark extends Building{
    private String name;
    private int level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void upgrade(){};
}
