package com.ktsrqs.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private String _id;
    private String _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText _idInput = findViewById(R.id.user_id);
        EditText _passInput = findViewById(R.id.user_password);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(v ->{
            _id = _idInput.getText().toString();
            _password = _passInput.getText().toString();
            String url = "https://stmikpontianak.cloud/011100862/login.php?id="+_id+"&password="+_password;

            asyncHttpClient.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String json = new String(responseBody, "UTF-8");

                        // Deserialize JSON to object
                        Gson gson = new Gson();
                        Type userListType = new TypeToken<List<LoginModel>>() {}.getType();
                        List<LoginModel> users = gson.fromJson(json, userListType);

                        if(users.get(0).idCount.equals("0")){
                            Toast.makeText(MainActivity.this, "Id atau password tidak cocok", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Intent menu = new Intent(getApplicationContext(),MenuActivity.class);
                        startActivity(menu);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

    }


}
