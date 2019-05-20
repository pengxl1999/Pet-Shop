package com.pengxl.petshop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.pengxl.petshop.util.Pet;
import com.pengxl.petshop.util.PetShop;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.regex.Pattern;

import static com.pengxl.petshop.util.PetShop.pets;

public class AddPetActivity extends AppCompatActivity {

    private Spinner type, gender;
    private EditText name, age, color;
    private ImageButton back;
    private Button clear, save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        init();
    }

    private void init() {
        back = (ImageButton) findViewById(R.id.add_pet_back);
        clear = (Button) findViewById(R.id.add_pet_clear);
        save = (Button) findViewById(R.id.add_pet_save);
        type = (Spinner) findViewById(R.id.add_pet_type);
        name = (EditText) findViewById(R.id.add_pet_name);
        age = (EditText) findViewById(R.id.add_pet_age);
        gender = (Spinner) findViewById(R.id.add_pet_gender);
        color = (EditText) findViewById(R.id.add_pet_color);

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
                Pet pet;
                if(!checkInputs()) {
                    return;
                }
                switch (type.getSelectedItemPosition()) {
                    case 0:
                        pet = new Pet("DOG");
                        pet.setName(name.getText().toString());
                        pet.setAge(Integer.parseInt(age.getText().toString()));
                        pet.setGender((String)gender.getSelectedItem());
                        pet.setColor(color.getText().toString());
                        pets.add(pet);
                        break;
                    case 1:
                        pet = new Pet("CAT");
                        pet.setName(name.getText().toString());
                        pet.setAge(Integer.parseInt(age.getText().toString()));
                        pet.setGender((String)gender.getSelectedItem());
                        pet.setColor(color.getText().toString());
                        pets.add(pet);
                        break;
                    case 2:
                        pet = new Pet("FISH");
                        pet.setName(name.getText().toString());
                        pet.setAge(Integer.parseInt(age.getText().toString()));
                        pet.setGender((String)gender.getSelectedItem());
                        pet.setColor(color.getText().toString());
                        pets.add(pet);
                        break;
                    case 3:
                        pet = new Pet("RABBIT");
                        pet.setName(name.getText().toString());
                        pet.setAge(Integer.parseInt(age.getText().toString()));
                        pet.setGender((String)gender.getSelectedItem());
                        pet.setColor(color.getText().toString());
                        pets.add(pet);
                        break;
                    default:
                        break;
                }
                addToServer();
                type.setSelection(0);
                name.setText("");
                age.setText("");
                gender.setSelection(0);
                color.setText("");
                Toast.makeText(AddPetActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkInputs() {
        if(name.getText().toString().equals("")) {
            Toast.makeText(AddPetActivity.this,"请输入名字！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(age.getText().toString().equals("")) {
            Toast.makeText(AddPetActivity.this,"请输入年龄！", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(color.getText().toString().equals("")) {
            Toast.makeText(AddPetActivity.this,"请输入颜色！", Toast.LENGTH_SHORT).show();
            return false;
        }
        Pattern pattern = Pattern.compile("^\\d{1,2}$");
        if(!pattern.matcher(age.getText().toString()).matches()) {
            Toast.makeText(AddPetActivity.this,"请输入合法的数字！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void addToServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                OutputStream outputStream = null;
                BufferedWriter bufferedWriter = null;
                try {
                    socket = new Socket("39.106.219.88", 8088);
                    outputStream = socket.getOutputStream();
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                    if(pets.size() == 0) {
                        bufferedWriter.write("2 " + PetShop.account + "\n");
                        bufferedWriter.flush();
                    }
                    for(Pet pet : pets) {
                        String s = pet.getType() + " " + pet.getName() + " " + pet.getAge() + " " + pet.getGender()
                                + " " + pet.getColor();
                        bufferedWriter.write("2 " + PetShop.account + " " + s + "\n");
                        bufferedWriter.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(outputStream != null ) {
                            outputStream.close();
                        }
                        if(bufferedWriter != null) {
                            bufferedWriter.close();
                        }
                        if(socket != null) {
                            socket.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
