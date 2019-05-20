package com.pengxl.petshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginActivity extends AppCompatActivity {
    private Socket socket;
    private OutputStream outputStream;
    private PrintWriter printWriter;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private EditText account, password;
    private Button login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        account = (EditText) findViewById(R.id.login_account);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login_login_btn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check(account.getText().toString(), password.getText().toString());
            }
        });
    }

    private void check(final String account, final String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("39.106.219.88", 8088);
                    outputStream = socket.getOutputStream();
                    printWriter = new PrintWriter(outputStream);
                    inputStreamReader = new InputStreamReader(socket.getInputStream());
                    bufferedReader = new BufferedReader(inputStreamReader);
                    printWriter.println("1 " + account + " " + password);
                    printWriter.flush();
                    String msg;
                    while((msg = bufferedReader.readLine()) != null) {
                        Log.i("pengxl1999", "msg:" + msg);
                        if (msg.equals("bad")) {
                            Looper.prepare();
                            Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
                            Looper.loop();
                            break;
                        } else if (msg.equals("good")) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "无法连接到服务器", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } finally {
                    try {
                        if(printWriter != null) {
                            printWriter.close();
                        }
                        if(outputStream != null) {
                            outputStream.close();
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
