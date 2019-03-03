package com.asterisks.medchange.user.api.models;

import android.os.Parcelable;

import java.io.Serializable;

public class UserMedicineModel implements Serializable {
    String id;
    String created_at;
    String updated_at;
    String creditForMedicine;
    String expiryDate;
    String medicinePicture;
    String expiryPicture;
    String quantityOfMedicine;
    Boolean isSoldByPharmacist;
    Boolean isAcceptedByPharmacist;
    Boolean isRequested;
    String user;
    String medicine;
    String pharmacist;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreditForMedicine() {
        return creditForMedicine;
    }

    public void setCreditForMedicine(String creditForMedicine) {
        this.creditForMedicine = creditForMedicine;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getMedicinePicture() {
        return medicinePicture;
    }

    public void setMedicinePicture(String medicinePicture) {
        this.medicinePicture = medicinePicture;
    }

    public String getExpiryPicture() {
        return expiryPicture;
    }

    public void setExpiryPicture(String expiryPicture) {
        this.expiryPicture = expiryPicture;
    }

    public String getQuantityOfMedicine() {
        return quantityOfMedicine;
    }

    public void setQuantityOfMedicine(String quantityOfMedicine) {
        this.quantityOfMedicine = quantityOfMedicine;
    }

    public Boolean getSoldByPharmacist() {
        return isSoldByPharmacist;
    }

    public void setSoldByPharmacist(Boolean soldByPharmacist) {
        isSoldByPharmacist = soldByPharmacist;
    }

    public Boolean getAcceptedByPharmacist() {
        return isAcceptedByPharmacist;
    }

    public void setAcceptedByPharmacist(Boolean acceptedByPharmacist) {
        isAcceptedByPharmacist = acceptedByPharmacist;
    }

    public Boolean getRequested() {
        return isRequested;
    }

    public void setRequested(Boolean requested) {
        isRequested = requested;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(String pharmacist) {
        this.pharmacist = pharmacist;
    }
}
