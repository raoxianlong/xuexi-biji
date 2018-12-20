package com.xhh.javaApi.ReflectApi;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class Dep implements Serializable {

    private String name;
    private Date time;
    private Integer peopleNums;
    private Company company;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Dep{" +
                "name='" + name + '\'' +
                ", time=" + time +
                ", peopleNums=" + peopleNums +
                ", company=" + company +
                ", empNames=" + Arrays.toString(empNames) +
                '}';
    }
}
