package com.deltacom.app.entity;

public class Option {
    private Integer id;
    private String name;
    private Float price;
    private Float connectionCost;

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void setPrice (Float price) {
        this.price = price;
    }

    public Float getPrice () {
        return price;
    }

    public void setConnectionCost (Float connectionCost) {
        this.connectionCost = connectionCost;
    }

    public Float getConnectionCost () {
        return connectionCost;
    }

    @Override
    public int hashCode(){
        final int prime = 31;
        int result = 1;

        result = prime * result + id;
        result = prime * result + Float.floatToIntBits(price);
        result = prime * result + Float.floatToIntBits(connectionCost);

        return result;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(!(obj instanceof Option))
            return false;
        Option anotherOption = (Option)obj;

        return anotherOption.id.compareTo(this.id) == 0 &&
                anotherOption.name.equals(this.name) &&
                anotherOption.price.compareTo(this.price) == 0 &&
                anotherOption.connectionCost.compareTo(this.connectionCost) == 0;
    }
}