package com.patelheggere.committeelection.activitites;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.patelheggere.committeelection.R;
import com.patelheggere.committeelection.adapter.MemberAdapter;
import com.patelheggere.committeelection.model.AnubhandaModel;

import java.util.ArrayList;
import java.util.List;

public class AnubhandaActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AnubhandaActivity";
    private DatabaseReference databaseReference1, databaseReference2, databaseReference3;
    private List<AnubhandaModel> cpList;
    private List<AnubhandaModel> pList;
    private List<AnubhandaModel> lmList;

    private RecyclerView recyclerView;

    private MemberAdapter memberAdapter;
    private ActionBar mActionBar;

    private Button btnAnubhand1, btnAnubhand2, btnAnubhanda3, btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anubhanda_lyt);
        cpList = new ArrayList<>();
        pList = new ArrayList<>();
        lmList = new ArrayList<>();
        initialixzeViewComponents();
        getData();
    }

    private void getData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 = firebaseDatabase.getReference().child("anubhandaone");
        databaseReference2 = firebaseDatabase.getReference().child("anubhanda_two");
        databaseReference3 = firebaseDatabase.getReference().child("anubhanda_three");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    AnubhandaModel ob = new AnubhandaModel();
                    ob = snapshot.getValue(AnubhandaModel.class);
                    cpList.add(ob);

                }

                memberAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    AnubhandaModel ob = new AnubhandaModel();
                    ob = snapshot.getValue(AnubhandaModel.class);
                    pList.add(ob);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    AnubhandaModel ob = new AnubhandaModel();
                    ob = snapshot.getValue(AnubhandaModel.class);
                    lmList.add(ob);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialixzeViewComponents() {
        mActionBar = getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setTitle("ಅನುಬ೦ಧಗಳು");
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        recyclerView = findViewById(R.id.rv_anubhnda);
        memberAdapter = new MemberAdapter(AnubhandaActivity.this, cpList);
        recyclerView.setAdapter(memberAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AnubhandaActivity.this));
        btnAnubhand1 = findViewById(R.id.btn_anubhanda1);
        btnAnubhand2 = findViewById(R.id.btn_anubhanda2);
        btnAnubhanda3 = findViewById(R.id.anubhanda3);
        btnContinue = findViewById(R.id.btn_continue);

        btnAnubhand1.setOnClickListener(this);
        btnAnubhand2.setOnClickListener(this);
        btnAnubhanda3.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_anubhanda1:
                memberAdapter = new MemberAdapter(AnubhandaActivity.this, cpList);
                recyclerView.setAdapter(memberAdapter);
                break;
            case R.id.btn_anubhanda2:
                memberAdapter = new MemberAdapter(AnubhandaActivity.this, pList);
                recyclerView.setAdapter(memberAdapter);
                break;
            case R.id.anubhanda3:
                memberAdapter = new MemberAdapter(AnubhandaActivity.this, lmList);
                recyclerView.setAdapter(memberAdapter);
                break;
            case R.id.btn_continue:
                startActivity(new Intent(AnubhandaActivity.this, RegisterMobActivity.class));
                break;
            case android.R.id.home:
                finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
