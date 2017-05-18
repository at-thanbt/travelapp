package com.example.asiantech.travelapp.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asiantech.travelapp.R;
import com.example.asiantech.travelapp.activities.adapters.ListTouristAdapter;
import com.example.asiantech.travelapp.activities.fragments.HomeBlankFragment;
import com.example.asiantech.travelapp.activities.objects.User;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

/**
 * Created by phuong on 08/04/2017.
 */

public class AddTourristActivity extends AppCompatActivity {
    Firebase myFirebaseRef;
    private List<User> mTourists;
    private RecyclerView mRecyclerViewListTourist;
    private ListTouristAdapter mAdapter;
    private FloatingActionButton mBtnAdd;

    private String mIdTour;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tourrist);
        Firebase.setAndroidContext(this);
        mRecyclerViewListTourist = (RecyclerView) findViewById(R.id.rcvListTourist);
        mBtnAdd = (FloatingActionButton) findViewById(R.id.btnAddTourist);

        mIdTour = getIntent().getStringExtra(HomeBlankFragment.ID_TOUR);
        initData();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewListTourist.setLayoutManager(layoutManager);
        mAdapter = new ListTouristAdapter(this, mTourists);
        mRecyclerViewListTourist.setAdapter(mAdapter);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(AddTourristActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_tourist);
                final EditText edtPrice = (EditText) dialog.findViewById(R.id.edtPrice);
                edtPrice.requestFocus();

                Button btnOk = (Button) dialog.findViewById(R.id.tvBtnOk);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTourists.add(new User(edtPrice.getText().toString()));
                        mAdapter.notifyDataSetChanged();

                        String url = "https://rest.nexmo.com/sms/json?api_key=49cb9691&api_secret=19d1379d8098c4b0&";
                        url += "to=" + edtPrice.getText().toString() + "&";
                        url += "from=travelApp" + "&";
                        String content1 = String.valueOf(new Random().nextInt(10000));
                        Log.d("tag11", "content" + content1);
                        url += "text=" + content1;
                        Log.d("tag11", url);

                        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                Toast.makeText(getBaseContext(), "Đã gởi mã code thành công", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                System.out.println("" + volleyError);
                            }
                        });
                        RequestQueue rQueue = Volley.newRequestQueue(AddTourristActivity.this);
                        rQueue.add(request);
                        Firebase.setAndroidContext(AddTourristActivity.this);
                        myFirebaseRef = new Firebase("https://travelapp-4961a.firebaseio.com/user/"+mIdTour);
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("id", UUID.randomUUID().toString());
                        map.put("name", "");
                        map.put("pass", content1);
                        map.put("phonenumber", edtPrice.getText().toString());

                        myFirebaseRef.push().setValue(map);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    public void initData() {
        mTourists = new ArrayList<>();
        myFirebaseRef = new Firebase("https://travelapp-4961a.firebaseio.com/user/"+mIdTour);
        myFirebaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Map map = data.getValue(Map.class);
                    String key = data.getKey();
                    if (!map.get("id").toString().equals("1")) {
                        User user = new User();
                        user.setId(key);
                        user.setPhoneNumber(map.get("phonenumber").toString());
                        mTourists.add(user);
                        Log.d("tag123", "user 123" +mTourists.toString()+" key "+key+" number phone "+map.get("phonenumber").toString());
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("The read failed: ", firebaseError.getMessage());
            }
        });

    }

}
