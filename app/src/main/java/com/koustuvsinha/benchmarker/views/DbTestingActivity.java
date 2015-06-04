package com.koustuvsinha.benchmarker.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.adaptors.DbResultAdaptor;
import com.koustuvsinha.benchmarker.models.DbResultModel;
import com.koustuvsinha.benchmarker.services.DbTestResultsReceiverService;
import com.koustuvsinha.benchmarker.services.DbTestRunnerService;
import com.koustuvsinha.benchmarker.utils.Constants;

import java.io.File;

import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;

public class DbTestingActivity extends Activity {

    private DbTestResultsReceiverService testResultsReceiver;
    private RecyclerView mRecyclerView;
    private DbResultAdaptor mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int numRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_testing);
        mRecyclerView = (RecyclerView) findViewById(R.id.resultListView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(500);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DbResultAdaptor();
        mRecyclerView.setAdapter(mAdapter);

        numRecords = getIntent().getIntExtra(Constants.TEST_LIMIT_SELECTED,1000);

        setupServiceReceiver();
        mAdapter.setResults(new DbResultModel("Starting Application..."));
        onStartTesting();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_db_testing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onStartTesting() {
        Intent i = new Intent(this, DbTestRunnerService.class);
        Log.i(Constants.APP_NAME,"Received numRecords = " + numRecords);
        i.putExtra(Constants.DB_NUM_RECORDS,numRecords);
        i.putExtra(Constants.RECEIVER_INTENT,testResultsReceiver);
        i.putExtra(Constants.DB_TYPE,Constants.DB_TYPE_DEFAULT);
        Log.i(Constants.APP_NAME, "Starting Service Intent..");
        startService(i);
    }

    public void setupServiceReceiver() {
        testResultsReceiver = new DbTestResultsReceiverService(new Handler());
        testResultsReceiver.setReceiver(new DbTestResultsReceiverService.Receiver() {

            @Override
            public void onReceiveResult(int resultCode, Bundle resultData) {
                if(resultCode == RESULT_OK) {
                    // get the data and display accordingly

                    if(resultData.getInt(Constants.RECEIVE_STATUS) == Constants.RECEIVE_STATUS_MSG) {
                        DbResultModel result = new DbResultModel(resultData.getString(Constants.RECEIVE_MSG));
                        mAdapter.setResults(result);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);
                    }
                }
            }
        });
    }

    public void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);

                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                prefs.edit().clear().commit();
            }
        } catch (Exception e) {}
    }

    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }


    public void deleteAppData(Context context) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(Constants.APP_NAME,"Cleaning app cache");
        deleteCache(getApplicationContext());
        deleteAppData(getApplicationContext());
        Log.i(Constants.APP_NAME,"Cleaned app cache");
    }
}
