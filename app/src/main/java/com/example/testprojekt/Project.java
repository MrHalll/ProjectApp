package com.example.testprojekt;


public class Project {
    String name;
    int id;

    public Project(){

    }

    public Project(String name, int id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public int getID(){
        return id;
    }
}
