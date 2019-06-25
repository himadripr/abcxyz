package com.baseinfotech.juscep.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baseinfotech.juscep.R;
import com.baseinfotech.juscep.adapter.EquipmentQuantityAdapter;
import com.baseinfotech.juscep.model.Equipment;
import com.baseinfotech.juscep.model.EquipmentsRowData;
import com.baseinfotech.juscep.model.PostCallCallback;
import com.baseinfotech.juscep.model.User;
import com.baseinfotech.juscep.utility.ApiUtility;
import com.baseinfotech.juscep.utility.ApplicationConstants;
import com.baseinfotech.juscep.utility.AsyncPostCall;
import com.baseinfotech.juscep.utility.SharedPreferenceUtil;
import com.baseinfotech.juscep.utility.Utilities;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TextView userMobileNumberHeaderTextView;
    private RecyclerView booking_recycler_view;
    private EquipmentQuantityAdapter equipmentQuantityAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText placeText, dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Booking");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        userMobileNumberHeaderTextView = (TextView) headerView.findViewById(R.id.user_mobile_number_header_textview);
        userMobileNumberHeaderTextView.setText(SharedPreferenceUtil.getLoggedInUSer(this).getMobileNumber());
        placeText = findViewById(R.id.place_edit_text);
        dateText = findViewById(R.id.date_edit_text);
        configureSettingsForDate();
        loadEquipmentList();
    }

    private void configureSettingsForDate(){
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        Calendar newCalender = Calendar.getInstance();
        dateText.setFocusable(false);
        dateText.setClickable(true);
        dateText.setLongClickable(false);
        final DatePickerDialog pickDateDialog = new DatePickerDialog(this , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dateText.setText(simpleDateFormat.format(newDate.getTime()));
            }
        }, newCalender.get(Calendar.YEAR), newCalender.get(Calendar.MONTH), newCalender.get(Calendar.DAY_OF_MONTH));
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDateDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void loadEquipmentList(){


        String url = ApiUtility.equipmentListApi;
        final ProgressDialog progressDialog = ProgressDialog.show(BookingActivity.this, "loading....","");

//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(ApiUtility.equipmentListApi)
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                if (!response.isSuccessful()) {
//                    throw new IOException("Unexpected code " + response);
//                } else {
//                    // do something wih the result
//                    String value = response.body().string();
//
//                    if (!value.isEmpty()){
//                        JsonParser parser = new JsonParser();
//                        JsonElement tradeElement = parser.parse(value);
//                        JsonArray result = tradeElement.getAsJsonArray();
//                        progressDialog.dismiss();
//                        List<Equipment> equipmentList = new ArrayList<>();
//
//                        for (int i=0; i<result.size(); i++){
//                            JsonObject jsonObject = (JsonObject) result.get(i);
//                            Equipment equipment = new Equipment();
//                            equipment.setId(jsonObject.get("id").getAsString());
//                            equipment.setName(jsonObject.get("name").getAsString());
//                            equipmentList.add(equipment);
//
//                        }
//                        createEquipmentQuantityAdapterAssignToRecyclerView(equipmentList);
//                    }
//                    System.out.println(value);
//                }
//            }
//        });

//        try {
//            Response response = client.newCall(request).execute();
//            try {
//                String value = response.body().string();
//                if (!value.isEmpty()){
//
//                }
//                System.out.println(value);
//            } catch (Throwable t){
//                //response = null;
//
//            }
//
//            // Do something with the response.
//        } catch (IOException e) {
//            //response = null;
//            e.printStackTrace();
//
//        }


        Ion.with(this)
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        progressDialog.dismiss();
                        List<Equipment> equipmentList = new ArrayList<>();

                        if (e==null){
                            for (int i=0; i<result.size(); i++){
                                JsonObject jsonObject = (JsonObject) result.get(i);
                                Equipment equipment = new Equipment();
                                equipment.setId(jsonObject.get("id").getAsString());
                                equipment.setName(jsonObject.get("name").getAsString());
                                equipmentList.add(equipment);

                            }
                            createEquipmentQuantityAdapterAssignToRecyclerView(equipmentList);

                        } else {
                            //Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            equipmentList.add(new Equipment("499", "Jcb Extractor"));
                            equipmentList.add(new Equipment("500", "Pocklen Excavator"));
                            equipmentList.add(new Equipment("501", "Hydra Crane"));
                            equipmentList.add(new Equipment("502", "Tractor Crane"));
                            equipmentList.add(new Equipment("503", "Water Tank Lorry"));
                            equipmentList.add(new Equipment("504", "Road Roller"));
                            equipmentList.add(new Equipment("505", "Vibrator Road Rollar"));
                            createEquipmentQuantityAdapterAssignToRecyclerView(equipmentList);

                        }

                    }
                });

    }

    private void createEquipmentQuantityAdapterAssignToRecyclerView(List<Equipment> equipmentList){
        booking_recycler_view = (RecyclerView) findViewById(R.id.booking_recycler_view);
        booking_recycler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        booking_recycler_view.setLayoutManager(mLayoutManager);

        equipmentQuantityAdapter = new EquipmentQuantityAdapter(equipmentList, this);
        booking_recycler_view.setAdapter(equipmentQuantityAdapter);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_my_account:
                startActivity(new Intent(this, MyAccountActivity.class));
                break;
            case R.id.nav_log_out:
                showLogOutAlertDialog();
                break;
            case R.id.nav_change_password:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.nav_booking:

                break;
            case R.id.nav_gallery:
                startActivity(new Intent(this, GalleryActivity.class));
                break;
            case R.id.nav_about_us:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.nav_faq:
                startActivity(new Intent(this, FAQActivity.class));
                break;
            case R.id.nav_privacy_policy:
                startActivity(new Intent(this, PrivacyPolicyActivity.class));
                break;
            case R.id.nav_contact_us:
                startActivity(new Intent(this, ContactUsActivity.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLogOutAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Log Out");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceUtil.clearUserLoggedIn(BookingActivity.this);
                startActivity(new Intent(BookingActivity.this, LaunchingActivity2.class));
                BookingActivity.this.finish();
            }
        });
        builder.show();
    }

    public void onAdd(View view) {
        equipmentQuantityAdapter.addItemToLast();
        booking_recycler_view.smoothScrollToPosition(equipmentQuantityAdapter.getItemCount());
    }

    public void onDelete(View view) {
        equipmentQuantityAdapter.deleteLastItem();
        booking_recycler_view.smoothScrollToPosition(equipmentQuantityAdapter.getItemCount());
    }

    public void onSubmit(View view) {
        String place = placeText.getText().toString();
        String date = dateText.getText().toString();
        if (place.equals("") || date.equals("")){
            Utilities.showBasicAlertDialog(this, "Empty data", "Please fill all the fields");
            return;
        }
        String userid = SharedPreferenceUtil.getLoggedInUSer(this).getId();
        String url = ApiUtility.bookingApi.get(SharedPreferenceUtil.getLoggedInUSer(this).getUserType());

        HashMap<String, String> keyValueMap = new HashMap<>();
        keyValueMap.put("user_id", userid);
        keyValueMap.put("ddate", date);
        keyValueMap.put("citydistrict", place);

        List<EquipmentsRowData> equipmentsRowDataList = equipmentQuantityAdapter.getEquipmentsRowDataList();
        for (int i=0; i<equipmentsRowDataList.size(); i++){
            EquipmentsRowData equipmentsRowData = equipmentsRowDataList.get(i);
            if (equipmentsRowData.getEquipment()==null || equipmentsRowData.getEquipment().getId().equals("-1")){
                Utilities.showBasicAlertDialog(this, "Empty data", "Please select an equipment.");
                return;
            }
            if (equipmentsRowData.getDays().trim().equals("") || equipmentsRowData.getQuantity().trim().equals("")){
                Utilities.showBasicAlertDialog(this, "Empty data", "Please fill all the fields");
                return;
            }
            keyValueMap.put("qty[]", equipmentsRowData.getQuantity());
            keyValueMap.put("days[]", equipmentsRowData.getDays());
            keyValueMap.put("category[]", equipmentsRowData.getEquipment().getId());
        }
        new AsyncPostCall(this, url, "", keyValueMap, "", "Booking...", new PostCallCallback() {
            @Override
            public void processResponse(Response response, String id, String responseString) {
                if (responseString==null || responseString.isEmpty()){
                    Toast.makeText(BookingActivity.this, "Some problem occurred", Toast.LENGTH_LONG).show();
                } else if (responseString.equals("Booking Successfully")){
                    Toast.makeText(BookingActivity.this, responseString, Toast.LENGTH_LONG).show();
                    System.out.println("Response value: "+responseString);
                    startActivity(new Intent(BookingActivity.this, BookingActivity.class));
                    BookingActivity.this.finish();

                } else {
                    Toast.makeText(BookingActivity.this, "Some problem occurred", Toast.LENGTH_LONG).show();
                }
            }
        }).execute();

    }

    class DownloadAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
