package com.indo.attendance.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.indo.attendance.DataManage;
import com.indo.attendance.R;

public class UserInfoFragment extends Fragment {


    private Button markAttendanceBtn;


    private TextView userNameTV;

    public UserInfoFragment() {
        super(R.layout.fragment_user_info);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_info, container, false);
        init(v);


        String uName = DataManage.contact;
        userNameTV.setText(uName);

        markAttendanceBtn.setOnClickListener(view -> {
            if(DataManage.updateAttendance() == -1) {
                Toast.makeText(requireContext(), "You can only mark once a day", Toast.LENGTH_LONG).show();
            }
            pingText(DataManage.latest_ping, v);
        });
        return v;
    }

    public void init(View v) {
        DataManage.getLastPing(v);

        markAttendanceBtn = v.findViewById(R.id.mark_attendance_button);
        userNameTV = v.findViewById(R.id.userName_TV);
    }

    public static void pingText(String s, View view) {
        String pingText = "Last Recorded Attendance: \n" + s;

        TextView l = view.findViewById(R.id.lastPing_TV);
        l.setText(pingText);

        Button b = view.findViewById(R.id.mark_attendance_button);
        b.setEnabled(true);
    }


}
