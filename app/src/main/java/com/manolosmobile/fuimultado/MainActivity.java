package com.manolosmobile.fuimultado;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.manolosmobile.fuimultado.adapters.CarsListAdapter;
import com.manolosmobile.fuimultado.bills.BillsUpdater;
import com.manolosmobile.fuimultado.callbacks.abstractions.OnBillsUpdatedCallback;
import com.manolosmobile.fuimultado.callbacks.abstractions.OnCarsReceivedCallback;
import com.manolosmobile.fuimultado.callbacks.abstractions.OnDatabaseOperationFinishCallback;
import com.manolosmobile.fuimultado.database.DatabaseManager;
import com.manolosmobile.fuimultado.exceptions.ParserNotFoundException;
import com.manolosmobile.fuimultado.models.Car;
import com.manolosmobile.fuimultado.sharedpreferences.DateSharedPreferencePersister;
import com.manolosmobile.fuimultado.ui.DialogHelper;
import com.manolosmobile.fuimultado.callbacks.DetranCallbackResult;
import com.manolosmobile.fuimultado.callbacks.abstractions.OnDetranParseFinishedCallback;
import com.manolosmobile.fuimultado.web.DetranParser;
import com.manolosmobile.fuimultado.web.DetranParserFactory;
import com.manolosmobile.fuimultado.web.EEstate;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ListView listCars;
    private TextView txtNoCarsWarning;
    private TextView txtLastTimeUpdated;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
        updateLatestDate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAddCar:
                DialogHelper.createAddCarDialog(this, DetranParserFactory.getAvailableEstates(), new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        EditText edtPlate = (EditText) dialog.findViewById(R.id.edtPlate);
                        EditText edtRenavam = (EditText) dialog.findViewById(R.id.edtRenavam);
                        Spinner spinnerEstate = (Spinner) dialog.findViewById(R.id.spinnerEstates);
                        String plate = edtPlate.getText().toString();
                        String renavam = edtRenavam.getText().toString();
                        String estate = spinnerEstate.getSelectedItem().toString();

                        if (!DatabaseManager.getInstance().carExists(plate, renavam)) {
                            addCar(plate, renavam, estate);
                        } else {
                            DialogHelper.createWarningDialog(MainActivity.this,
                                    String.format("O carro de placa %s e renavam %s já está cadastrado",
                                            plate, renavam));
                        }

                    }
                });

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        updateCars();
    }

    private void addCar(String plate, String renavam, String estate) {
        try {
            DialogHelper.createProgressDialog(this);
            DetranParser parser = DetranParserFactory.createParser(this, EEstate.getEnumByString(estate));
            parser.getCarInfoFromWeb(plate, renavam, new OnDetranParseFinishedCallback() {

                @Override
                public void onFinish(DetranCallbackResult result) {
                    DialogHelper.dismissCurrentDialog();

                    if (result.hasError()) {
                        DialogHelper.createWarningDialog(MainActivity.this, result.getErrorMessage());
                    } else {
                        Car car = result.getResultCar();
                        DatabaseManager.getInstance().addCar(car, new OnDatabaseOperationFinishCallback() {
                            @Override
                            public void onFinish(boolean success, String errorMessage) {
                                if (success) {
                                    updateList();
                                } else {
                                    DialogHelper.createWarningDialog(MainActivity.this, errorMessage);
                                }
                            }
                        });
                    }
                }
            });
        } catch (ParserNotFoundException ex) {
            DialogHelper.createWarningDialog(this, ex.getDetailedMessage());
        }
    }

    private void updateCars() {
        BillsUpdater.updateBillsFromAllCarsInDatabase(this, new OnBillsUpdatedCallback() {

            @Override
            public void onSuccess() {
                refreshLayout.setRefreshing(false);
                DateSharedPreferencePersister.persistDateNow(getApplicationContext());
                updateList();
                updateLatestDate();
            }

            @Override
            public void onError(String errorMessage) {
                refreshLayout.setRefreshing(false);
                DialogHelper.createWarningDialog(MainActivity.this, errorMessage);
            }
        });
    }

    private void initComponents() {
        txtNoCarsWarning = (TextView) findViewById(R.id.txtNoCarsWarnning);
        txtLastTimeUpdated = (TextView) findViewById(R.id.txtLastTimeUpdated);
        listCars = (ListView) findViewById(R.id.listCars);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        refreshLayout.setOnRefreshListener(this);

        listCars.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // This code doesn't work if listView has paddingTop
                boolean enable = false;
                if (listCars != null && listCars.getChildCount() > 0) {
                    // Check if the first item of the list is visible
                    boolean firstItemVisible = listCars.getFirstVisiblePosition() == 0;
                    // Check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listCars.getChildAt(0).getTop() == 0;
                    // Enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                refreshLayout.setEnabled(enable);
            }
        });
    }

    private void updateList() {
        DatabaseManager.getInstance().findAllCars(new OnCarsReceivedCallback() {
            @Override
            public void onFinish(List<Car> cars) {
                if (!cars.isEmpty()) {
                    txtNoCarsWarning.setVisibility(View.GONE);
                    listCars.setVisibility(View.VISIBLE);

                    CarsListAdapter adapter = new CarsListAdapter(MainActivity.this, cars);
                    listCars.setAdapter(adapter);
                    listCars.setOnItemClickListener(adapter);
                } else {
                    txtNoCarsWarning.setVisibility(View.VISIBLE);
                    listCars.setVisibility(View.GONE);
                }
            }
        });
    }

    private void updateLatestDate() {
        String lastDate = DateSharedPreferencePersister.getLastSavedDateAsString(this);
        txtLastTimeUpdated.setText("Última atualização em " + lastDate);
    }
}
