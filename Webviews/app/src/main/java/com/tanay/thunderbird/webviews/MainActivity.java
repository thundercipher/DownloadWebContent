package com.tanay.thunderbird.webviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView)findViewById(R.id.webView);
        webView.getSettings().getJavaScriptEnabled();                   // to enable the usage of JavaScript in the app
        webView.setWebViewClient(new WebViewClient());                  // if this command is not there, when we try to access the web from the app, the default browser of the phone opens up

        webView.loadUrl("https://www.google.com");
        //webView.loadData("<html><body><h1>Hi there!</h1><p>This is my webpage</p></body></html>", "text/html", "UTF-8");      // we can also operate html pages and stuff

    }
}
