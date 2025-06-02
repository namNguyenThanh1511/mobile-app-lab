package com.example.lab03_bai2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemLayout extends LinearLayout {
    private ImageView imageView;
    private TextView tvTitle;
    private TextView tvDescription;

    public ItemLayout(Context context) {
        super(context);
        init(context);
    }

    public ItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        // Inflate layout
        LayoutInflater.from(context).inflate(R.layout.activity_item_layout, this, true);

        // Ánh xạ các view
        imageView = findViewById(R.id.imageView);
        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
    }

    // Phương thức để set dữ liệu cho item
    public void setData(Country country) {
        if (country != null) {
            imageView.setImageResource(country.getImageId());
            tvTitle.setText(country.getTitle());
            tvDescription.setText(country.getDescription());
        }
    }

    // Getters cho các view
    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTitleTextView() {
        return tvTitle;
    }

    public TextView getDescriptionTextView() {
        return tvDescription;
    }
}
