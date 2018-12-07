package com.xhh.javaApi.ReflectApi;

import java.io.Serializable;
import java.util.Date;

public class Dep implements Serializable {

    private String name;
    private Date time;
    private Integer peopleNums;
    private Company sCompany;
    private String[] empNames;

    public String[] getEmpNames() {
        return empNames;
    }
    public void setEmpNames(String[] empNames) {
        this.empNames = empNames;
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

    public Company getsCompany() {
        return sCompany;
    }

    public void setsCompany(Company sCompany) {
        this.sCompany = sCompany;
    }
}
