package com.apiHotel.userHotel.entities;

import java.util.Date;

public class SimpleHotel {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyDetail() {
        return keyDetail;
    }

    public void setKeyDetail(String keyDetail) {
        this.keyDetail = keyDetail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public SimpleHotel(Hotel hotel)
    {
        this.id = hotel.getId();
        this.keyDetail = hotel.getKeyDetail();
        this.name = hotel.getName();
        this.checkIn = hotel.getCheckIn();
        this.checkOut = hotel.getCheckOut();
    }

    private int id;
    private String keyDetail;
    private String name;
    private String checkIn;
    private String checkOut;
}
