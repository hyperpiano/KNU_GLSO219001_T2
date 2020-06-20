package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<RestaurantsModel> restaurantsData;
    LayoutInflater layoutInflater;
    RestaurantsModel restaurantsModel;

    public CustomAdapter(Context context, ArrayList<RestaurantsModel> restaurantsData) {
        this.context = context;
        this.restaurantsData = restaurantsData;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return restaurantsData.size();
    }

    @Override
    public Object getItem(int i) {
        return restaurantsData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return restaurantsData.get(i).getId();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View rowView = view;
        if (rowView==null) {
            rowView = layoutInflater.inflate(R.layout.restaurant_row, null, true);
        }
        //link views
        ImageView itemFlagIv = rowView.findViewById(R.id.itemFlagIv);
        TextView itemNameTv = rowView.findViewById(R.id.itemNameTv);
        TextView itemInfoTv = rowView.findViewById(R.id.itemInfoTv);
        TextView itemInfo2Tv = rowView.findViewById(R.id.itemInfo2Tv);

        restaurantsModel = restaurantsData.get(position);

        itemFlagIv.setImageResource(restaurantsModel.getImage());
        itemNameTv.setText(restaurantsModel.getName());
        itemInfoTv.setText("restaurant info : " + restaurantsModel.getInfo());
        itemInfo2Tv.setText("info2 : " + restaurantsModel.getInfo2());

        return rowView;
    }
}
