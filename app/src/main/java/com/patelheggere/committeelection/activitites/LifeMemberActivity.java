package com.patelheggere.committeelection.activitites;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.patelheggere.committeelection.R;
import com.patelheggere.committeelection.model.PhoneNumbersModel;
import com.patelheggere.committeelection.model.VotingModel;
import com.patelheggere.committeelection.util.SharedPrefsHelper;

import java.util.ArrayList;
import java.util.List;

import static com.patelheggere.committeelection.util.AppConstants.CP;
import static com.patelheggere.committeelection.util.AppConstants.FIRST_TIME;
import static com.patelheggere.committeelection.util.AppConstants.ID;
import static com.patelheggere.committeelection.util.AppConstants.LM;
import static com.patelheggere.committeelection.util.AppConstants.NAME;
import static com.patelheggere.committeelection.util.AppConstants.P;
import static com.patelheggere.committeelection.util.AppConstants.PHONE;

public class LifeMemberActivity extends AppCompatActivity implements View.OnClickListener{

    private ActionBar mActionBar;
    private Spinner mPresidentSpinner, mSecretarySpinner, mTreasurerSpinner, mVPSpinner, mOSSpinner, mSpSpinner, mD1Spinner
            ,mD2Spinner,mD3Spinner, mD4Spinner, mD5Spinner, mD6Spinner, mD7Spinner, mD8Spinner, mD9Spinner, mD10Spinner, mD11Spinner,
            mD12Spinner, mD13Spinner, mD14Spinner, mD15Spinner, mD16Spinner, mD17Spinner;
    private Button mSubmitButton,mButtonNext, btnReload;
    private TextView textViewAlreadyVoted, textViewName;
    private TextView mTvSubmit;
    private PhoneNumbersModel phoneNumbersModel;
    private LinearLayout ll_next, ll_voted;
    private ScrollView scrollView;
    private ProgressBar progressBar;
    private DatabaseReference databaseReference;
    private ArrayAdapter<String> PresidentAdapter, VPAdapter, SVPAdapter, SecretaryAdapter, TreasurerAdapter, OSAdapter, d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12,
            d13, d14, d15, d16, d17;
    private List<PhoneNumbersModel> managementModelList;
    private List<String> managemnetName;
    private List<String> PresidentName;
    private List<String> VicePresidentName;
    private List<String> SVPresidentName;
    private List<String> SecretaryName;
    private List<String> OSecretaryName;
    private List<String> TreasurerName;
    private List<String> D1Name, D2Name, D3Name, D4Name, D5Name, D6Name, D7Name, D8Name, D9Name, D10Name, D11Name, D12Name,D13Name, D14Name, D15Name, D16Name, D17Name;
    private String pName, svpName, vpName, sName, osName, tName, d1Name, d2Name, d3Name, d4Name, d5Name, d6Name, d7Name, d8Name, d9Name, d10Name, d11Name, d12Name, d13Name, d14Name, d15Name, d16Name,d17Name;

