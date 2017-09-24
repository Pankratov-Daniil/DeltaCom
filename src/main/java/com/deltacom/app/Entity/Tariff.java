package com.deltacom.app.Entity;

import java.util.List;
import java.util.ArrayList;

public class Tariff {
    private int id;
    private String name;
    private float price;

    private List<Option> options;

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

    public void setOptions (List<Option> options) {
        this.options = (options == null) ? new ArrayList<Option>() : new ArrayList<Option>(options);
    }

    public List<Option> getOptions () {
        return new ArrayList<Option>(options);
    }
}