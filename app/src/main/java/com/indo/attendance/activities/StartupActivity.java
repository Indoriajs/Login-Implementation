package com.indo.attendance.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.indo.attendance.DataManage;
import com.indo.attendance.R;
import com.indo.attendance.fragments.PhoneFragment;

public class StartupActivity extends AppCompatActivity {
    private PhoneFragment phone_login_fragment;

    private FirebaseAuth mAuth;

    private FragmentManager fragmentManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_start);

        init();
        Log.d("Check", "init Success ");
        fragmentManager.beginTransaction()
                .replace(R.id.flFragment_holder, phone_login_fragment, null)
                .commit();
    }

    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null) {
            Toast.makeText(getApplicationContext(), "Welcome " + mAuth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
            DataManage.contact = user.getEmail();
            startNext();
        }
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();

        phone_login_fragment = new PhoneFragment();

        fragmentManager = getSupportFragmentManager();
    }

    private void startNext() {
        Intent intent = new Intent(this, UserPageActivity.class);
        startActivity(intent);
        finish();
    }
}
