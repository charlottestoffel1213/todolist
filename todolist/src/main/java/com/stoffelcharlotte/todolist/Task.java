package com.stoffelcharlotte.todolist;

/**
 * Created by stoffelcharlotte on 10/12/18.
 * ******************************
 * ******************************
 */

public class    Task {
    private     String name;
    private     String date;
    private     String desc;

    public Task(String name, String date, String desc){
        this.name = name;
        this.date = date;
        this.desc = desc;
    }

    public Task(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
