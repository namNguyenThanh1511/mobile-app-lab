package com.example.lab05_bai2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ModuleAdapter extends BaseAdapter {
    private Context context;
    private List<Module> moduleList;
    private LayoutInflater inflater;

    public ModuleAdapter(Context context, List<Module> moduleList) {
        this.context = context;
        this.moduleList = moduleList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return moduleList.size();
    }

    @Override
    public Module getItem(int position) {
        return moduleList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_module, parent, false);
            holder = new ViewHolder();
            holder.imgPlatform = convertView.findViewById(R.id.imgPlatform);
            holder.tvTitle = convertView.findViewById(R.id.tvTitle);
            holder.tvDescription = convertView.findViewById(R.id.tvDescription);
            holder.tvPlatform = convertView.findViewById(R.id.tvPlatform);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Module module = getItem(position);
        holder.tvTitle.setText(module.getTitle());
        holder.tvDescription.setText(module.getDescription());
        holder.tvPlatform.setText(module.getPlatform());

        // Set icon based on platform
        if (module.getPlatform().equalsIgnoreCase("Android")) {
            holder.imgPlatform.setImageResource(R.drawable.ic_android);
        } else if (module.getPlatform().equalsIgnoreCase("iOS")) {
            holder.imgPlatform.setImageResource(R.drawable.ic_ios);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imgPlatform;
        TextView tvTitle, tvDescription, tvPlatform;
    }
}

