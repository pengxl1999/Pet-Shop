package com.pengxl.petshop;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.pengxl.petshop.util.Pet;

import java.util.regex.Pattern;

public class ChangeActivity extends AppCompatActivity {

    public static Pet pet;
    private Spinner type, gender;
    private EditText name, age, color;
    private ImageButton back;
    private Button clear, save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);


        init();
    }

    private void init() {
        back = (ImageButton) findViewById(R.id.change_back);
        clear = (Button) findViewById(R.id.change_clear);
        save = (Button) findViewById(R.id.change_save);
        type = (Spinner) findViewById(R.id.change_type);
        name = (EditText) findViewById(R.id.change_name);
        age = (EditText) findViewById(R.id.change_age);
        gender = (Spinner) findViewById(R.id.change_gender);
        color = (EditText) findViewById(R.id.change_color);

        setType();
        name.setText(pet.getName());
        age.setText(String.valueOf(pet.getAge()));
        setGender();
        color.setText(pet.getColor());

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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkInputs()) {
                    return;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeActivity.this)
                        .setTitle("确定要修改吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                savePetInfo();
                                finish();
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
    }

    private void setType() {
        switch (pet.getType()) {
            case "DOG":
                type.setSelection(0);
                break;
            case "CAT":
                type.setSelection(1);
                break;
            case "FISH":
                type.setSelection(2);
                break;
            case "RABBIT":
                type.setSelection(3);
                break;
            default:
                break;
        }
    }

    private void setGender() {
        switch (pet.getGender()) {
            case "UNKNOWN":
                gender.setSelection(0);
                break;
            case "MALE":
                gender.setSelection(1);
                break;
            case "FEMALE":
                gender.setSelection(2);
                break;
            default:
                break;
        }
    }

    private boolean checkInputs() {
        if(name.getText().toString().equals("")) {
            Toast.makeText(ChangeActivity.this,"请输入名字！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(age.getText().toString().equals("")) {
            Toast.makeText(ChangeActivity.this,"请输入年龄！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(color.getText().toString().equals("")) {
            Toast.makeText(ChangeActivity.this,"请输入颜色！", Toast.LENGTH_SHORT).show();
            return false;
        }
        Pattern pattern = Pattern.compile("^\\d{1,2}$");
        if(!pattern.matcher(age.getText().toString()).matches()) {
            Toast.makeText(ChangeActivity.this,"请输入合法的数字！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void savePetInfo() {
        pet.setType((String)type.getSelectedItem());
        pet.setName(name.getText().toString());
        pet.setAge(Integer.parseInt(age.getText().toString()));
        pet.setGender((String)gender.getSelectedItem());
        pet.setColor(color.getText().toString());
        Toast.makeText(ChangeActivity.this, "修改成功！",Toast.LENGTH_SHORT).show();
    }
}
