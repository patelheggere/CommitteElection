package com.patelheggere.committeelection.activitites;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.patelheggere.committeelection.R;
import com.patelheggere.committeelection.model.PhoneNumbersModel;
import com.patelheggere.committeelection.util.SharedPrefsHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.patelheggere.committeelection.util.AppConstants.FIRST_TIME;
import static com.patelheggere.committeelection.util.AppConstants.IS_VOTED;
import static com.patelheggere.committeelection.util.AppConstants.NAME;
import static com.patelheggere.committeelection.util.AppConstants.PHONE;

public class RegisterMobActivity extends AppCompatActivity {

private DatabaseReference databaseReference;
private EditText mEditTextMobile, mEditTextOTP;
private TextView textViewNumberSent;
private Button buttonSubmit, btnOtp;
private LinearLayout ll1, otpLinearLyt;
private List<PhoneNumbersModel> phoneNumbersModelList;
private int randomNumber;
private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMobileNumbers();
        setContentView(R.layout.activity_register_mob);
        initializeViewComponents();

    }

    private void getMobileNumbers() {
        phoneNumbersModelList = new ArrayList<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("phone_numbers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren())
                {
                    PhoneNumbersModel ob = new PhoneNumbersModel();
                    ob = child.getValue(PhoneNumbersModel.class);
                    phoneNumbersModelList.add(ob);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeViewComponents() {
        ll1 = findViewById(R.id.ll1);
        otpLinearLyt = findViewById(R.id.linear_otp);
       mEditTextMobile = findViewById(R.id.et_mobile_number);
       buttonSubmit = findViewById(R.id.btn_mob_submit);
       btnOtp = findViewById(R.id.btn_otp_submit);
       mEditTextOTP = findViewById(R.id.et_otp);
       textViewNumberSent = findViewById(R.id.textView2);

       ll1.setVisibility(View.VISIBLE);
       otpLinearLyt.setVisibility(View.GONE);

       buttonSubmit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               boolean isFound = false;
               if(mEditTextMobile.getText().toString().length()==10) {
                   for (int i = 0; i < phoneNumbersModelList.size(); i++) {
                       if (mEditTextMobile.getText().toString().equalsIgnoreCase(phoneNumbersModelList.get(i).getPhone())) {
                           position = i;
                           isFound = true;
                           break;

                       }

                   }
                   if (isFound) {
                       otpLinearLyt.setVisibility(View.VISIBLE);
                       ll1.setVisibility(View.GONE);
                       SendOTP();
                   } else {
                       Snackbar.make(findViewById(android.R.id.content), "Your Number is not registered with us for voting please contact Admin/Concerened Person", Snackbar.LENGTH_LONG).show();

                   }
               }
               else
               {
                   Snackbar.make(findViewById(android.R.id.content), "Please enter 10 digit mobile Number", Snackbar.LENGTH_LONG).show();

               }
           }
       });

       btnOtp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               verifyOTP();
           }
       });

    }

    private void SendOTP() {
        String mobile = mEditTextMobile.getText().toString();
       Random random = new Random();
       randomNumber = random.nextInt((9999-1000)+1)+1000;
       String otp = "your OTP is "+randomNumber;
       textViewNumberSent.setText("OTP is Sent to "+mobile);
        String url = "http://bulksms.srushti.info/api/sendhttp.php?authkey=98994ArUXXszM0HlY57fb9c32&mobiles="+mobile+"&message="+otp+"& new&mobile&sender=OTPSERV&route=4";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //System.out.println("Resadsdf:"+response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // System.out.println("eerrwerwer");
                System.out.println(error.getMessage());
               // System.out.println(error.networkResponse.statusCode);
            }
        });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(jsonObjectRequest);
    }
    private void verifyOTP() {
        if(mEditTextOTP.getText().toString()!="") {
            if (randomNumber == Integer.parseInt(mEditTextOTP.getText().toString())) {
                SharedPrefsHelper.getInstance().save(FIRST_TIME, false);
                SharedPrefsHelper.getInstance().save(NAME, phoneNumbersModelList.get(position).getName());
                SharedPrefsHelper.getInstance().save(PHONE, phoneNumbersModelList.get(position).getPhone());
                SharedPrefsHelper.getInstance().save(IS_VOTED, phoneNumbersModelList.get(position).isVoted());
                moveNextActivity();
            } else {
                Snackbar.make(findViewById(android.R.id.content), "Please Enter Valid OTP recieved via SMS", Snackbar.LENGTH_LONG).show();
            }
        }
        else {
            Snackbar.make(findViewById(android.R.id.content), "OTP Cannot be Empty", Snackbar.LENGTH_LONG).show();
        }
    }

    private void moveNextActivity() {
        Intent intent =  new Intent(RegisterMobActivity.this, MainActivity.class);
        intent.putExtra("DETAILS", phoneNumbersModelList.get(position));
        startActivity(intent);
        finish();
    }
}
