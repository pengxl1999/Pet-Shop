package com.pengxl.petshop.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pengxl.petshop.R;

public class ImageTextButton extends LinearLayout {

    private ImageView imageView;
    private TextView textView;

    public ImageTextButton(Context context) {
        super(context);
    }

    public ImageTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.main_img_txt_btn, this, true);

        imageView = (ImageView) findViewById(R.id.main_img_txt_btn_image);
        textView = (TextView) findViewById(R.id.main_img_txt_btn_text);

        this.setClickable(true);
        this.setFocusable(true);
    }

    public void setImgResource(int resourceId) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(resourceId);
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    public void setTextSize(float size) {
        textView.setTextSize(size);
    }

}
