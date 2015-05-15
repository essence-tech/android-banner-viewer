package com.essencedigital.essencebanners;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.util.List;


public class PreviewActivity extends ActionBarActivity {

    private WebView webView;
    private int dimX;
    private int dimY;
    private String url;


    private int pxToDp (int dim) {
        return (int) (dim * getResources().getDisplayMetrics().density);
    }

    private String scrutineerTransform(Uri initial) {
        List<String> parts = initial.getPathSegments();
        return "https://www.scrutineer.net/preview/"+parts.get(2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_PROGRESS);

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent.getAction() == Intent.ACTION_VIEW) {
            Uri tmpUri = intent.getData();
            url = scrutineerTransform(tmpUri);

        } else {
            url = intent.getStringExtra(MainActivity.EXTRA_URI);
        }

        setContentView(R.layout.activity_preview);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);



        final Activity activity = this;
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
            }
        });

        if (savedInstanceState == null) {
            webView.loadUrl(url);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webView.restoreState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        WebView webView = (WebView) findViewById(R.id.webView);
        ViewGroup.LayoutParams lp = webView.getLayoutParams();

        switch (id) {
            case R.id.banner300x250:
                dimX = pxToDp(300);
                dimY = pxToDp(250);
                break;
            case R.id.banner300x600:
                dimX = pxToDp(300);
                dimY = pxToDp(600);
                break;
            case R.id.banner320x416:
                dimX = pxToDp(320);
                dimY = pxToDp(416);
                break;
            case R.id.banner320x480:
                dimX = pxToDp(320);
                dimY = pxToDp(480);
                break;
            case R.id.banner728x90:
                dimX = pxToDp(728);
                dimY = pxToDp(90);
                break;
            case R.id.banner160x600:
                dimX = pxToDp(160);
                dimY = pxToDp(600);
                break;
            case R.id.bannerFullscreen:
                lp.width = pxToDp(10000);
                lp.height = pxToDp(10000);
                break;
            default:
                break;
        }

        lp.width = dimX;
        lp.height = dimY;
        webView.setLayoutParams(lp);
        webView.loadUrl(url);
        return super.onOptionsItemSelected(item);
    }

    public void reload(View view) {
        webView.clearCache(true);
        webView.reload();
        webView.loadUrl(url);
    }
}
