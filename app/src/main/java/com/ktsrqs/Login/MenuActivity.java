package com.ktsrqs.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView goMahasiswa = findViewById(R.id.go_mahasiswa);
        goMahasiswa.setOnClickListener(v ->{
            Intent mahasiswa = new Intent(MenuActivity.this,Mahasiswa.class);
            startActivity(mahasiswa);
        });
        TextView goForex = findViewById(R.id.go_forex);
        goForex.setOnClickListener(v ->{
            Intent forex = new Intent(MenuActivity.this,ForexActivity.class);
            startActivity(forex);
        });
        TextView goCuaca = findViewById(R.id.go_cuaca);
        goCuaca.setOnClickListener(v ->{
            Intent cuaca = new Intent(MenuActivity.this,CuacaActivity.class);
            startActivity(cuaca);
        });

    }


}
