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
import com.pengxl.petshop.util.Pet;
import com.pengxl.petshop.util.PetListAdapter;
import com.pengxl.petshop.util.PetShop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private ListView petList;
    private ImageTextButton addPet, searchPet, deletePet, changePet;
    private boolean isLoaded = false;

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

        getFromServer();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if(isLoaded) {
                        PetListAdapter adapter = new PetListAdapter(MainActivity.this, R.layout.list_view_item, pets);
                        petList.setAdapter(adapter);
                        break;
                    }
                }
            }
        });
    }

    private void getFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                InputStream inputStream = null;
                OutputStream outputStream = null;
                BufferedReader bufferedReader = null;
                PrintWriter printWriter = null;
                String msg = null;
                String[] message;
                try {
                    socket = new Socket("39.106.219.88", 8088);
                    inputStream = socket.getInputStream();
                    outputStream = socket.getOutputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    printWriter = new PrintWriter(outputStream);
                    printWriter.println("3 " + account);
                    printWriter.flush();
                    while((msg = bufferedReader.readLine()) != null) {
                        message = msg.split(" ");
                        if(message.length == 5) {
                            Log.i("pengxl1999", "msg:" + msg);
                            Pet pet = new Pet(message[0]);
                            pet.setName(message[1]);
                            pet.setAge(Integer.parseInt(message[2]));
                            pet.setGender(message[3]);
                            pet.setColor(message[4]);
                            pets.add(pet);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(inputStream != null ) {
                            inputStream.close();
                        }
                        if(outputStream != null) {
                            outputStream.close();
                        }
                        if(printWriter != null) {
                            printWriter.close();
                        }
                        if(bufferedReader != null) {
                            bufferedReader.close();
                        }
                        if(socket != null) {
                            socket.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isLoaded = true;
            }
        }).start();
    }
}
