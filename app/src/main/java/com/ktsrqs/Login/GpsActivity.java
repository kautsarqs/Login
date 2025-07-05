package com.ktsrqs.Login;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GpsActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        webView = findViewById(R.id.wvMain);

        TextView textViewCoordinat = findViewById(R.id.textView_koordinat);

        Bundle param = getIntent().getBundleExtra("param");
        textViewCoordinat.setText(param.getDouble("lat") + "x" + param.getDouble("lon"));

        String url = "https://www.google.com/maps" +
                "?q="+ param.getDouble("lat") + "," + param.getDouble("lon") +
                "&ll=" + param.getDouble("lat") + "," + param.getDouble("lon") +
                "&z=10";

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }
}