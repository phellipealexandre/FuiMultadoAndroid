package com.manolosmobile.fuimultado;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.manolosmobile.fuimultado.database.DatabaseManager;
import com.manolosmobile.fuimultado.callbacks.FinishActivityCallback;
import com.manolosmobile.fuimultado.ui.DialogHelper;
import com.manolosmobile.fuimultado.utils.Constants;
import com.manolosmobile.fuimultado.adapters.BillsListAdapter;
import com.manolosmobile.fuimultado.models.Car;

public class CarInfoActivity extends AppCompatActivity {

    private TextView txtModel;
    private TextView txtPlate;
    private TextView txtRenavam;
    private ListView listBillsInfo;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_info);

        car = (Car) getIntent().getSerializableExtra(Constants.INTENT_KEY_CAR);
        initComponents(car);
        BillsListAdapter adapter = new BillsListAdapter(this, car.getBills());
        listBillsInfo.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.carinfomenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionRemoveCar:
                DialogHelper.createRemoveCarDialog(this, new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        DatabaseManager.getInstance().removeCar(car, new FinishActivityCallback(CarInfoActivity.this));
                    }
                });

                return true;
            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    private void initComponents(Car car) {
        txtModel = (TextView) findViewById(R.id.txtCarModel);
        txtPlate = (TextView) findViewById(R.id.txtCarPlate);
        txtRenavam = (TextView) findViewById(R.id.txtCarRenavam);
        listBillsInfo = (ListView) findViewById(R.id.listBillsInfo);

        txtModel.setText(car.getModel());
        txtRenavam.setText(car.getRenavam());
        txtPlate.setText(car.getPlate().toUpperCase());
    }
}
