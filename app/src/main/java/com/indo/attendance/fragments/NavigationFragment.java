package com.indo.attendance.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.indo.attendance.R;
import com.indo.attendance.activities.StartupActivity;

public class NavigationFragment extends Fragment {
    public NavigationFragment() {
        super(R.layout.fragment_navigation);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation, container, false);
        Button logoutBtn = v.findViewById(R.id.logout_button);
        logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireContext(), StartupActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });
        return v;
    }
}
