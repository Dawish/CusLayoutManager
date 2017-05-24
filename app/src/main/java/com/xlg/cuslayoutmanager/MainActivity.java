package com.xlg.cuslayoutmanager;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private String mRectFMsg = "[{\"bottom\":353.0,\"left\":50.0,\"right\":245.0,\"top\":201.0}," +
            "{\"bottom\":201.0,\"left\":830.0,\"right\":1220.0,\"top\":49.0}," +
            "{\"bottom\":353.0,\"left\":245.0,\"right\":830.0,\"top\":49.0}," +
            "{\"bottom\":505.0,\"left\":830.0,\"right\":1025.0,\"top\":353.0}," +
            "{\"bottom\":201.0,\"left\":50.0,\"right\":245.0,\"top\":49.0}," +
            "{\"bottom\":505.0,\"left\":245.0,\"right\":830.0,\"top\":353.0}," +
            "{\"bottom\":505.0,\"left\":1025.0,\"right\":1220.0,\"top\":353.0}," +
            "{\"bottom\":505.0,\"left\":50.0,\"right\":245.0,\"top\":353.0}," +
            "{\"bottom\":353.0,\"left\":830.0,\"right\":1220.0,\"top\":201.0}," +

            "{\"bottom\":353.0,\"left\":1220.0,\"right\":1415.0,\"top\":201.0}," +
            "{\"bottom\":201.0,\"left\":2000.0,\"right\":2390.0,\"top\":49.0}," +
            "{\"bottom\":353.0,\"left\":1415.0,\"right\":2000.0,\"top\":49.0}," +
            "{\"bottom\":505.0,\"left\":2000.0,\"right\":2195.0,\"top\":353.0}," +
            "{\"bottom\":201.0,\"left\":1220.0,\"right\":1415.0,\"top\":49.0}," +
            "{\"bottom\":505.0,\"left\":1415.0,\"right\":2000.0,\"top\":353.0}," +
            "{\"bottom\":505.0,\"left\":2195.0,\"right\":2390.0,\"top\":353.0}," +
            "{\"bottom\":505.0,\"left\":1220.0,\"right\":1415.0,\"top\":353.0}," +
            "{\"bottom\":353.0,\"left\":2000.0,\"right\":2390.0,\"top\":201.0},]";
    private ArrayList<RectF> mItemRectFList;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mItemRectFList = new Gson().fromJson(mRectFMsg, new TypeToken<ArrayList<RectF>>() {
        }.getType());
        if (mItemRectFList != null) {
            Log.d("dddd", "onCreate: "+mItemRectFList.size());
            mRecyclerView = (RecyclerView) findViewById(R.id.activity_main);
            HomeAdapter homeAdapter = new HomeAdapter(mItemRectFList);
            mRecyclerView.setLayoutManager(new HomeLayoutManager(mItemRectFList));
            mRecyclerView.setAdapter(homeAdapter);
        }
    }
}
