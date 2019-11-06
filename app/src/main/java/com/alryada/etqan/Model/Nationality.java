package com.alryada.etqan.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Nationality {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nationality_name")
    @Expose
    private String nationalityName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
    }
}

