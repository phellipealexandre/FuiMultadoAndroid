package com.manolosmobile.fuimultado.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by phellipe on 4/21/16.
 */
@DatabaseTable
public class Car implements Serializable {

    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField
    private String plate;
    @DatabaseField
    private String renavam;
    @DatabaseField
    private String estate;
    @DatabaseField
    private String model;
    private List<Bill> bills;

    public Car() {
    }

    public Car(String plate, String renavam, String estate) {
        this.plate = plate;
        this.renavam = renavam;
        this.estate = estate;
        this.model = "";
        this.bills = new ArrayList<>();
    }

    public Car(String plate, String renavam, String estate, String model) {
        this.plate = plate;
        this.renavam = renavam;
        this.estate = estate;
        this.model = model;
        this.bills = new ArrayList<>();
    }

    public String getPlate() {
        return plate;
    }

    public String getRenavam() {
        return renavam;
    }

    public String getEstate() {
        return estate;
    }

    public String getModel() {
        return model;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public long getId() {
        return id;
    }

    public int getAssessmentsNumber() {
        int assessmentsCount = 0;
        for (Bill bill : bills) {
            if (bill.isAssessment()) {
                assessmentsCount++;
            }
        }

        return assessmentsCount;
    }

    public int getNonAssessmentBillsNumber() {
        int assessmentsCount = getAssessmentsNumber();
        return bills.size() - assessmentsCount;
    }
}
