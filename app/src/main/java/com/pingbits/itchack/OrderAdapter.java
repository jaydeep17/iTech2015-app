package com.pingbits.itchack;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pingbits.greendao.Ordder;

import java.text.SimpleDateFormat;
import java.util.List;

public class OrderAdapter extends BaseAdapter {

    private final Context context;
    private List<Ordder> items;

    public OrderAdapter(Context context) {
        this.context = context;
        items = ((MyApplication) context.getApplicationContext()).getOrderDao().loadAll();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Ordder getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_order, viewGroup, false);
        }

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(getItem(i).getName());
        TextView subTitle = (TextView) view.findViewById(R.id.subTitle);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        subTitle.setText("Ordered on " + sdf.format(getItem(i).getDate()));
        return view;
    }

    public void remove(int i) {
        ((MyApplication) context.getApplicationContext()).getOrderDao().delete(getItem(i));
        items.remove(i);
        notifyDataSetChanged();
    }
}
