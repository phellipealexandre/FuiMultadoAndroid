package com.manolosmobile.fuimultado.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.manolosmobile.fuimultado.R;
import com.manolosmobile.fuimultado.models.Bill;
import com.manolosmobile.fuimultado.models.Car;

import java.util.List;

public class BillsListAdapter extends ArrayAdapter<Bill> {


    private final int COLOR_LIGHT_YELLOW = 0xFFFFFFE6;
    private final int COLOR_LIGHT_RED = 0xFFFFE6E6;

    public BillsListAdapter(Context context, List<Bill> bills) {
        super(context, R.layout.billinfo_list_item, bills);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.billinfo_list_item, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.txtBillCode = (TextView) convertView.findViewById(R.id.txtBillCode);
            viewHolder.txtBillDescription = (TextView) convertView.findViewById(R.id.txtBillDescription);
            viewHolder.txtBillDate = (TextView) convertView.findViewById(R.id.txtBillDate);
            viewHolder.txtBillLocation = (TextView) convertView.findViewById(R.id.txtBillLocation);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Bill bill = getItem(position);
        viewHolder.txtBillCode.setText(String.format("Código: %s", bill.getCode()));
        viewHolder.txtBillDescription.setText(bill.getDescription());
        viewHolder.txtBillDate.setText(bill.getDate());
        viewHolder.txtBillLocation.setText(bill.getLocation());

        if (bill.isAssessment()) {
            convertView.setBackgroundColor(COLOR_LIGHT_YELLOW);
        } else {
            convertView.setBackgroundColor(COLOR_LIGHT_RED);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView txtBillDescription;
        TextView txtBillCode;
        TextView txtBillLocation;
        TextView txtBillDate;
    }
}
