package com.asterisks.medchange.user.api.models;

public class PharmacistLocationModel {
    String quantityOfMedicine;
    String pharmacist__address;
    String pharmacist__username;
    String id;
    String creditForMedicine;
    String pharmacist__latitude;
    String pharmacist__longitude;
    String expiryDate;
    String medicine__id;
    String medicine__name;

    public String getQuantityOfMedicine() {
        return quantityOfMedicine;
    }

    public void setQuantityOfMedicine(String quantityOfMedicine) {
        this.quantityOfMedicine = quantityOfMedicine;
    }

    public String getPharmacist__address() {
        return pharmacist__address;
    }

    public void setPharmacist__address(String pharmacist__address) {
        this.pharmacist__address = pharmacist__address;
    }

    public String getPharmacist__username() {
        return pharmacist__username;
    }

    public void setPharmacist__username(String pharmacist__username) {
        this.pharmacist__username = pharmacist__username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreditForMedicine() {
        return creditForMedicine;
    }

    public void setCreditForMedicine(String creditForMedicine) {
        this.creditForMedicine = creditForMedicine;
    }

    public String getPharmacist__latitude() {
        return pharmacist__latitude;
    }

    public void setPharmacist__latitude(String pharmacist__latitude) {
        this.pharmacist__latitude = pharmacist__latitude;
    }

    public String getPharmacist__longitude() {
        return pharmacist__longitude;
    }

    public void setPharmacist__longitude(String pharmacist__longitude) {
        this.pharmacist__longitude = pharmacist__longitude;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMedicine__id() {
        return medicine__id;
    }

    public void setMedicine__id(String medicine__id) {
        this.medicine__id = medicine__id;
    }

    public String getMedicine__name() {
        return medicine__name;
    }

    public void setMedicine__name(String medicine__name) {
        this.medicine__name = medicine__name;
    }
}

