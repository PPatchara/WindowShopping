package com.example.justwyne.windowshopping.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Justwyne on 10/2/2016 AD.
 */

public class EventList {

    private ArrayList<Event> events;
    private static EventList instance;

    private EventList(){
        events = new ArrayList<>();
    }

    public static EventList getInstance() {
        if (instance == null) {
            instance = new EventList();
        }
        return instance;
    }

    public void loadData(InputStream inputStream){
        Scanner scan = new Scanner(inputStream);
        String jsonString = "";
        while (scan.hasNext()){
            jsonString += scan.nextLine();
        }
        scan.close();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray eventList = jsonObject.getJSONArray("event_list");
            for (int index=0; index < eventList.length(); index++){
                JSONObject eventTmp = eventList.getJSONObject(index);

                Event event = new Event();
                event.setCategory(eventTmp.getString("category"));
                event.setSubcategory(eventTmp.getString("subcategory"));
                event.setId(eventTmp.getString("id"));
                event.setName(eventTmp.getString("name"));
                event.setDescription(eventTmp.getString("description"));
                event.setDate(eventTmp.getString("date"));
                event.setTime(eventTmp.getString("time"));
                event.setPrice(eventTmp.getString("price"));
                event.setPlace(eventTmp.getString("place"));
                events.add(event);

            }
            Log.d("events", "Load Data...");
        }catch (JSONException ex){
            Log.e("JsonParser",ex.getMessage());
        }
    }

    public Event getEvent(String eventId){
        for (int index=0; index < length(); index++){
            String id = events.get(index).getId();
            if (id.equalsIgnoreCase(eventId)){
                return events.get(index);
            }
        }
        return null;
    }

    public int length(){
        return events.size();
    }
}
