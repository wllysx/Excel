package com.wll.myproject.excel;
/*
    Create by WLL on 2020/3/28 DATA: 12:20
*/

class BeanExportData {

    private String number;
    private String name;
    private String age;
    private String data;

    public BeanExportData(String number, String name, String age, String data) {
        this.number = number;
        this.name = name;
        this.age = age;
        this.data = data;
    }

    @Override
    public String toString() {
        return "BeanExportData{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
