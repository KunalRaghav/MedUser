package com.asterisks.medchange.user.api.models;

public class MedicineModel {
    long id;
    String created_at;
    String updated_at;
    boolean archived;
    String name;
    String mrp;
    String quantity;
    String pricePerTablet;

    public long getId() {
        return id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public boolean isArchived() {
        return archived;
    }

    public String getName() {
        return name;
    }

    public String getMrp() {
        return mrp;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPricePerTablet() {
        return pricePerTablet;
    }
}


