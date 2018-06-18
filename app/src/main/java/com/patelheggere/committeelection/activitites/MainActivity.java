package com.patelheggere.committeelection.activitites;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.patelheggere.committeelection.R;
import com.patelheggere.committeelection.model.ManagementModel;
import com.patelheggere.committeelection.model.PhoneNumbersModel;
import com.patelheggere.committeelection.model.VotingModel;
import com.patelheggere.committeelection.util.SharedPrefsHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.patelheggere.committeelection.util.AppConstants.FIRST_TIME;
import static com.patelheggere.committeelection.util.AppConstants.IS_VOTED;
import static com.patelheggere.committeelection.util.AppConstants.NAME;
import static com.patelheggere.committeelection.util.AppConstants.PHONE;

public class MainActivity extends AppCompatActivity {

    private Spinner mChairmanSpinner,mSecretarySpinner, mTreasurerSpinner;
    private Button mSubmitButton, btnReload;
    private TextView textViewAlreadyVoted, textViewName;
    private PhoneNumbersModel phoneNumbersModel;
    private LinearLayout ll_details, ll_voted;
    private DatabaseReference databaseReference;
    private ArrayAdapter<String> ChairmanAdapter, SecretaryAdapter, TreasurerAdapter;
    private List<ManagementModel> managementModelList;
    private List<String> managemnetName;
    private List<String> ChairmanName;
    private List<String> SecretaryName;
    private List<String> TreasurerName;


