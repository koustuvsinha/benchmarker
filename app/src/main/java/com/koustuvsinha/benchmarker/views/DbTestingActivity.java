package com.koustuvsinha.benchmarker.views;

import android.app.Activity;
import android.content.Intent;
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

public class DbTestingActivity extends Activity {

    private DbTestResultsReceiverService testResultsReceiver;
    private RecyclerView mRecyclerView;
    private DbResultAdaptor mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_testing);
        mRecyclerView = (RecyclerView) findViewById(R.id.resultListView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DbResultAdaptor();
        mRecyclerView.setAdapter(mAdapter);
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
        i.putExtra(Constants.DB_NUM_RECORDS,1000);
        i.putExtra(Constants.RECEIVER_INTENT,testResultsReceiver);
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
                    }
                }
            }
        });
    }
}
