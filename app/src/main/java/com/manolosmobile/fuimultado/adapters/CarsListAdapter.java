package com.manolosmobile.fuimultado.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.manolosmobile.fuimultado.CarInfoActivity;
import com.manolosmobile.fuimultado.R;
import com.manolosmobile.fuimultado.utils.Constants;
import com.manolosmobile.fuimultado.models.Car;

import java.util.List;

public class CarsListAdapter extends ArrayAdapter<Car> implements AdapterView.OnItemClickListener{

    public CarsListAdapter(Context context, List<Car> cars) {
        super(context, R.layout.carinfo_list_item, cars);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.carinfo_list_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtCarModel = (TextView) convertView.findViewById(R.id.txtCarModel);
            viewHolder.txtCarPlate = (TextView) convertView.findViewById(R.id.txtCarPlate);
            viewHolder.txtCarRenavam = (TextView) convertView.findViewById(R.id.txtCarRenavam);
            viewHolder.txtCarAssessments = (TextView) convertView.findViewById(R.id.txtCarAssessmentsNumber);
            viewHolder.txtCarBills = (TextView) convertView.findViewById(R.id.txtCarBillsNumber);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Car car = getItem(position);

        viewHolder.txtCarBills.setText(String.format("%d multas", car.getNonAssessmentBillsNumber()));
        viewHolder.txtCarAssessments.setText(String.format("%d autuações", car.getAssessmentsNumber()));
        viewHolder.txtCarPlate.setText(String.format("Placa: %s", car.getPlate()));
        viewHolder.txtCarRenavam.setText(String.format("Renavam: %s", car.getRenavam()));
        viewHolder.txtCarModel.setText(car.getModel());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), CarInfoActivity.class);
        intent.putExtra(Constants.INTENT_KEY_CAR, getItem(position));
        getContext().startActivity(intent);
    }

    static class ViewHolder {
        TextView txtCarModel;
        TextView txtCarPlate;
        TextView txtCarRenavam;
        TextView txtCarAssessments;
        TextView txtCarBills;
    }
}
