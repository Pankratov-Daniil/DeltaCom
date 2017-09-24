package com.deltacom.app.entity;

public class ClientContract {
    private Integer idClientContract;
    private Integer idClient;
    private Long numberContract;

    public Integer getIdClientContract() {
        return idClientContract;
    }

    public void setIdClientContract(Integer idClientContract) {
        this.idClientContract = idClientContract;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClient(Integer idClient) {
        this.idClient = idClient;
    }

    public Long getNumberContract() {
        return numberContract;
    }

    public void setNumberContract(Long numberContract) {
        this.numberContract = numberContract;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(!(obj instanceof ClientContract))
            return false;
        ClientContract anotherObj = (ClientContract)obj;

        return this.idClient.compareTo(anotherObj.idClient) == 0 &&
                this.numberContract.compareTo(anotherObj.numberContract) == 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + idClient.hashCode();
        result = prime * result + numberContract.hashCode();

        return result;
    }
}
