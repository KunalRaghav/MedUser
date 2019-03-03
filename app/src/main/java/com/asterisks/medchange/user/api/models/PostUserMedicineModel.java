package com.asterisks.medchange.user.api.models;

public class PostUserMedicineModel {
    String expiryDate;
    Integer quantityOfMedicine;
    Integer medicine;

    public PostUserMedicineModel(String expiryDate, Integer quantityOfMedicine, Integer medicine) {
        this.expiryDate = expiryDate;
        this.quantityOfMedicine = quantityOfMedicine;
        this.medicine = medicine;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getQuantityOfMedicine() {
        return quantityOfMedicine;
    }

    public void setQuantityOfMedicine(Integer quantityOfMedicine) {
        this.quantityOfMedicine = quantityOfMedicine;
    }

    public Integer getMedicine() {
        return medicine;
    }

    public void setMedicine(Integer medicine) {
        this.medicine = medicine;
    }
}

