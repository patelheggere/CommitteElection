package com.patelheggere.committeelection.activitites;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.patelheggere.committeelection.R;
import com.patelheggere.committeelection.fragment.InstructionsFragment;

public class InstructionActivity extends AppCompatActivity implements InstructionsFragment.OnFragmentInteractionListener, View.OnClickListener{

    private Button btnAccept, btnDecline;
    private ActionBar mActionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        initializeViewComponent();
    }

    private void initializeViewComponent() {
        mActionBar = getSupportActionBar();
        if(mActionBar!=null)
        {
            mActionBar.setTitle("ವಿಷಯ");
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
        btnAccept = findViewById(R.id.btn_accept);
        btnDecline = findViewById(R.id.btn_decline);
        btnDecline.setOnClickListener(this);
        btnAccept.setOnClickListener(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_accept:
                startActivity(new Intent(InstructionActivity.this, AnubhandaActivity.class));
                finish();
                break;
            case R.id.btn_decline:
                finish();
                break;
            case android.R.id.home:
                finish();
                break;
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
