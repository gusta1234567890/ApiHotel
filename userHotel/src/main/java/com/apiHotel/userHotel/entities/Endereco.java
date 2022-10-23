package com.apiHotel.userHotel.entities;

public class Endereco {
    public Endereco(){

    }

    private int zipcode;
    private String addresss;
    private int number;
    private String complement;
    private Cidade cidade;
    private String neighborhood;
    private Cordenadas coordinates;

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Cordenadas getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Cordenadas coordinates) {
        this.coordinates = coordinates;
    }
}
