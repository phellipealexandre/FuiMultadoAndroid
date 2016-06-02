package com.manolosmobile.fuimultado.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.manolosmobile.fuimultado.R;
import com.manolosmobile.fuimultado.models.Bill;
import com.manolosmobile.fuimultado.models.Car;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "FuiMultadoDB";
    private static final int DATABASE_VERSION = 1;

    private Dao<Car, Long> carDao;
    private Dao<Bill, Long> billDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Car.class);
            TableUtils.createTable(connectionSource, Bill.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Car.class, false);
            TableUtils.dropTable(connectionSource, Bill.class, false);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Car, Long> getCarDao() throws SQLException {
        if(carDao == null) {
            carDao = getDao(Car.class);
        }
        return carDao;
    }

    public Dao<Bill, Long> getBillDao() throws SQLException {
        if(billDao == null) {
            billDao = getDao(Bill.class);
        }
        return billDao;
    }
}