    VotingModel votingModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_member);
        managementModelList = new ArrayList<>();
        phoneNumbersModel = new PhoneNumbersModel();
        votingModel = new VotingModel();
        //managementModelList = getIntent().getParcelableArrayListExtra("MEMBERS");
        phoneNumbersModel = getIntent().getParcelableExtra("DETAILS");
        if(phoneNumbersModel!=null)
        {
            SharedPrefsHelper.getInstance().save(NAME, phoneNumbersModel.getName());
            SharedPrefsHelper.getInstance().save(PHONE, phoneNumbersModel.getPhone());
            SharedPrefsHelper.getInstance().save(ID, phoneNumbersModel.getId());
            SharedPrefsHelper.getInstance().save(CP, phoneNumbersModel.isCp());
            SharedPrefsHelper.getInstance().save(P, phoneNumbersModel.isP());
            SharedPrefsHelper.getInstance().save(LM, phoneNumbersModel.isLm() );
            // SharedPrefsHelper.getInstance().save(IS_VOTED, phoneNumbersModel.isVoted());
        }
        if(SharedPrefsHelper.getInstance().get(FIRST_TIME, true))
        {

        }
        /*else {
            phoneNumbersModel = new PhoneNumbersModel();
            phoneNumbersModel.setName(SharedPrefsHelper.getInstance().get(NAME,null).toString());
            phoneNumbersModel.setPhone(SharedPrefsHelper.getInstance().get(PHONE, null).toString());
            phoneNumbersModel.setId(SharedPrefsHelper.getInstance().get(ID, null).toString());
            // phoneNumbersModel.setVoted(SharedPrefsHelper.getInstance().get(IS_VOTED, false));
        }*/
        initializeView();
        getPersonDetails();
        getDetails();
    }

    private void initializeView() {
        progressBar = findViewById(R.id.progress_bar);
        mTvSubmit = findViewById(R.id.btn_submit);
        mTvSubmit.setOnClickListener(this);

        mButtonNext = findViewById(R.id.btn_patron_act);
        mButtonNext.setOnClickListener(this);
        mButtonNext.setEnabled(false);

        mActionBar = getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setTitle(" ಆಜೀವ ಸದಸ್ಯರ ಆಯ್ಕೆ ");
        }

        mTvSubmit = findViewById(R.id.btn_submit);
        scrollView = findViewById(R.id.scroll_view);
        ll_voted = findViewById(R.id.ll_next);
        ll_voted.setVisibility(View.INVISIBLE);

        mPresidentSpinner = findViewById(R.id.sp_president1);
        mSpSpinner = findViewById(R.id.sp_svp);
        mVPSpinner = findViewById(R.id.sp_vp);
        mSecretarySpinner = findViewById(R.id.sp_secretary);
        mTreasurerSpinner = findViewById(R.id.sp_treasurer);
        mOSSpinner = findViewById(R.id.sp_osecretary);


        mD1Spinner = findViewById(R.id.sp_director1);
        mD2Spinner = findViewById(R.id.sp_director2);
        mD3Spinner = findViewById(R.id.sp_director3);
        mD4Spinner = findViewById(R.id.sp_director4);
        mD5Spinner = findViewById(R.id.sp_director5);
        mD6Spinner = findViewById(R.id.sp_director6);
        mD7Spinner = findViewById(R.id.sp_director7);
        mD8Spinner = findViewById(R.id.sp_director8);
        mD9Spinner = findViewById(R.id.sp_director9);
        mD10Spinner = findViewById(R.id.sp_director10);
        mD11Spinner = findViewById(R.id.sp_director11);
        mD12Spinner = findViewById(R.id.sp_director12);
        mD13Spinner = findViewById(R.id.sp_director13);
        mD14Spinner = findViewById(R.id.sp_director14);
        mD15Spinner = findViewById(R.id.sp_director15);
        mD16Spinner = findViewById(R.id.sp_director16);
        mD17Spinner = findViewById(R.id.sp_director17);

    }

    private void getPersonDetails() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("phone_numbers").child(phoneNumbersModel.getId());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PhoneNumbersModel ob = new PhoneNumbersModel();
                ob = dataSnapshot.getValue(PhoneNumbersModel.class);
                if(ob.isLm())
                {
                    scrollView.setVisibility(View.GONE);
                    ll_voted.setVisibility(View.VISIBLE);
                    SharedPrefsHelper.getInstance().save(CP, ob.isCp());
                    SharedPrefsHelper.getInstance().save(P, ob.isP());
                    SharedPrefsHelper.getInstance().save(LM, ob.isLm());
                }
                else {
                    scrollView.setVisibility(View.VISIBLE);
                    ll_voted.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getDetails() {
        progressBar.setVisibility(View.VISIBLE);
        // scrollView.setVisibility(View.INVISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("ec_members");
        managementModelList.removeAll(managementModelList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for(DataSnapshot child: dataSnapshot.getChildren())
                {
                    PhoneNumbersModel ob = new PhoneNumbersModel();
                    ob = child.getValue(PhoneNumbersModel.class);
                    managementModelList.add(ob);
                }
                managemnetName = new ArrayList<String>();
                managemnetName.add("Select");
                for(int i = 0;i<managementModelList.size();i++)
                {
                    managemnetName.add(managementModelList.get(i).getName());
                }
                PresidentName = new ArrayList<String>(managemnetName);
                VicePresidentName = new ArrayList<String>(managemnetName);
                SVPresidentName = new ArrayList<String>(managemnetName);
                SecretaryName = new ArrayList<String>(managemnetName);
                OSecretaryName = new ArrayList<String>(managemnetName);
                TreasurerName = new ArrayList<String>(managemnetName);
                D1Name = new ArrayList<String>(managemnetName);
                D2Name = new ArrayList<String>(managemnetName);
                D3Name = new ArrayList<String>(managemnetName);
                D4Name = new ArrayList<String>(managemnetName);
                D5Name = new ArrayList<String>(managemnetName);
                D6Name = new ArrayList<String>(managemnetName);
                D7Name = new ArrayList<String>(managemnetName);
                D8Name = new ArrayList<String>(managemnetName);
                D9Name = new ArrayList<String>(managemnetName);
                D10Name = new ArrayList<String>(managemnetName);
                D11Name = new ArrayList<String>(managemnetName);
                D12Name = new ArrayList<String>(managemnetName);
                D14Name = new ArrayList<String>(managemnetName);
                D15Name = new ArrayList<String>(managemnetName);
                D16Name = new ArrayList<String>(managemnetName);
                D17Name = new ArrayList<String>(managemnetName);
                D13Name = new ArrayList<String>(managemnetName);

                loadData();
                progressBar.setVisibility(View.INVISIBLE);
                mButtonNext.setEnabled(true);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadData() {

        PresidentAdapter = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, PresidentName);
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        PresidentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mPresidentSpinner.setAdapter(PresidentAdapter);
        mPresidentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(PresidentName.get(position)!="Select"){
                    pName = PresidentName.get(position);
                    removeName(PresidentName.get(position));
                    SVPAdapter.notifyDataSetChanged();
                    VPAdapter.notifyDataSetChanged();
                    SecretaryAdapter.notifyDataSetChanged();
                    TreasurerAdapter.notifyDataSetChanged();
                    OSAdapter.notifyDataSetChanged();

                    d1.notifyDataSetChanged();
                    d2.notifyDataSetChanged();
                    d3.notifyDataSetChanged();
                    d4.notifyDataSetChanged();
                    d5.notifyDataSetChanged();
                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SVPAdapter= new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, SVPresidentName);
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        SVPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mSpSpinner.setAdapter(SVPAdapter);
        mSpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(SVPresidentName.get(position)!="Select"){
                    svpName = SVPresidentName.get(position);
                    removeName(SVPresidentName.get(position));
                    VPAdapter.notifyDataSetChanged();
                    OSAdapter.notifyDataSetChanged();
                    SecretaryAdapter.notifyDataSetChanged();
                    TreasurerAdapter.notifyDataSetChanged();

                    d1.notifyDataSetChanged();
                    d2.notifyDataSetChanged();
                    d3.notifyDataSetChanged();
                    d4.notifyDataSetChanged();
                    d5.notifyDataSetChanged();
                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        VPAdapter= new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, VicePresidentName);
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        VPAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mVPSpinner.setAdapter(VPAdapter);
        mVPSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(VicePresidentName.get(position)!="Select"){
                    vpName = VicePresidentName.get(position);
                    removeName(VicePresidentName.get(position));

                    SecretaryAdapter.notifyDataSetChanged();
                    TreasurerAdapter.notifyDataSetChanged();
                    OSAdapter.notifyDataSetChanged();

                    d1.notifyDataSetChanged();
                    d2.notifyDataSetChanged();
                    d3.notifyDataSetChanged();
                    d4.notifyDataSetChanged();
                    d5.notifyDataSetChanged();
                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SecretaryAdapter = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, SecretaryName);
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        SecretaryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mSecretarySpinner.setAdapter(SecretaryAdapter);
        mSecretarySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(SecretaryName.get(position)!="Select"){
                    sName = SecretaryName.get(position);
                    removeName(SecretaryName.get(position));

                    TreasurerAdapter.notifyDataSetChanged();
                    OSAdapter.notifyDataSetChanged();

                    d1.notifyDataSetChanged();
                    d2.notifyDataSetChanged();
                    d3.notifyDataSetChanged();
                    d4.notifyDataSetChanged();
                    d5.notifyDataSetChanged();
                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        TreasurerAdapter = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, TreasurerName);
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        TreasurerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mTreasurerSpinner.setAdapter(TreasurerAdapter);

        mTreasurerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(TreasurerName.get(position)!="Select"){
                    tName = TreasurerName.get(position);
                    removeName(TreasurerName.get(position));
                    OSAdapter.notifyDataSetChanged();
                    d1.notifyDataSetChanged();
                    d2.notifyDataSetChanged();
                    d3.notifyDataSetChanged();
                    d4.notifyDataSetChanged();
                    d5.notifyDataSetChanged();
                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        OSAdapter= new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, OSecretaryName);
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        OSAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mOSSpinner.setAdapter(OSAdapter);
        mOSSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(OSecretaryName.get(position)!="Select"){
                    osName = OSecretaryName.get(position);
                    removeName(OSecretaryName.get(position));

                    d1.notifyDataSetChanged();
                    d2.notifyDataSetChanged();
                    d3.notifyDataSetChanged();
                    d4.notifyDataSetChanged();
                    d5.notifyDataSetChanged();
                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        d1 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D1Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD1Spinner.setAdapter(d1);

        mD1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D1Name.get(position)!="Select"){
                    d1Name = D1Name.get(position);
                    removeName(D1Name.get(position));


                    d2.notifyDataSetChanged();
                    d3.notifyDataSetChanged();
                    d4.notifyDataSetChanged();
                    d5.notifyDataSetChanged();
                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d2 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D2Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD2Spinner.setAdapter(d2);

        mD2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D2Name.get(position)!="Select"){
                    d2Name = D2Name.get(position);
                    removeName(D2Name.get(position));



                    d3.notifyDataSetChanged();
                    d4.notifyDataSetChanged();
                    d5.notifyDataSetChanged();
                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        d3 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D3Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD3Spinner.setAdapter(d3);

        mD3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D3Name.get(position)!="Select"){
                    d3Name = D3Name.get(position);
                    removeName(D3Name.get(position));

                    d4.notifyDataSetChanged();
                    d5.notifyDataSetChanged();
                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        d4 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D4Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD4Spinner.setAdapter(d4);

        mD4Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D4Name.get(position)!="Select"){
                    d4Name = D4Name.get(position);
                    removeName(D4Name.get(position));


                    d5.notifyDataSetChanged();
                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d5 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D5Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD5Spinner.setAdapter(d5);

        mD5Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D5Name.get(position)!="Select"){
                    d5Name = D5Name.get(position);
                    removeName(D5Name.get(position));

                    d6.notifyDataSetChanged();
                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d6 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D6Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD6Spinner.setAdapter(d6);

        mD6Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D6Name.get(position)!="Select"){
                    d6Name = D6Name.get(position);
                    removeName(D6Name.get(position));


                    d7.notifyDataSetChanged();
                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d7 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D7Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD7Spinner.setAdapter(d7);

        mD7Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D7Name.get(position)!="Select"){
                    d7Name = D7Name.get(position);
                    removeName(D7Name.get(position));

                    d8.notifyDataSetChanged();
                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d8 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D8Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD8Spinner.setAdapter(d8);

        mD8Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D8Name.get(position)!="Select"){
                    d8Name = D8Name.get(position);
                    removeName(D8Name.get(position));

                    d9.notifyDataSetChanged();
                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d9 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D9Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD9Spinner.setAdapter(d9);

        mD9Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D9Name.get(position)!="Select"){
                    d9Name = D9Name.get(position);
                    removeName(D9Name.get(position));

                    d10.notifyDataSetChanged();
                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d10 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D10Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD10Spinner.setAdapter(d10);

        mD10Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D10Name.get(position)!="Select"){
                    d10Name = D10Name.get(position);
                    removeName(D10Name.get(position));

                    d11.notifyDataSetChanged();
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d11 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D11Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD11Spinner.setAdapter(d11);

        mD11Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D11Name.get(position)!="Select"){
                    d11Name = D11Name.get(position);
                    removeName(D11Name.get(position));
                    d12.notifyDataSetChanged();
                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d12 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D12Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD12Spinner.setAdapter(d12);

        mD12Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D12Name.get(position)!="Select"){
                    d12Name = D12Name.get(position);
                    removeName(D12Name.get(position));

                    d13.notifyDataSetChanged();
                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d13 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D13Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD13Spinner.setAdapter(d13);

        mD13Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D13Name.get(position)!="Select"){
                    d13Name = D13Name.get(position);
                    removeName(D13Name.get(position));

                    d14.notifyDataSetChanged();
                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d14 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D14Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD14Spinner.setAdapter(d14);

        mD14Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D14Name.get(position)!="Select"){
                    d14Name = D14Name.get(position);
                    removeName(D14Name.get(position));

                    d15.notifyDataSetChanged();
                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d15 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D15Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD15Spinner.setAdapter(d15);

        mD15Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D15Name.get(position)!="Select"){
                    d15Name = D15Name.get(position);
                    removeName(D15Name.get(position));

                    d16.notifyDataSetChanged();
                    d17.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d16 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D16Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d16.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD16Spinner.setAdapter(d16);

        mD16Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(D16Name.get(position)!="Select"){
                    d16Name = D16Name.get(position);
                    removeName(D16Name.get(position));
                    d17.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        d17 = new ArrayAdapter<String>( LifeMemberActivity.this, android.R.layout.simple_spinner_item, D17Name );
        // ArrayAdapter<CharSequence> CategoryAdapter = ArrayAdapter.createFromResource(this, R.array.array_cat, android.R.layout.simple_spinner_item);
        d17.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        mD17Spinner.setAdapter(d17);

        mD17Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                d17Name = D17Name.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void removeName(String  pos)
    {
        if(!pos.equalsIgnoreCase("Select")) {
            VicePresidentName.remove(pos);
            SecretaryName.remove(pos);
            OSecretaryName.remove(pos);
            TreasurerName.remove(pos);
            SVPresidentName.remove(pos);
            D1Name.remove(pos);
            D2Name.remove(pos);
            D3Name.remove(pos);
            D4Name.remove(pos);
            D5Name.remove(pos);
            D6Name.remove(pos);
            D7Name.remove(pos);
            D8Name.remove(pos);
            D9Name.remove(pos);
            D10Name.remove(pos);
            D11Name.remove(pos);
            D12Name.remove(pos);
            D13Name.remove(pos);
            D14Name.remove(pos);
            D15Name.remove(pos);
            D16Name.remove(pos);
            D17Name.remove(pos);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                if(allFeilds())
                {
                AlertDialog.Builder builder = new AlertDialog.Builder(LifeMemberActivity.this);
                builder.setNegativeButton("EDIT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDetails();
                    }
                });
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scrollView.setVisibility(View.INVISIBLE);
                        ll_voted.setVisibility(View.VISIBLE);
                        submitData();
                    }
                });
                builder.setTitle("CONFIRM!!!");
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        else{
            showAlert();
        }
                break;
            case R.id.btn_patron_act:
               finish();
                break;


        }
    }

    private void submitData() {
        final VotingModel votingModel = new VotingModel();

        votingModel.setName(phoneNumbersModel.getName());
        votingModel.setPhone(phoneNumbersModel.getPhone());

            final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference().child("CastedVotes").child("LifeMembers").child(phoneNumbersModel.getId());
            databaseReference.setValue(votingModel);
            databaseReference = firebaseDatabase.getReference().child("phone_numbers").child(phoneNumbersModel.getId()).child("lm");
            databaseReference.setValue(true);
            SharedPrefsHelper.getInstance().save(LM, true);

    }

    private void showAlert() {
        Toast.makeText(LifeMemberActivity.this, "Please Select all memebers", Toast.LENGTH_LONG).show();
        return;
    }

    private boolean allFeilds()
    {
        if(d17Name!=null)
            votingModel.setD17name(d17Name);
        else
            return false;
        if(pName!=null)
            votingModel.setChairman(pName);
        else
            return false;
        if(sName!=null)
            votingModel.setSecretary(sName);
        else
            return false;
        if(tName!=null)
            votingModel.setTreasurer(tName);
        else
            return false;
        if(svpName!=null)
            votingModel.setSvpname(svpName);
        else
            return false;
        if(vpName!=null)
            votingModel.setVpname(vpName);
        else
            return false;
        if(osName!=null)
            votingModel.setOsname(osName);
        else
            return false;
        if(d1Name!=null)
            votingModel.setD1name(d1Name);
        else
            return false;
        if(d2Name!=null)
            votingModel.setD2name(d2Name);
        else
            return false;
        if(d3Name!=null)
            votingModel.setD3name(d3Name);
        else
            return false;
        if(d4Name!=null)
            votingModel.setD4name(d4Name);
        else
            return false;
        if(d5Name!=null)
            votingModel.setD5name(d5Name);
        else
            return false;
        if(d6Name!=null)
            votingModel.setD6name(d6Name);
        else
            return false;
        if(d7Name!=null)
            votingModel.setD7name(d7Name);
        else
            return false;
        if(d8Name!=null)
            votingModel.setD8name(d8Name);
        else
            return false;
        if(d9Name!=null)
            votingModel.setD9name(d9Name);
        else
            return false;
        if(d10Name!=null)
            votingModel.setD10name(d10Name);
        else
            return false;
        if(d11Name!=null)
            votingModel.setD11name(d11Name);
        else
            return false;
        if(d12Name!=null)
            votingModel.setD12name(d12Name);
        else
            return false;
        if(d13Name!=null)
            votingModel.setD13name(d13Name);
        else
            return false;

        if(d14Name!=null)
            votingModel.setD14name(d14Name);
        else
            return false;
        if(d15Name!=null)
            votingModel.setD15name(d15Name);
        else
            return false;
        if(d16Name!=null)
            votingModel.setD16name(d16Name);
        else
            return false;


        return true;
    }
}


