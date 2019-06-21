package com.baseinfotech.juscep.utility;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;

import com.baseinfotech.juscep.model.PostCallCallback;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

public class AsyncPostCall extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<Activity> activityRef;
        private String url;
        private HashMap<String, String> keyValueMap;
        private PostCallCallback postCallCallback;
        private ProgressDialog progressDialog;
        private Response response;
        private String id;
        private String responseValue;
        private String progressDialogTitle, progressDialogMessage;
        private boolean isNetworkAvailable;

        public AsyncPostCall(Activity activity, String url, String id, HashMap<String, String> keyValueMap, String progressDialogTitle, String progressDialogMessage , PostCallCallback postCallCallback){
            this.url = url;
            this.postCallCallback = postCallCallback;
            this.id = id;
            this.keyValueMap = keyValueMap;
            this.progressDialogTitle = progressDialogTitle;
            this.progressDialogMessage = progressDialogMessage;
            activityRef = new WeakReference<Activity>(activity);
            isNetworkAvailable = Utilities.isNetworkAvailable(activity);

        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(activityRef.get(), progressDialogTitle, progressDialogMessage);
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (isNetworkAvailable){
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder builder = new FormBody.Builder();

                for (Map.Entry<String, String> entry: keyValueMap.entrySet()){
                    builder.add(entry.getKey(), entry.getValue());
                }

                RequestBody formBody = builder.build();

                Request request = new Request.Builder()
                        .url(this.url)
                        .post(formBody)
                        .build();

                try {
                    response = client.newCall(request).execute();
                    try {
                        String value = response.body().string();
                        if (!value.isEmpty()){
                            responseValue = value;
                        }
                        System.out.println(value);
                    } catch (Throwable t){
                        //response = null;
                        return false;
                    }
                    return true;
                    // Do something with the response.
                } catch (IOException e) {
                    //response = null;
                    e.printStackTrace();
                    return false;
                }
            }

            return false;
        }



        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            if (!isNetworkAvailable){
                Utilities.showBasicAlertDialog(activityRef.get(), "No internet connection", "");
            } else {
                postCallCallback.processResponse(response, id, responseValue);
            }

        }
    }