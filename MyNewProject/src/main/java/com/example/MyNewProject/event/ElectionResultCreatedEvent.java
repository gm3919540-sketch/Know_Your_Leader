package com.example.MyNewProject.event;

public class ElectionResultCreatedEvent {
    private final int id;
    public  ElectionResultCreatedEvent(int id){
        this.id=id;
    }
    public  int getCandidateId(){
        return  id;
    }
}
