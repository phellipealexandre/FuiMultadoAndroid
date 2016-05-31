package com.manolosmobile.fuimultado.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by phellipe on 4/21/16.
 */
@DatabaseTable
public class Bill implements Serializable {

    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String code;
    @DatabaseField
    private boolean isAssessment;
    @DatabaseField
    private String carPlate;
    @DatabaseField
    private String description;
    @DatabaseField
    private String location;
    @DatabaseField
    private String date;

    public Bill() {
    }

    public Bill(String code, boolean isAssessment, String carPlate, String description,
                String location, String date) {
        this.code = code;
        this.isAssessment = isAssessment;
        this.carPlate = carPlate;
        this.description = description;
        this.location = location;
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public boolean isAssessment() {
        return isAssessment;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public long getId() {
        return id;
    }
}
