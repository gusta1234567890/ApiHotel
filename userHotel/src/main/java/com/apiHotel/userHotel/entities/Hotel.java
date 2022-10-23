package com.apiHotel.userHotel.entities;

import java.util.Date;

public class Hotel {

    public Hotel(){
    }

    private int id;
    private String keyDetail;
    private String name;
    private String description;
    private Endereco address;
    private String checkIn;
    private String checkOut;
    private Imagem images;

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

    public Imagem getImages() {
        return images;
    }

    public void setImages(Imagem images) {
        this.images = images;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Endereco getAddress() {
        return address;
    }

    public void setAddress(Endereco address) {
        this.address = address;
    }
}
