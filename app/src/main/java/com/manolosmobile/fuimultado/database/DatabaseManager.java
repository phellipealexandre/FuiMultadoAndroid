package com.manolosmobile.fuimultado.database;

import android.content.Context;
import android.os.AsyncTask;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.manolosmobile.fuimultado.callbacks.OnCarsReceivedCallback;
import com.manolosmobile.fuimultado.callbacks.OnDatabaseOperationFinishCallback;
import com.manolosmobile.fuimultado.models.Bill;
import com.manolosmobile.fuimultado.models.Car;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static DatabaseManager instance;
    private DatabaseHelper helper;

    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    public static void init(Context context) {
        if (null == instance) {
            instance = new DatabaseManager(context);
        }
    }

    public synchronized boolean carExists(String plate, String renavam) {
        List<Car> cars = null;
        try {
             cars = helper.getCarDao().queryBuilder().where().eq("plate", plate).and().eq("renavam", renavam).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cars != null && !cars.isEmpty();
    }

    public synchronized void findAllCars(final OnCarsReceivedCallback callback) {
        AsyncTask<Void, Void, List<Car>> task = new AsyncTask<Void, Void, List<Car>>() {

            @Override
            protected List<Car> doInBackground(Void... params) {
                List<Car> list = new ArrayList<>();
                try {
                    list = helper.getCarDao().queryBuilder().orderBy("plate", false).query();

                    for (Car car: list) {
                        car.setBills(findAllBillsFromCarPlate(car.getPlate()));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                return list;
            }

            @Override
            protected void onPostExecute(List<Car> cars) {
                super.onPostExecute(cars);
                callback.onSuccess(cars);
            }
        };

        task.execute();
    }

    public synchronized void addCar(final Car car, final OnDatabaseOperationFinishCallback callback) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                String errorMessage = null;
                try {
                    helper.getCarDao().create(car);

                    if (car.getBills() != null) {
                        for (Bill bill : car.getBills()) {
                            addBill(bill);
                        }
                    }
                } catch (SQLException e) {
                    errorMessage = e.getMessage();
                }

                return errorMessage;
            }

            @Override
            protected void onPostExecute(String errorMessage) {
                super.onPostExecute(errorMessage);
                callback.onOperationFinish(errorMessage == null, errorMessage);
            }
        };

        task.execute();
    }

    public synchronized void removeCar(final Car car, final OnDatabaseOperationFinishCallback callback) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                String errorMessage = null;
                try {
                    helper.getCarDao().deleteById(car.getId());
                    for (Bill bill : car.getBills()) {
                        removeBill(bill);
                    }
                } catch (SQLException e) {
                    errorMessage = e.getMessage();
                }

                return errorMessage;
            }

            @Override
            protected void onPostExecute(String errorMessage) {
                super.onPostExecute(errorMessage);
                callback.onOperationFinish(errorMessage == null, errorMessage);
            }
        };

        task.execute();
    }

    public synchronized void updateAllBills(final Car car, final OnDatabaseOperationFinishCallback callback) {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                String errorMessage = null;
                for (Bill bill : findAllBillsFromCarPlate(car.getPlate())) {
                    removeBill(bill);
                }
                for (Bill bill : car.getBills()) {
                    addBill(bill);
                }

                return errorMessage;
            }

            @Override
            protected void onPostExecute(String errorMessage) {
                super.onPostExecute(errorMessage);
                callback.onOperationFinish(errorMessage == null, errorMessage);
            }
        };

        task.execute();
    }

    private synchronized void removeBill(Bill bill) {
        try {
            helper.getBillDao().deleteById(bill.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private synchronized List<Bill> findAllBillsFromCarPlate(String carPlate) {
        List<Bill> list = new ArrayList<>();

        try {
            list = helper.getBillDao().queryBuilder().where().eq("carPlate", carPlate).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private synchronized void addBill(Bill bill) {
        try {
            helper.getBillDao().create(bill);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