    private String SelectedChairman, SelectedSec, SelectedTreasurer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        managementModelList = new ArrayList<>();
        phoneNumbersModel = new PhoneNumbersModel();
        phoneNumbersModel = getIntent().getParcelableExtra("DETAILS");
        if(phoneNumbersModel!=null)
        {
            SharedPrefsHelper.getInstance().save(NAME, phoneNumbersModel.getName());
            SharedPrefsHelper.getInstance().save(PHONE, phoneNumbersModel.getPhone());
            SharedPrefsHelper.getInstance().save(IS_VOTED, phoneNumbersModel.isVoted());
        }
        if(SharedPrefsHelper.getInstance().get(FIRST_TIME, true))
        {

        }
        else {
            phoneNumbersModel = new PhoneNumbersModel();
            phoneNumbersModel.setName(SharedPrefsHelper.getInstance().get(NAME,null).toString());
            phoneNumbersModel.setPhone(SharedPrefsHelper.getInstance().get(PHONE, null).toString());
            phoneNumbersModel.setVoted(SharedPrefsHelper.getInstance().get(IS_VOTED, false));
        }
        initializeViewComponents();
        getPersonDetails();
        getDetails();
    }

    private void getPersonDetails() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("phone_numbers").child(phoneNumbersModel.getPhone());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PhoneNumbersModel ob = new PhoneNumbersModel();
                ob = dataSnapshot.getValue(PhoneNumbersModel.class);
                if(ob.isVoted())
                {
                    ll_details.setVisibility(View.GONE);
                    ll_voted.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDetails() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("management_numbers");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren())
                {
                    ManagementModel ob = new ManagementModel();
                    ob = child.getValue(ManagementModel.class);
                    managementModelList.add(ob);
                }
                managemnetName = new ArrayList<String>();
                managemnetName.add("Select");
                for(int i = 0;i<managementModelList.size();i++)
                {
                    managemnetName.add(managementModelList.get(i).getName());
                }
                ChairmanName = new ArrayList<String>(managemnetName);
                SecretaryName = new ArrayList<String>(managemnetName);
                TreasurerName = new ArrayList<String>(managemnetName);

                ChairmanAdapter = new ArrayAdapter<String>( MainActivity.this, android.R.layout.simple_spinner_item, ChairmanName);
                // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
                ChairmanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Applying the adapter to our spinner
                mChairmanSpinner.setAdapter(ChairmanAdapter);
                mChairmanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SelectedChairman = ChairmanName.get(position);
                        if(SelectedChairman.equalsIgnoreCase(SelectedSec))
                        {
                            SelectedChairman="";
                            Toast.makeText(MainActivity.this, "You Cant This Person Already Selected as Secretary", Toast.LENGTH_SHORT).show();
                        }
                        else if(SelectedChairman.equalsIgnoreCase(SelectedTreasurer))
                        {
                            SelectedChairman="";
                            Toast.makeText(MainActivity.this, "You Cant This Person Already Selected as Treasurer", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                SecretaryAdapter = new ArrayAdapter<String>( MainActivity.this, android.R.layout.simple_spinner_item, SecretaryName);
                // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
                SecretaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Applying the adapter to our spinner
                mSecretarySpinner.setAdapter(SecretaryAdapter);
                mSecretarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SelectedSec = SecretaryName.get(position);
                       if(SelectedSec.equalsIgnoreCase(SelectedChairman))
                       {
                           SelectedSec="";
                           Toast.makeText(MainActivity.this, "You Cant This Person Already Selected as Chairman", Toast.LENGTH_SHORT).show();
                       }
                       else if(SelectedSec.equalsIgnoreCase(SelectedTreasurer))
                       {
                           SelectedSec="";
                           Toast.makeText(MainActivity.this, "You Cant This Person Already Selected as Treasurer", Toast.LENGTH_SHORT).show();
                       }
                       // ChairmanAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                TreasurerAdapter = new ArrayAdapter<String>( MainActivity.this, android.R.layout.simple_spinner_item, TreasurerName);
                // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
                TreasurerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Applying the adapter to our spinner
                mTreasurerSpinner.setAdapter(TreasurerAdapter);

                mTreasurerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SelectedTreasurer = TreasurerName.get(position);
                        if(SelectedTreasurer.equalsIgnoreCase(SelectedSec))
                        {
                            SelectedTreasurer="";
                            Toast.makeText(MainActivity.this, "You Cant This Person Already Selected as Secretary", Toast.LENGTH_SHORT).show();
                        }
                        else if(SelectedTreasurer.equalsIgnoreCase(SelectedChairman))
                        {
                            SelectedTreasurer="";
                            Toast.makeText(MainActivity.this, "You Cant This Person Already Selected as Chairman", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initializeViewComponents() {
        btnReload = findViewById(R.id.btn_reload);
        btnReload.setVisibility(View.GONE);
        mChairmanSpinner = findViewById(R.id.sp_chairman);
        mSecretarySpinner = findViewById(R.id.sp_secretary);
        mTreasurerSpinner = findViewById(R.id.sp_trouser);
        textViewAlreadyVoted = findViewById(R.id.tv_already_voted);
        textViewName = findViewById(R.id.tv_name);
        mSubmitButton = findViewById(R.id.btn_submit);

        ll_details = findViewById(R.id.linear_details);
        ll_voted = findViewById(R.id.linear_already);
        if(phoneNumbersModel.isVoted()) {
            ll_voted.setVisibility(View.VISIBLE);
            ll_details.setVisibility(View.GONE);
        }
        else {
            ll_voted.setVisibility(View.GONE);
            ll_details.setVisibility(View.VISIBLE);
        }
        if(phoneNumbersModel.getName()!=null)
        textViewName.setText(phoneNumbersModel.getName());
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SelectedChairman!="" && SelectedTreasurer!="" && SelectedSec!="")
                submitDetails();
                else {
                    Snackbar.make(findViewById(android.R.id.content), "Please Select memebers Correctly", Snackbar.LENGTH_LONG ).show();
                }
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeViewComponents();
                getPersonDetails();
                getDetails();
            }
        });
    }

    private void submitDetails() {
        // SharedPrefsHelper.getInstance().save(IS_VOTED, true);
        // ll_details.setVisibility(View.GONE);
        // ll_voted.setVisibility(View.VISIBLE);
        final VotingModel votingModel = new VotingModel();

        votingModel.setName(phoneNumbersModel.getName());
        votingModel.setPhone(phoneNumbersModel.getPhone());
        votingModel.setChairman(SelectedChairman);
        votingModel.setSecretary(SelectedSec);
        votingModel.setTreasurer(SelectedTreasurer);

        final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("CastedVotes").child(votingModel.getPhone());
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirm!!!");
        builder.setMessage("Are you Sure, once you have submitted you can't change? \n you have selected:\n Chairman:"+votingModel.getChairman()+"\n Secretary:"+votingModel.getSecretary()+"\n Treasurer:"+votingModel.getTreasurer());
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                databaseReference.setValue(votingModel);
                databaseReference = firebaseDatabase.getReference().child("phone_numbers").child(votingModel.getPhone());
                phoneNumbersModel.setVoted(true);
                SharedPrefsHelper.getInstance().save(IS_VOTED, true);
                databaseReference.setValue(phoneNumbersModel);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}
