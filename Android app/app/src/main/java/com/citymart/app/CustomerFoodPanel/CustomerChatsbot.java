package com.citymart.app.CustomerFoodPanel;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;

import com.citymart.app.R;

public class CustomerChatsbot extends AppCompatActivity {

    String chefId, userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_chatsbot);

        chefId = getIntent().getStringExtra("chefId").trim();
        userid = getIntent().getStringExtra("userid").trim();

        WebView dialogflowWebView = findViewById(R.id.dialogflow_webview);

        WebSettings webSettings = dialogflowWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

//        dialogflowWebView.loadUrl("https://console.dialogflow.com/api-client/demo/embedded/YOUR_CLIENT_ACCESS_TOKEN");
        // dialogflowWebView.loadUrl("https://console.dialogflow.com/api-client/demo/embedded/1095da52-d908-49dd-8cc8-e66646df2a87");

        String dialogflowUrl = "https://console.dialogflow.com/api-client/demo/embedded/1095da52-d908-49dd-8cc8-e66646df2a87";
        dialogflowUrl += "?userid=" + userid + "&chefid=" + chefId;

        dialogflowWebView.loadUrl(dialogflowUrl);


    }
}


