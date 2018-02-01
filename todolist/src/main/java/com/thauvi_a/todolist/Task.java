package com.thauvi_a.todolist;

/**
 * Created by thauvi_a on 1/13/18.
 * <p>
 * ******************************
 * * BOOM BITCH GET OUT THE WAY *
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
