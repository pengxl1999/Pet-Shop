package com.pengxl.petshop;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import static com.pengxl.petshop.util.PetShop.*;

import com.pengxl.petshop.util.ImageTextButton;
import com.pengxl.petshop.util.PetListAdapter;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private ListView petList;
    private ImageTextButton addPet, searchPet, deletePet, changePet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PetListAdapter adapter = new PetListAdapter(MainActivity.this, R.layout.list_view_item, pets);
        petList.setAdapter(adapter);
    }

    private void init() {
        addPet = (ImageTextButton) findViewById(R.id.add_pet);
        searchPet = (ImageTextButton) findViewById(R.id.search_pet);
        deletePet = (ImageTextButton) findViewById(R.id.delete_pet);
        changePet = (ImageTextButton) findViewById(R.id.change_pet);
        petList = (ListView) findViewById(R.id.pet_list);

        petList.setEnabled(false);

        addPet.setText("添加宠物");
        addPet.setImgResource(R.drawable.add);

        searchPet.setText("查询宠物");
        searchPet.setImgResource(R.drawable.search);

        deletePet.setText("删除宠物");
        deletePet.setImgResource(R.drawable.delete);

        changePet.setText("修改宠物信息");
        changePet.setImgResource(R.drawable.change);

        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPetActivity.class);
                startActivity(intent);
            }
        });

        searchPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchPetActivity.class);
                startActivity(intent);
            }
        });

        deletePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteSearchActivity.class);
                startActivity(intent);
            }
        });

        changePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChangeSearchActivity.class);
                startActivity(intent);
            }
        });

        Log.i("pengxl1999", pets.size()+"");
        PetListAdapter adapter = new PetListAdapter(MainActivity.this, R.layout.list_view_item, pets);
        petList.setAdapter(adapter);
    }
}
