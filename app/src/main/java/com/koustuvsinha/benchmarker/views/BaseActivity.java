package com.koustuvsinha.benchmarker.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.adaptors.DbListAdaptor;
import com.koustuvsinha.benchmarker.listeners.DbItemClickListener;
import com.koustuvsinha.benchmarker.utils.Constants;
import com.koustuvsinha.benchmarker.utils.DividerItemDecoration;
import com.melnykov.fab.FloatingActionButton;

import io.fabric.sdk.android.Fabric;

/**
 * Activity of the landing page
 */
public class BaseActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_base);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setSubtitle("Simple Db performance runner");

        mRecyclerView = (RecyclerView) findViewById(R.id.listview);
        mRecyclerView.setHasFixedSize(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.attachToRecyclerView(mRecyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DbListAdaptor();
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.addOnItemTouchListener(
                new DbItemClickListener(this, new DbItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, final int position) {

                        if(position == Constants.DB_TYPE_DEFAULT || position == Constants.DB_TYPE_REALM || position == Constants.DB_TYPE_SNAPPY) {

                            new MaterialDialog.Builder(mContext)
                                    .title("Select Test Limit")
                                    .items(Constants.TEST_LIMIT)
                                    .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {

                                        @Override
                                        public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {

                                            final Intent intent = new Intent(BaseActivity.this, DbTestingActivity.class);
                                            intent.putExtra(Constants.TEST_LIMIT_SELECTED, Constants.TEST_LIMIT_VAL[i]);
                                            intent.putExtra(Constants.SELECTED_DB_TEST, position);
                                            startActivity(intent);
                                            return true;
                                        }
                                    })
                                    .positiveText("Start Testing")
                                    .negativeText("Cancel")
                                    .show();
                        } else {

                            Toast.makeText(getApplicationContext(), "Tests are not yet ready!", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
        );

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(BaseActivity.this, DbGraphActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
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
}
