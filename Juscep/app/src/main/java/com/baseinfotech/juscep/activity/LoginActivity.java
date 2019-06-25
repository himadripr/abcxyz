package com.baseinfotech.juscep.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baseinfotech.juscep.R;
import com.baseinfotech.juscep.model.PostCallCallback;
import com.baseinfotech.juscep.model.User;
import com.baseinfotech.juscep.utility.ApiUtility;
import com.baseinfotech.juscep.utility.ApplicationConstants;
import com.baseinfotech.juscep.utility.AsyncPostCall;
import com.baseinfotech.juscep.utility.SharedPreferenceUtil;

import java.util.HashMap;

import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText mobileText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mobileText = (EditText) findViewById(R.id.mobile_number_text);
        passwordText = (EditText) findViewById(R.id.password_text);

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

    public void onLogin(View view) {
        submitToServer();
    }

    public void submitToServer(){
        String url = ApiUtility.loginApi;
        HashMap<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("identity", mobileText.getText().toString().trim());
        keyValueMap.put("password", passwordText.getText().toString());


        new AsyncPostCall(this, url, "", keyValueMap, "", "Logging...", new PostCallCallback() {
            @Override
            public void processResponse(Response response, String id, String responseString) {
                if (responseString==null || responseString.isEmpty()){
                    findViewById(R.id.submit_button).setEnabled(true);
                    Toast.makeText(LoginActivity.this, "Some problem occurred", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_LONG).show();
                    System.out.println("Response value: "+responseString);
                    User user = new User();
                    user.setMobileNumber(mobileText.getText().toString().trim());
                    user.setId(responseString);
                    user.setUserType(ApplicationConstants.SELECTED_USER_TYPE_FOR_LOGIN_OR_REGISTRATION);
                    SharedPreferenceUtil.setLoggedInUser(LoginActivity.this, user);
                    startActivity(new Intent(LoginActivity.this, BookingActivity.class));

                    LoginActivity.this.finish();

                }
            }
        }).execute();

    }
}
