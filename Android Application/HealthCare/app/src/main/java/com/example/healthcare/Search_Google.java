package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Search_Google extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_google);

        WebView webView = findViewById(R.id.web_view);

        Bundle bundle = getIntent().getExtras();

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());

        if(bundle==null){
            webView.loadUrl("https://www.google.com/");
        }
        else {
            String search_string = bundle.getString("key1");
            String val = "https://www.google.com/"+"search?q="+search_string;
            webView.loadUrl(val);

        }


    }
}