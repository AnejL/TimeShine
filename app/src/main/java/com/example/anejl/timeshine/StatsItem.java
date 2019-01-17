package com.example.anejl.timeshine;

public class StatsItem {
    private String name;
    private String type;
    private String rating;
    private String comment;

    public String getName(){
        return this.name;
    }
    public String getType(){
        return this.type;
    }
    public String getRating() {
        return this.rating;
    }
    public String getComment() {
        return this.comment;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setType(String type){
        this.type=type;
    }
    public void setRating(String rating){
        this.rating=rating;
    }
    public void setComment(String comment){
        this.comment=comment;
    }
}