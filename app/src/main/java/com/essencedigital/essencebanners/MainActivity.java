package com.essencedigital.essencebanners;

import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import android.view.View;
import android.content.Intent;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_URI = "com.essencedigital.essencebanners.URI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when user clicks the preview button
     */
    public void sendUrl(View view) {
        Intent intent = new Intent(this, PreviewActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_url);

        String url = editText.getText().toString();
        Uri uri = Uri.parse(url);

        intent.putExtra(EXTRA_URI, uri.toString());
        startActivity(intent);
    }
}
