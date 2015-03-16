package org.iflab.icampus.model;

public class YelloPageList {
    private String id;
    private String name;
    private String telnum;
    private String depart;


    public YelloPageList() {
        super();
    }

    public YelloPageList(String id, String name, String telnum, String depart) {
        super();
        this.id = id;
        this.name = name;
        this.telnum = telnum;
        this.depart = depart;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelnum() {
        return telnum;
    }

    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    @Override
    public String toString() {
        return "YelloPage [id=" + id + ", name=" + name + ", telnum=" + telnum
                + ", depart=" + depart + "]";
    }


}
