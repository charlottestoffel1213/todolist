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
    private     String time;

    public Task(String name, String time){
        this.name = name;
        this.time = time;
    }

    public Task(){
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
