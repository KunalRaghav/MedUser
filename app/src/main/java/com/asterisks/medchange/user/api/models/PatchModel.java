package com.asterisks.medchange.user.api.models;

public class PatchModel {
    public boolean isRequested;
    public int pharmacist;

    public PatchModel(boolean isRequested, int pharmacist) {
        this.isRequested = isRequested;
        this.pharmacist = pharmacist;
    }
}

