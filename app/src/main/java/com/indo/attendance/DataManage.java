package com.indo.attendance;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.indo.attendance.fragments.UserInfoFragment;

import org.w3c.dom.Document;

import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// [ Day, Month, Date, Time, GMT, Year]

public class DataManage {

    public static String contact;

    public static String latest_ping;

    public static String[] getArr(String s, String delimiter) {
        return s.split(delimiter);
    }

    public static String formatToEmail(String contact, String uName) {
        uName = uName.replaceAll(" ", "");
        uName = uName.toLowerCase(Locale.ROOT);
        return uName + "@" + contact + ".com";
    }

    public static int updateAttendance() {
        String[] time_now = getArr(Timestamp.now().toDate().toString(), " ");
        String[] time_old = getArr(latest_ping, " ");

        if(time_now[5].equals(time_old[5])
        && time_now[1].equals(time_old[1])
        && time_now[0].equals(time_old[0])) {
            Log.d("Check", "Same day ");
            return -1;
        }
        markAttendance();
        return 1;
    }

    public static void markAttendance() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> data = new HashMap<>();
        Timestamp tempStamp = Timestamp.now();
        data.put(contact, tempStamp);
        String[] date_data = getArr(Timestamp.now().toDate().toString(), " ");
        Log.d("Check", "Got: " + Arrays.toString(date_data));

        db.collection("year/" + "2022" +
                "/month/" + date_data[1] +
                "/date").document(date_data[2]).set(data)
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updatePing(tempStamp);
            }
        });
    }

    public static void getLastPing(View v) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Log.d("Check", "COntact: " + contact);

        db.collection("users").document(contact).get()
        .addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                if(task.getResult().exists()) {
                    Timestamp timestamp = task.getResult().getTimestamp("Last Ping");

                    if(timestamp != null) {
                        String s = timestamp.toDate().toString();
                        UserInfoFragment.pingText(s, v);
                        latest_ping = s;
                        Log.d("Check", "In: " + timestamp);
                    }
                }
                else {
                    Calendar cal = Calendar.getInstance();
                    cal.set(1111, 11, 1);
                    Timestamp tp = new Timestamp(cal.getTime());
                    updatePing(tp);
                    getLastPing(v);
                }
            }
        });
    }

    public static void updatePing(Timestamp time) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> temp = new HashMap<>();
        temp.put("Last Ping", time);

        latest_ping = time.toDate().toString();
        Log.d("Check", "Latest Ping: " + latest_ping);

        db.collection("users").document(contact).set(temp);
    }
}
