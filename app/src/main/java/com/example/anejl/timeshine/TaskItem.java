package com.example.anejl.timeshine;

public class TaskItem {
    private String name;
    private String type;
    private String duration;

    public String getName(){
        return this.name;
    }
    public String getType(){
        return this.type;
    }
    public String getDuration() {
        return this.duration;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setType(String type){
        this.type=type;
    }
    public void setDuration(String duration){
        this.duration=duration;
    }
}
