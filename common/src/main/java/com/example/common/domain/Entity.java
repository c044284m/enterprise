package com.example.common.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.common.events.Event;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity extends AssertionConcern {
    protected final Identity id;
    @JsonIgnore //If not included then when we convert to JSON without DTO for remote events it will cause recursive loop
    public List<Event> listOfEvents = new ArrayList<>();

    protected Entity(Identity id) {
        this.id = id;
    }

    protected void addDomainEvent(Event event){
        listOfEvents.add(event);
    }
    protected void removeDomainEvent(Event event){
        listOfEvents.remove(event);
    }

    public List<Event> listOfDomainEvents(){
        return listOfEvents;
    }

    public Identity id() {
        return id;
    }

    public boolean equals(Object o){
        if (o == null && o.getClass() != this.getClass()){
            return false;
        }
        Entity another = (Entity) o;

        return another.id == this.id;
    }
}
