package com.xhh.javaApi.ReflectApi;

import java.io.Serializable;
import java.util.Date;

public class Company implements Serializable {

    private String id; //公司编号
    private String name;
    private Date time;
    private Integer peopleNums;
    private Double register;
    private Byte[] status;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getPeopleNums() {
        return peopleNums;
    }

    public void setPeopleNums(Integer peopleNums) {
        this.peopleNums = peopleNums;
    }

    public Double getRegister() {
        return register;
    }

    public void setRegister(Double register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", peopleNums=" + peopleNums +
                ", register=" + register +
                '}';
    }
}
