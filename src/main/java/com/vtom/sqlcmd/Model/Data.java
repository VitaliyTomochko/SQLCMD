package com.vtom.sqlcmd.Model;

public class Data {
    private String name;
    private Object value;

    public Data() {

    }

    public Data(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getName() + "  " + this.getValue();
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return this.name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
