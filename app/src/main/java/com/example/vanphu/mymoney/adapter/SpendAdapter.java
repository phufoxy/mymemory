package com.example.vanphu.mymoney.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vanphu.mymoney.R;
import com.example.vanphu.mymoney.UpdateSpendActivity;
import com.example.vanphu.mymoney.controller.SpendController;
import com.example.vanphu.mymoney.model.SpendModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class SpendAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<SpendModel> mSpendList;

    public SpendAdapter(Context Context, List<SpendModel> SpendList) {
        this.mSpendList = SpendList;
        mInflater = LayoutInflater.from(Context);
        ArrayList<SpendModel> mArrayList = new ArrayList<>();
        mArrayList.addAll(mSpendList);
    }

    public class ViewHolder {
        ImageView img_Spend;
        TextView txt_NameSpend;
        TextView txt_PriceSpend;
        TextView txt_time;
    }

    @Override
    public int getCount() {
        return mSpendList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSpendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.row_spend_item, null);
            //locate the views in row.xml
            holder.img_Spend = view.findViewById(R.id.img_Spend);
            holder.txt_NameSpend = view.findViewById(R.id.txt_NameSpend);
            holder.txt_PriceSpend = view.findViewById(R.id.txt_PriceSpend);
            holder.txt_time = view.findViewById(R.id.txt_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.img_Spend.setImageResource(mSpendList.get(position).getmImage());
        holder.txt_NameSpend.setText(mSpendList.get(position).getmTilte());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        int money_item = mSpendList.get(position).getmPrice();
        String Money = formatter.format(money_item) + " " + SpendController.sKeyMoney;
        holder.txt_PriceSpend.setText(Money);
        holder.txt_time.setText(mSpendList.get(position).getmDate());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mInflater.getContext(), UpdateSpendActivity.class);
                intent.putExtra("id", String.valueOf(mSpendList.get(position).getmId()));
                intent.putExtra("Money", String.valueOf(mSpendList.get(position).getmPrice()));
                intent.putExtra("Title", mSpendList.get(position).getmTilte());
                intent.putExtra("Date", mSpendList.get(position).getmDate());
                mInflater.getContext().startActivity(intent);
            }
        });
        return view;
    }
}