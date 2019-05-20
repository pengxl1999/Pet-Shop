package com.pengxl.petshop.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pengxl.petshop.R;

public class ListViewItem extends LinearLayout {

    private ListView parent;
    private ImageView type;
    private TextView name, age, color, gender;

    public ListViewItem(Context context) {
        super(context);

    }

    public ListViewItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        parent = (ListView) findViewById(R.id.pet_list);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.list_view_item, parent, false);
        type = (ImageView) findViewById(R.id.list_item_type);
        name = (TextView) findViewById(R.id.list_item_name);
        age = (TextView) findViewById(R.id.list_item_age);
        color = (TextView) findViewById(R.id.list_item_color);
        gender = (TextView) findViewById(R.id.list_item_gender);
    }

    public void setType(int resId) {
        type.setImageResource(resId);
    }

    public void setName(String text) {
        name.setText(text);
    }

    public void setAge(String text) {
        age.setText(text);
    }

    public void setColor(String text) {
        color.setText(text);
    }

    public void setGender(String text) {
        gender.setText(text);
    }
}
