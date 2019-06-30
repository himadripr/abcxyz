package com.baseinfotech.juscep.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.baseinfotech.juscep.R;
import com.baseinfotech.juscep.utility.ApiUtility;
import com.baseinfotech.juscep.utility.Utilities;

import me.biubiubiu.justifytext.library.JustifyTextView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About Us");

        WebView mywebview = (WebView) findViewById(R.id.webView);
        if (!Utilities.isNetworkAvailable(this)) {
            //Toast.makeText(this, "No internet connection..", Toast.LENGTH_SHORT).show();
        } else {
            // mywebview.loadUrl("https://www.javatpoint.com/");

            /*String data = "<html><body><h1>Hello, Javatpoint!</h1></body></html>";
            mywebview.loadData(data, "text/html", "UTF-8"); */

            //mywebview.loadUrl(ApiUtility.ABOUT_US_URL);
            //mywebview.getSettings().setJavaScriptEnabled(true);
            //mywebview.setWebViewClient(new AppWebViewClients(this));
            //mywebview.loadUrl(ApiUtility.ABOUT_US_URL);
        }

        String who_we_are_text = getString(R.string.who_we_are_text);
        JustifyTextView textView = (JustifyTextView) findViewById(R.id.who_we_text_view);
        textView.setText(who_we_are_text);
    }

    public class AppWebViewClients extends WebViewClient {
        private ProgressDialog progressBar;
        private Activity activity;
        AppWebViewClients(Activity activity) {
//            this.progressBar=progressBar;
            this.activity = activity;
            progressBar = ProgressDialog.show(activity, "", "loading...");
//            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
            progressBar.dismiss();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
