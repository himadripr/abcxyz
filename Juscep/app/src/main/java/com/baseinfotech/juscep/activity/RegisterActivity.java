package com.baseinfotech.juscep.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baseinfotech.juscep.R;
import com.baseinfotech.juscep.model.PostCallCallback;
import com.baseinfotech.juscep.utility.ApiUtility;
import com.baseinfotech.juscep.utility.ApplicationConstants;
import com.baseinfotech.juscep.utility.AsyncPostCall;
import com.baseinfotech.juscep.utility.Utilities;
import com.msg91.sendotpandroid.library.SendOtpVerification;
import com.msg91.sendotpandroid.library.Verification;
import com.msg91.sendotpandroid.library.VerificationListener;

import java.util.HashMap;

import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements VerificationListener{
    EditText mobileNumberForOTP;
    EditText mobileNumberText, otpText, nameText, emailText, aadhaarNumberText, panNumberText, addressText, cityText, stateText, passwordText, confirmPasswordText;
    EditText[] editTexts;
    LinearLayout topOtpLayout, bottomRegisterLayout;
    ProgressDialog progressDialog;
    Verification mVerification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mobileNumberForOTP = (EditText) findViewById(R.id.mobile_number_for_otp);
        topOtpLayout = (LinearLayout) findViewById(R.id.top_otp_layout);
        bottomRegisterLayout = (LinearLayout) findViewById(R.id.bottom_register_layout);
        mobileNumberText = (EditText) findViewById(R.id.mobile_number_text);
        otpText = (EditText) findViewById(R.id.otp_text);
        nameText = (EditText) findViewById(R.id.name_text);
        emailText = (EditText) findViewById(R.id.email_text);
        aadhaarNumberText = (EditText) findViewById(R.id.aadhaar_number_text);
        panNumberText = (EditText) findViewById(R.id.pan_number_text);
        addressText = (EditText) findViewById(R.id.address_text);
        cityText = (EditText) findViewById(R.id.city_text);
        stateText = (EditText) findViewById(R.id.state_text);
        passwordText = (EditText) findViewById(R.id.password_text);
        confirmPasswordText = (EditText) findViewById(R.id.confirm_password_text);
        editTexts = new EditText[11];
        editTexts[0] = mobileNumberText;
        editTexts[1] = otpText;
        editTexts[2] = nameText;
        editTexts[3] = emailText;
        editTexts[4] = aadhaarNumberText;
        editTexts[5] = panNumberText;
        editTexts[6] = addressText;
        editTexts[7] = cityText;
        editTexts[8] = stateText;
        editTexts[9] = passwordText;
        editTexts[10] = confirmPasswordText;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }

    @Override
    public void onInitiated(String response) {
        System.out.println(response);
        progressDialog.dismiss();
        topOtpLayout.setVisibility(View.GONE);
        bottomRegisterLayout.setVisibility(View.VISIBLE);
        mobileNumberText.setText(mobileNumberForOTP.getText().toString().trim());
        Toast.makeText(this, "OTP sent.", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onInitiationFailed(Exception paramException) {
        System.out.println(paramException.getMessage());
        topOtpLayout.setVisibility(View.VISIBLE);
        bottomRegisterLayout.setVisibility(View.GONE);
        Toast.makeText(this, paramException.getMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        findViewById(R.id.get_otp_button).setEnabled(true);
    }

    @Override
    public void onVerified(String response) {
        submitToServer();
    }

    @Override
    public void onVerificationFailed(Exception paramException) {
        findViewById(R.id.submit_button).setEnabled(true);
        Utilities.showBasicAlertDialog(this, "OTP verification failed", paramException.getMessage());
    }

    public void getOtp(View view) {
        EditText[] editTexts = new EditText[1];
        editTexts[0] = mobileNumberForOTP;
        if (isAnyTextFieldEmpty(editTexts)){
            Utilities.showBasicAlertDialog(this, "Alert", "Empty mobile number");
        } else if (!Utilities.isNetworkAvailable(this)){
            Utilities.displayOnSnackBar(this, view, "No internet connection", R.color.colorPrimaryDark);
        } else {
             findViewById(R.id.get_otp_button).setEnabled(false);
             checkWhetherMobileNumberIsAlreadyRegistered(mobileNumberForOTP.getText().toString().trim());
        }

    }

    private void sendOtpToMobileNumber(String mobileNumber){
        mVerification = SendOtpVerification.createSmsVerification
                (SendOtpVerification
                        .config("91" + mobileNumber)
                        .context(this)
                        .autoVerification(false)
                        .httpsConnection(false)//connection to be use in network calls
                        .expiry("30")//value in minutes
                        .senderId("JUSCEP") //where XXXX is any string
                        .otplength("4") //length of your otp max length up to 9 digits
                        //--------case 1-------------------
                        .message("Your OTP code is ##OTP##. It will expire in 30 minutes.")//##OTP## use for default generated OTP
                        //--------case 2-------------------
                        //-------------------------------------
                        //use single case at a time either 1 or 2
                        .build(), this);
        mVerification.initiate();
        progressDialog = ProgressDialog.show(this, "", "Verifying...");
    }

    private void checkWhetherMobileNumberIsAlreadyRegistered(final String mobileNumber){
        String url = ApiUtility.CHECK_USER_ALREADY_REGISTERED_URL;
        HashMap<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("mobile", mobileNumber);
        new AsyncPostCall(this, url, "", keyValueMap, "", "Verifying...", new PostCallCallback() {
            @Override
            public void processResponse(Response response, String id, String responseString) {
                if (responseString==null || responseString.trim().isEmpty()){
                    sendOtpToMobileNumber(mobileNumber);

                } else {
                    Toast.makeText(RegisterActivity.this, responseString, Toast.LENGTH_LONG).show();
                    findViewById(R.id.get_otp_button).setEnabled(true);
                }
            }
        }).execute();
    }

    public void onSubmitRegistrationDetails(View view) {
        if (isAnyTextFieldEmpty(editTexts)){
            Utilities.showBasicAlertDialog(this, "Alert", "Some fields are empty.");
        } else if (!isPasswordMathced()){
            Utilities.showBasicAlertDialog(this, "Alert", "Password did not match");
        } else if (!Utilities.isNetworkAvailable(this)){
            Utilities.showBasicAlertDialog(this, "Alert", "No internet connection");

        } else {
            findViewById(R.id.submit_button).setEnabled(false);
            mVerification.verify(otpText.getText().toString().trim());
        }
    }

    private boolean isPasswordMathced(){
        return passwordText.getText().toString().equals(confirmPasswordText.getText().toString());
    }

    /**
     * mobile:9756575925
     companyname:XYZ
     aadhar_no:1234567898765
     panno:DET6H9576H
     email:gjhj@gmail.com
     address:abc
     citydistrict:patna
     state:bihar
     password:b
     */
    public void submitToServer(){
        String url = ApiUtility.registrationApi.get(ApplicationConstants.SELECTED_USER_TYPE_FOR_LOGIN_OR_REGISTRATION);
        HashMap<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("mobile", mobileNumberText.getText().toString().trim());
        keyValueMap.put("companyname", nameText.getText().toString().trim());
        keyValueMap.put("aadhar_no", aadhaarNumberText.getText().toString().trim());
        keyValueMap.put("panno", panNumberText.getText().toString().trim());
        keyValueMap.put("email", emailText.getText().toString().trim());
        keyValueMap.put("address", addressText.getText().toString().trim());
        keyValueMap.put("citydistrict", cityText.getText().toString().trim());
        keyValueMap.put("state", stateText.getText().toString().trim());
        keyValueMap.put("password", passwordText.getText().toString());

        new AsyncPostCall(this, url, "", keyValueMap, "", "Submitting...", new PostCallCallback() {
            @Override
            public void processResponse(Response response, String id, String responseString) {
                if (responseString==null || responseString.isEmpty()){
                    findViewById(R.id.submit_button).setEnabled(true);
                    Toast.makeText(RegisterActivity.this, "Some problem occurred", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, responseString, Toast.LENGTH_LONG).show();
                    System.out.println("Response value: "+responseString);
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    RegisterActivity.this.finish();

                }
             }
        }).execute();

    }

    private boolean isAnyTextFieldEmpty(EditText[] editTexts){
        boolean isEmpty = false;
        for (EditText editText : editTexts) {
            String str = editText.getText().toString();
            if (editText.getInputType()!= InputType.TYPE_TEXT_VARIATION_PASSWORD && editText.getInputType()!= InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                str = str.trim();
            }
            if (str.isEmpty()) {
                isEmpty = true;
                break;
            }
        }
        return isEmpty;
    }



    public void onResendOtp(View view) {
        if (!Utilities.isNetworkAvailable(this)){
            Utilities.displayOnSnackBar(this, view, "No internet connection", R.color.colorPrimaryDark);

        } else {
            mVerification.resend("text");
            Toast.makeText(this, "OTP sent.", Toast.LENGTH_SHORT).show();
            findViewById(R.id.resend_otp_button).setVisibility(View.GONE);
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
