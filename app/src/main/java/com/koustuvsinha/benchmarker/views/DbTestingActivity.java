package com.koustuvsinha.benchmarker.views;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.adaptors.ViewerPagerAdaptor;
import com.koustuvsinha.benchmarker.models.DbResultModel;
import com.koustuvsinha.benchmarker.models.DbStatusMessageModel;
import com.koustuvsinha.benchmarker.services.DbTestRunnerService;
import com.koustuvsinha.benchmarker.utils.BusProvider;
import com.koustuvsinha.benchmarker.utils.Constants;

import java.io.File;
import java.util.ArrayList;


public class DbTestingActivity extends AppCompatActivity implements DbTestResultDetails.OnFragmentInteractionListener,DbTestResultStatus.OnFragmentInteractionListener {

    private int numRecords;
    private int dbType;
    private int numPercent;
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_testing);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        numRecords = getIntent().getIntExtra(Constants.TEST_LIMIT_SELECTED,1000);

        dbType = getIntent().getIntExtra(Constants.SELECTED_DB_TEST,5);
        numPercent = 0;

        getSupportActionBar().setTitle(Constants.DB_LIST.get(dbType).getDbName());
        getSupportActionBar().setSubtitle("Testing with " + numRecords + " rows of data");

        generateViews();
        //setupServiceReceiver();
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
        Log.i(Constants.APP_NAME, "Received numRecords = " + numRecords);
        i.putExtra(Constants.DB_NUM_RECORDS,numRecords);
        i.putExtra(Constants.DB_TYPE,dbType);
        Log.i(Constants.APP_NAME, "Starting Service Intent..");
        startService(i);
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int resultCode = intent.getIntExtra("resultCode", RESULT_CANCELED);

            if (resultCode == RESULT_OK) {
                numPercent ++;
                // get the data and display accordingly

                int statusCode = intent.getIntExtra(Constants.RECEIVE_STATUS, 0);
                Log.i(Constants.APP_NAME,"Status code -------------------.... " + statusCode);

                if (statusCode == Constants.RECEIVE_STATUS_MSG) {
                    DbResultModel result = new DbResultModel(intent.getStringExtra(Constants.RECEIVE_MSG));
                    //pass this model to the logs fragment
                    BusProvider.getInstance().getBus().post(result);
                } else {

                    //pass everything else to the status fragment
                    DbStatusMessageModel statusMessageModel = new DbStatusMessageModel();
                    statusMessageModel.setStatusCode(statusCode);
                    statusMessageModel.setStatusMessage(intent.getStringExtra(Constants.RECEIVE_MSG));
                    Log.i(Constants.APP_NAME, "-----> " + statusMessageModel.getStatusMessage());
                    BusProvider.getInstance().getBus().post(statusMessageModel);
                }

                DbStatusMessageModel percentStat = new DbStatusMessageModel();
                percentStat.setStatusCode(Constants.PERCENT_STATUS);
                percentStat.setStatusPercent((int)(((double) numPercent / Constants.PERCENT_TOTAL) * 100));
                BusProvider.getInstance().getBus().post(percentStat);
            }
        }
    };

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void generateViews() {
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);

        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(DbTestResultStatus.newInstance(dbType, numRecords));
        fragments.add(DbTestResultDetails.newInstance());

        viewPager.setAdapter(new ViewerPagerAdaptor(getSupportFragmentManager(), fragments));
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Constants.INTENT_FILTER);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
}
