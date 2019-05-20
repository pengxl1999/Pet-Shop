package com.pengxl.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.pengxl.petshop.util.RequestCode;

import org.json.JSONObject;

import static com.pengxl.petshop.ShowResultActivity.condition;

public class SearchPetActivity extends AppCompatActivity {
    private Spinner type, gender;
    private EditText name, age, color;
    private ImageButton back;
    private Button clear, search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pet);

        init();
    }

    private void init() {
        back = (ImageButton) findViewById(R.id.search_pet_back);
        clear = (Button) findViewById(R.id.search_pet_clear);
        search = (Button) findViewById(R.id.search_pet_search);
        type = (Spinner) findViewById(R.id.search_pet_type);
        name = (EditText) findViewById(R.id.search_pet_name);
        age = (EditText) findViewById(R.id.search_pet_age);
        gender = (Spinner) findViewById(R.id.search_pet_gender);
        color = (EditText) findViewById(R.id.search_pet_color);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type.setSelection(0);
                name.setText("");
                age.setText("");
                gender.setSelection(0);
                color.setText("");
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condition = new JSONObject();
                try {
                    switch (type.getSelectedItemPosition()) {
                        case 0:
                            condition.put("type", "DOG");
                            break;
                        case 1:
                            condition.put("type", "CAT");
                            break;
                        case 2:
                            condition.put("type", "FISH");
                            break;
                        case 3:
                            condition.put("type", "RABBIT");
                            break;
                        default:
                            break;
                    }
                    condition.put("name", name.getText().toString());
                    condition.put("age", Integer.parseInt(age.getText().toString()));
                    condition.put("gender", gender.getSelectedItem());
                    condition.put("color", color.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(SearchPetActivity.this, ShowResultActivity.class);
                intent.putExtra("requestCode", RequestCode.SEARCH);     //只做查询
                startActivity(intent);
            }
        });
    }
}
