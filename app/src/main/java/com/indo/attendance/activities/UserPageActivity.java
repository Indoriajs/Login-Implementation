package com.indo.attendance.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.indo.attendance.R;
import com.indo.attendance.fragments.NavigationFragment;
import com.indo.attendance.fragments.UserInfoFragment;

public class UserPageActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_user_page);

        UserInfoFragment userInfoFragment = new UserInfoFragment();
        NavigationFragment navigationFragment = new NavigationFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.user_info_flFragmentView, userInfoFragment, null)
                .commit();

        fragmentManager.beginTransaction()
                .replace(R.id.navigationFragment_View, navigationFragment, null)
                .commit();
    }
}
