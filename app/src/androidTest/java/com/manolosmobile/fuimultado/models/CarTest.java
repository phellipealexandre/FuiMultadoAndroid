package com.manolosmobile.fuimultado.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CarTest {

    @Test
    public void Car_OneAssessmentBill_Success() {
        Car car = new Car("plate", "renavam", "estate", "model");
        Bill bill = new Bill("Code", true, "plate", "description", "location", "date");
        List<Bill> bills = new ArrayList<>();
        bills.add(bill);

        car.setBills(bills);

        assertEquals(1, car.getAssessmentsNumber());
    }

    @Test
    public void Car_OneNonAssessmentBill_Success() {
        Car car = new Car("plate", "renavam", "estate", "model");
        Bill bill = new Bill("Code", false, "plate", "description", "location", "date");
        List<Bill> bills = new ArrayList<>();
        bills.add(bill);

        car.setBills(bills);

        assertEquals(1, car.getNonAssessmentBillsNumber());
    }

    @Test
    public void Car_ZeroNonAssessmentBillWithOneAssessmentInsertion_Success() {
        Car car = new Car("plate", "renavam", "estate", "model");
        Bill bill = new Bill("Code", true, "plate", "description", "location", "date");
        List<Bill> bills = new ArrayList<>();
        bills.add(bill);

        car.setBills(bills);

        assertEquals(0, car.getNonAssessmentBillsNumber());
    }

    @Test
    public void Car_ZeroAssessmentBillWithOneNonAssessmentInsertion_Success() {
        Car car = new Car("plate", "renavam", "estate", "model");
        Bill bill = new Bill("Code", false, "plate", "description", "location", "date");
        List<Bill> bills = new ArrayList<>();
        bills.add(bill);

        car.setBills(bills);

        assertEquals(0, car.getAssessmentsNumber());
    }

    @Test
    public void Car_OneAssessmentBillAndOneNonAssessmentInsertion_Success() {
        Car car = new Car("plate", "renavam", "estate", "model");
        Bill bill = new Bill("Code", false, "plate", "description", "location", "date");
        Bill bill2 = new Bill("Code", true, "plate", "description", "location", "date");
        List<Bill> bills = new ArrayList<>();
        bills.add(bill);
        bills.add(bill2);

        car.setBills(bills);

        assertEquals(1, car.getAssessmentsNumber());
        assertEquals(1, car.getNonAssessmentBillsNumber());
    }
}