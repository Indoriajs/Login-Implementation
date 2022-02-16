package com.indo.attendance.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.indo.attendance.DataManage;
import com.indo.attendance.R;
import com.indo.attendance.activities.UserPageActivity;

public class PhoneFragment extends Fragment {

    private FirebaseAuth mAuth;

    private EditText contactET;
    private EditText passwordET;
    private EditText nameET;

    private Button signInBtn;

    public PhoneFragment() {
        super(R.layout.fragment_login_type_phone);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View v = layoutInflater.inflate(R.layout.fragment_login_type_phone, container, false);
        init(v);
        Log.d("Check", "init(v) Success ");

        signInBtn.setOnClickListener(v1 -> {
            String uName = nameET.getText().toString();
            String contact = contactET.getText().toString();
            String password = passwordET.getText().toString();

            if(!uName.isEmpty() && !contact.isEmpty() && !password.isEmpty()) {
                DataManage.contact = DataManage.formatToEmail(contact, uName);
                SignIn(DataManage.formatToEmail(contact, uName), password, uName);
            }
            else {
                nameET.setError("required");
                contactET.setError("required");
                passwordET.setError("required");
            }
        });

        return v;
    }

    private void init(View view) {
        mAuth = FirebaseAuth.getInstance();

        contactET = view.findViewById(R.id.contact_etField);
        passwordET = view.findViewById(R.id.password_etField);
        nameET = view.findViewById(R.id.uName_TV);

        signInBtn = view.findViewById(R.id.signIn_button);
    }

    private void SignIn(String email, String password, String contact) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(getContext(), "Login Success", Toast.LENGTH_SHORT).show();
                        startNext();
                    } else {
                        Toast.makeText(getContext(), "Please check Contact and Password", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void startNext(){
        Intent intent = new Intent(requireContext(), UserPageActivity.class);
        requireActivity().startActivity(intent);
        requireActivity().finish();
    }
}
