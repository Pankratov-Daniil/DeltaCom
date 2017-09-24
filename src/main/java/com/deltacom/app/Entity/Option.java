package com.deltacom.app.Entity;

public class Option {
    private int id;
    private String name;
    private float price;
    private float connectionCost;

    public void setId (int id) {
        this.id = id;
    }

    public int getId () {
        return id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void setPrice (float price) {
        this.price = price;
    }

    public float getPrice () {
        return price;
    }

    public void setConnectionCost (float connectionCost) {
        this.connectionCost = connectionCost;
    }

    public float getConnectionCost () {
        return connectionCost;
    }
}