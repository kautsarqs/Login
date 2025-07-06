package com.ktsrqs.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Mahasiswa extends AppCompatActivity {
    private FloatingActionButton _addButton, _refreshButton;

    private RecyclerView _recyclerView1;
    private List <MahasiswaModel> mahasiswaModelList;

    private MahasiswaAdapter ma;
    private TextView _txtMahasiswaCount, _txtSearch;

    private ImageButton _btnSearch;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mahasiswa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _recyclerView1 = findViewById(R.id.recyclerView1);
        _txtMahasiswaCount = findViewById(R.id.txtTotal);

        loadRecyclerView();
        initAddButton();
        initRefreshButton();
        initSearch();
    }

    private void initSearch() {
        _txtSearch = findViewById(R.id.txtSearch);
        _txtSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                String filterText = _txtSearch.getText().toString();
                if (!filterText.isEmpty()) {
                    filter(filterText);
                } else {
                    loadRecyclerView();
                }
                return false;
            }
        });
    }

    private void filter(String text) {
        List<MahasiswaModel> filteredList = new ArrayList<>();

        for (MahasiswaModel item : mahasiswaModelList) {
            if (item.getNama().toLowerCase().contains(text.toLowerCase())) {
//                Toast.makeText(Mahasiswa.this, "test", Toast.LENGTH_SHORT).show(); // Ini hanya untuk debugging
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(Mahasiswa.this, "No Data Found...", Toast.LENGTH_SHORT).show();
        } else {
            ma.filter(filteredList);
        }
    }

    private void loadRecyclerView() {
        AsyncHttpClient ahc = new AsyncHttpClient();
        String url = "https://stmikpontianak.cloud/011100862/tampilMahasiswa.php"; // Ganti dengan URL API Anda

        ahc.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Gson g = new Gson();
                mahasiswaModelList = g.fromJson(new String(responseBody), new TypeToken<List<MahasiswaModel>>(){}.getType());

                RecyclerView.LayoutManager lm = new LinearLayoutManager(Mahasiswa.this);
                _recyclerView1.setLayoutManager(lm);

                ma = new MahasiswaAdapter(mahasiswaModelList);
                _recyclerView1.setAdapter(ma);

                String mahasiswaCount = "Total Mahasiswa : " + ma.getItemCount();
                _txtMahasiswaCount.setText(mahasiswaCount);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initAddButton() {
        _addButton = findViewById(R.id.addDButton); // Sesuaikan ID tombol Anda
        _addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), AddMahasiswa.class);
                startActivity(intent);

            }
        });
    }

    private void initRefreshButton() {
        _refreshButton = findViewById(R.id.refreshButton); // Sesuaikan ID tombol Anda
        _refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRecyclerView();
            }
        });
    }
}