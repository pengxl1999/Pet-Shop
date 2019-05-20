package com.pengxl.petshop;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.pengxl.petshop.util.Pet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static com.pengxl.petshop.util.PetShop.account;
import static com.pengxl.petshop.util.PetShop.pets;

public class SignUpActivity extends AppCompatActivity {

    private EditText account, password, confirmPassword;
    private Button signUp;
    private ImageButton back;
    private boolean isSuccess = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
    }

    private void init() {
        account = (EditText) findViewById(R.id.sign_up_account);
        password = (EditText) findViewById(R.id.sign_up_password);
        confirmPassword = (EditText) findViewById(R.id.sign_up_confirm_password);

        back = (ImageButton) findViewById(R.id.sign_up_back);
        signUp = (Button) findViewById(R.id.sign_up_sign_up_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
    }

    private void commit() {
        if(!checkInputs()) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                InputStream inputStream = null;
                OutputStream outputStream = null;
                BufferedReader bufferedReader = null;
                PrintWriter printWriter = null;
                String msg;
                try {
                    socket = new Socket("39.106.219.88", 8088);
                    outputStream = socket.getOutputStream();
                    inputStream = socket.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    printWriter = new PrintWriter(outputStream);
                    printWriter.println("0 " + account.getText().toString() + " " + password.getText().toString());
                    printWriter.flush();
                    msg = bufferedReader.readLine();
                    if(msg.equals("good")) {
                        isSuccess = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if(outputStream != null) {
                            outputStream.close();
                        }
                        if(printWriter != null) {
                            printWriter.close();
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
        int count = 0;
        while(true) {
            if(isSuccess) {
                Toast.makeText(SignUpActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                break;
            }
            count++;
            if(count > 1000) {
                Toast.makeText(SignUpActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        finish();
    }

    private boolean checkInputs() {
        if(account.getText().toString().equals("") || password.getText().toString().equals("")
            || confirmPassword.getText().toString().equals("")) {
            Toast.makeText(SignUpActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!password.getText().toString().equals(confirmPassword.getText().toString())) {
            password.setText("");
            confirmPassword.setText("");
            Toast.makeText(SignUpActivity.this, "密码不相同", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
