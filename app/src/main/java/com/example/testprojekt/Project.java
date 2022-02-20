package com.example.testprojekt;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Project implements Parcelable {
    private String name;
    private int id;
    private ArrayList<String> checklist;

    public Project(){

    }

    public Project(String name, int id){
        this.name = name;
        this.id = id;
        checklist = new ArrayList<>();
    }

    protected Project(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    public void addToChecklist(String task){
        if (checklist == null)
            checklist = new ArrayList<>();
        checklist.add(task);
    }

    public String getName(){
        return name;
    }

    public String toString(){ return name; }

    public int getID(){
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }

    public ArrayList<String> getChecklist() {
        return checklist;
    }
}
