package com.xhh.javaApi.ReflectApi;

import java.io.Serializable;
import java.util.Date;

public class Emp implements Serializable {

    private String name;
    private Long id; //员工ID
    private Integer age;
    private Date df;//出生日期
    private Dep dep;//所属部门
    private Company company;//所属公司

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getDf() {
        return df;
    }

    public void setDf(Date df) {
        this.df = df;
    }

    public Dep getDep() {
        return dep;
    }

    public void setDep(Dep dep) {
        this.dep = dep;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", age=" + age +
                ", df=" + df +
                ", dep=" + dep +
                ", company=" + company +
                '}';
    }
}
