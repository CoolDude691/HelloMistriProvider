package com.hellomistri.hellomistriprovidern.Model;

public class City{
    public String id;
    public String cname;
    public String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public City(String id, String cname, String status) {
        this.id = id;
        this.cname = cname;
        this.status = status;
    }
}