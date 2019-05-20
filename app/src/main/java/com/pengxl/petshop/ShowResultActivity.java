package com.pengxl.petshop;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.pengxl.petshop.util.Pet;
import com.pengxl.petshop.util.PetListAdapter;
import com.pengxl.petshop.util.RequestCode;

import org.json.JSONObject;

import java.util.LinkedList;

import static com.pengxl.petshop.util.PetShop.pets;


public class ShowResultActivity extends AppCompatActivity {

    public static JSONObject condition = new JSONObject();
    private LinkedList<Pet> searchResult;
    private ImageButton back;
    private TextView title;
    private ListView searchList;
    private String type, name, gender, color;
    private int age, requestCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        PetListAdapter adapter = new PetListAdapter(ShowResultActivity.this, R.layout.list_view_item, searchResult);
        searchList.setAdapter(adapter);
    }

    private void init() {
        searchResult = new LinkedList<>();
        back = (ImageButton) findViewById(R.id.result_back);
        title = (TextView) findViewById(R.id.result_title);
        searchList = (ListView) findViewById(R.id.search_list);
        requestCode = getIntent().getIntExtra("requestCode", 0);
        switch (requestCode) {
            case RequestCode.SEARCH:
                title.setText("查询宠物");
                searchList.setEnabled(false);
                break;
            case RequestCode.DELETE:
                title.setText("删除宠物");
                searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Pet pet = (Pet) parent.getItemAtPosition(position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ShowResultActivity.this)
                                .setTitle("确定要删除吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        searchResult.remove(pet);
                                        pets.remove(pet);
                                        PetListAdapter adapter = new PetListAdapter(ShowResultActivity.this, R.layout.list_view_item, searchResult);
                                        searchList.setAdapter(adapter);
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        builder.setCancelable(false);
                        builder.create();
                        builder.show();
                    }
                });
                break;
            case RequestCode.CHANGE:
                title.setText("修改宠物信息");
                searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ChangeActivity.pet = (Pet) parent.getItemAtPosition(position);
                        Intent intent = new Intent(ShowResultActivity.this, ChangeActivity.class);
                        startActivity(intent);
                    }
                });
                break;
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getListView();
    }

    private void getListView() {
        try {
            type = condition.getString("type");
            name = condition.getString("name");
            age = condition.getInt("age");
            gender = condition.getString("gender");
            color = condition.getString("color");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.i("pengxl1999", "age: " + age);
        for(Pet pet : pets) {
            if(pet.getType().equals(type) || type.equals("")
                    && (pet.getName().equals(name) || name.equals(""))
                    && (pet.getAge() == age || age == 0)
                    && (pet.getGender().equals(gender) || gender.equals("UNKNOWN"))
                    && (pet.getColor().equals(color) || color.equals(""))) {
                searchResult.add(pet);
            }
        }
    }
}
