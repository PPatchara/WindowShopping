package com.example.justwyne.windowshopping.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Justwyne on 10/3/2016 AD.
 */

public class SavingList implements Serializable {
    private static SavingList instance;
    private ArrayList<Event> eventList;

    private SavingList() {
        eventList = new ArrayList<>();
    }

    public static SavingList getInstance() {
        if(instance == null) {
            instance = new SavingList();
        }
        return instance;
    }

    public int size(){
        return eventList.size();
    }

    public void add(Event event){
        for (Event eventObj : eventList) {
            if (eventObj.equals(event)) {
                return;
            }
        }
        eventList.add(event);
    }

    public Event getEvent(Event event){
        for (Event eventObj : eventList) {
            if (eventObj.equals(event)) {
                return eventObj;
            }
        }
        return null;
    }

    public ArrayList<Event> getEventList() {
        return eventList;
    }
}
