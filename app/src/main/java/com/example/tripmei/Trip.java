package com.example.tripmei;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity
public class Trip {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String date;
    private String time;
    private String tripState;
    private String tripType;
    private String tripRepeat;
    private String from;
    private String to;
    private String notes;
    private double latitudeStart;
    private double latitudeEnd;
    private double longitudeStart;
    private double longitudeEnd;

    public Trip() {
    }

    public Trip(int id ,String name, String date, String time, String tripState, String tripType, String tripRepeat, String from,
                String to, double latitudeStart, double latitudeEnd, double longitudeStart, double longitudeEnd) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.tripState = tripState;
        this.tripType = tripType;
        this.tripRepeat = tripRepeat;
        this.from = from;
        this.to = to;
        this.latitudeStart = latitudeStart;
        this.latitudeEnd =latitudeEnd;
        this.longitudeStart = longitudeStart;
        this.longitudeEnd =longitudeEnd;

    }

    @Ignore
    public Trip(String name, String date, String time, String tripState, String tripType,String tripRepeat, String from,
                String to , double latitudeStart, double latitudeEnd , double longitudeStart , double longitudeEnd){
        this.name = name;
        this.date = date;
        this.time = time;
        this.tripState = tripState;
        this.tripType = tripType;
        this.tripRepeat = tripRepeat;
        this.from = from;
        this.to = to;
        this.latitudeStart = latitudeStart;
        this.latitudeEnd =latitudeEnd;
        this.longitudeStart = longitudeStart;
        this.longitudeEnd =longitudeEnd;
    }


    public String getTripRepeat() {
        return tripRepeat;
    }

    public void setTripRepeat(String tripRepeat) {
        this.tripRepeat = tripRepeat;
    }

    public String getTripState() {
        return tripState;
    }

    public void setTripState(String tripState) {
        this.tripState = tripState;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getLatitudeStart() {
        return latitudeStart;
    }

    public void setLatitudeStart(double latitudeStart) {
        this.latitudeStart = latitudeStart;
    }

    public double getLatitudeEnd() {
        return latitudeEnd;
    }

    public void setLatitudeEnd(double latitudeEnd) {
        this.latitudeEnd = latitudeEnd;
    }

    public double getLongitudeStart() {
        return longitudeStart;
    }

    public void setLongitudeStart(double longitudeStart) {
        this.longitudeStart = longitudeStart;
    }

    public double getLongitudeEnd() {
        return longitudeEnd;
    }

    public void setLongitudeEnd(double longitudeEnd) {
        this.longitudeEnd = longitudeEnd;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}