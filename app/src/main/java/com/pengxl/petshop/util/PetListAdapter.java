package com.pengxl.petshop.util;

import android.content.Context;
import android.support.annotation.*;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pengxl.petshop.R;

import java.util.List;

public class PetListAdapter extends ArrayAdapter<Pet> {

    private Context mContext;
    private int mResource;

    public PetListAdapter(@NonNull Context context, int resource, @NonNull List<Pet> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ViewHolder item = null;

        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResource, parent, false);
            item = new ViewHolder();
            item.type = convertView.findViewById(R.id.list_item_type);
            item.name = convertView.findViewById(R.id.list_item_name);
            item.age = convertView.findViewById(R.id.list_item_age);
            item.gender = convertView.findViewById(R.id.list_item_gender);
            item.color = convertView.findViewById(R.id.list_item_color);
            convertView.setTag(item);
        }
        else {
            item = (ViewHolder) convertView.getTag();
        }

        Pet pet = getItem(position);
        switch (Pet.petType.valueOf(pet.getType())) {
            case DOG:
                item.type.setImageResource(R.drawable.dog);
                break;
            case CAT:
                item.type.setImageResource(R.drawable.cat);
                break;
            case FISH:
                item.type.setImageResource(R.drawable.fish);
                break;
            case RABBIT:
                item.type.setImageResource(R.drawable.rabbit);
                break;
            default:
                break;
        }
        item.name.setText("Name: " + pet.getName());
        item.age.setText("Age: " + String.valueOf(pet.getAge()));
        item.gender.setText("Gender: " + pet.getGender());
        item.color.setText("Color:" + pet.getColor());

        return convertView;
    }

    private class ViewHolder {
        public ImageView type;
        public TextView name, age, gender, color;
    }
}
