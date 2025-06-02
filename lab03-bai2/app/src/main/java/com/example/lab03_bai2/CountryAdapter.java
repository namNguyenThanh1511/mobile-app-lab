package com.example.lab03_bai2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CountryAdapter extends BaseAdapter {
    private Context context;
    private List<Country> countryList;
    private LayoutInflater inflater;

    public CountryAdapter(Context context, List<Country> countryList) {
        this.context = context;
        this.countryList = countryList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int position) {
        return countryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_item_layout, null);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.tvTitle = convertView.findViewById(R.id.tvTitle);
            holder.tvDescription = convertView.findViewById(R.id.tvDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Country country = countryList.get(position);
        holder.imageView.setImageResource(country.getImageId());
        holder.tvTitle.setText(country.getTitle());
        holder.tvDescription.setText(country.getDescription());

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView tvTitle;
        TextView tvDescription;
    }
}

