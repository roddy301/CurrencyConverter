package com.example.currencyconverter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class currencyAdapter extends ArrayAdapter<currencyItem> {

    private ArrayList<currencyItem> currencyList;

    public currencyAdapter(Context context, int textViewResourceID, ArrayList<currencyItem> currencyList) {
        super(context,textViewResourceID,currencyList);
        this.currencyList = currencyList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_view_item,null);
        }

        currencyItem i = currencyList.get(position);

        if (i!=null){
            TextView tvBase = v.findViewById(R.id.tvBase);
            TextView tvValue = v.findViewById(R.id.tvValue);

            tvBase.setText(i.getBase());
            tvValue.setText(String.valueOf(i.getValue()));
        }

        return v;
    }
}
