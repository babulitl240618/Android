package com.imagine.bd.hayvenapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.imagine.bd.hayvenapp.MainActivity2;
import com.imagine.bd.hayvenapp.Model.SigninResponse;
import com.imagine.bd.hayvenapp.Model.user;
import com.imagine.bd.hayvenapp.R;
import com.imagine.bd.hayvenapp.Retrofit.ApiClient;
import com.imagine.bd.hayvenapp.Retrofit.ApiInterface;
import com.imagine.bd.hayvenapp.Retrofit.IRetrofit;
import com.imagine.bd.hayvenapp.Retrofit.ServiceGenerator;
import com.imagine.bd.hayvenapp.utils.API_URL;
import com.imagine.bd.hayvenapp.utils.AppConstant;
import com.imagine.bd.hayvenapp.utils.PersistData;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.ServerSocket;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    private TextView tvLogin;
    private EditText etName, etpass;
    private Context con;
    String notificationToken = "";
    private CheckBox signCheckBox1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin2);
        con = this;
        inittUI();
    }


    public void inittUI() {
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        etName = (EditText) findViewById(R.id.etName);
        etpass = (EditText) findViewById(R.id.etPass);
        signCheckBox1 = (CheckBox) findViewById(R.id.signCheckBox1);

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        notificationToken = FirebaseInstanceId.getInstance().getToken();

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(SigninActivity.this, MainActivity2.class));
                String name = etName.getText().toString();
                String pass = etpass.getText().toString();

                FirebaseMessaging.getInstance().subscribeToTopic("news");
                notificationToken = FirebaseInstanceId.getInstance().getToken();

                Log.e("email", name);
                Log.e("pass", pass);
                //  Log.e("token", notificationToken);

                //requestSignin("rabbi@gmail.com","123456","fhdhfjfjd");
                // requestRoom("rabbi@gmail.com","123456","fhdhfjfjd");

                if (!TextUtils.isEmpty(notificationToken)) {
                    // Log.e("token", notificationToken);
                    //  Toast.makeText(con, notificationToken, Toast.LENGTH_LONG).show();
                    requestSignin(name, pass, notificationToken);
                } else {
                    Toast.makeText(con, "not found firebase id", Toast.LENGTH_LONG).show();
                    // requestSignin(name, pass, notificationToken);
                }

            }
        });
    }


    public void requestSignin(String email, String pass, String gcm_id) {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", pass);
        jsonObject.addProperty("gcm_id", gcm_id);

        // Using the Retrofit
        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class, API_URL.BASE_URL);
        Call<JsonObject> call = jsonPostService.postRawJSON(jsonObject);
        call.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    Log.e("response-success", response.body().toString());

                    if (signCheckBox1.isChecked()) {
                        PersistData.setStringData(con, AppConstant.isSignin, "true");
                    } else {
                        PersistData.setStringData(con, AppConstant.isSignin, "flase");
                    }

                    JsonParser parser = new JsonParser();
                    JsonElement mJson = parser.parse(response.body().toString());
                    Gson gson = new Gson();
                    SigninResponse object = gson.fromJson(mJson, SigninResponse.class);


                    if (object.getMsg().equalsIgnoreCase("true")) {
                        Log.e("id", object.getUser().getId());
                        Log.e("name", object.getUser().getFullname());
                        Log.e("size", object.getAlluserlist().size() + " tst");

                        PersistData.setStringData(con, AppConstant.userId, object.getUser().getId());
                        PersistData.setStringData(con, AppConstant.userName, object.getUser().getFullname());

                        Toast.makeText(con, "login success!", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SigninActivity.this, MainActivity2.class));

                        if (object.getAlluserlist().size() > 0) {

                            for (int i = 0; object.getAlluserlist().size() > i; i++) {

                                if (object.getAlluserlist().get(i).getId().equalsIgnoreCase(PersistData.getStringData(con, AppConstant.userId))) {
                                    object.getAlluserlist().remove(i);
                                }
                            }

                            AppConstant.alluserlist = object.getAlluserlist();
                        }
                        finish();
                    } else {
                        Toast.makeText(con, "login failed", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("response-failure", call.toString());
            }

        });
    }

}
