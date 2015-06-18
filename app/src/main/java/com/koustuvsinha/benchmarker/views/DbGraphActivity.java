package com.koustuvsinha.benchmarker.views;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.exceptions.ResultDataNotFoundException;
import com.koustuvsinha.benchmarker.models.DbFactoryModel;
import com.koustuvsinha.benchmarker.models.DbResultsSaverModel;
import com.koustuvsinha.benchmarker.results.DbResultsSaver;
import com.koustuvsinha.benchmarker.utils.AlertProvider;
import com.koustuvsinha.benchmarker.utils.AppUtils;
import com.koustuvsinha.benchmarker.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by koustuvsinha on 13/6/15.
 * Activity of the Graph display page
 */
public class DbGraphActivity extends AppCompatActivity {

    private Context mContext;
    private Toolbar mToolbar;
    private BarChart barChart;
    private TextView graphHeader;
    private List<DbResultsSaverModel> savedData;
    private List<String> xVals;
    private List<List<BarEntry>> yVals;
    private int reportTestType;
    private List<DbFactoryModel> dbList;
    private RadioButton defaultChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_graph);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Performance Analysis");
        getSupportActionBar().setSubtitle("Comparision between db performance");

        barChart = (BarChart)findViewById(R.id.chart);
        barChart.setBorderColor(getResources().getColor(R.color.primary_dark));
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription("Operations/second");
        barChart.setMaxVisibleValueCount(20);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);

        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);

        YAxis yl = barChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.3f);

        YAxis yr = barChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);

        reportTestType = Constants.DB_TEST_TYPE_INSERT;
        getSavedData(Constants.TEST_LIMIT_VAL[0]);
        setTestTimes();

        try {
            renderData();
        } catch (ResultDataNotFoundException e) {
            new AlertProvider(mContext)
                    .displayErrorMessage("Sorry! No saved result data found!");
        }

        Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f);

        graphHeader = (TextView)findViewById(R.id.graphHeader);
        graphHeader.setText("Comparision - Insert");

        defaultChoice = (RadioButton)findViewById(R.id.rowsGroup1);
        defaultChoice.setChecked(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph, menu);
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

    private void getSavedData(int numRows) {

        savedData = new ArrayList<>();
        DbResultsSaver resultsSaver = new DbResultsSaver(mContext);

        Iterator it = Constants.DB_LIST.iterator();
        while(it.hasNext()) {
            DbFactoryModel factoryModel = (DbFactoryModel)it.next();
            DbResultsSaverModel resultsSaverModel = null;

            resultsSaverModel = resultsSaver.getLatestTestData(factoryModel.getDbType(),
                    numRows,reportTestType);
            if(resultsSaverModel != null && resultsSaverModel.getId() != 0) {
                savedData.add(resultsSaverModel);
                Log.i(Constants.APP_NAME, "retrieved " + resultsSaverModel.toString());
            }

        }
    }

    private void prepareChartColumnNames() {
        xVals = new ArrayList<String>();
        if(dbList!=null) {
            Iterator it = dbList.iterator();
            int count = 0;
            while (it.hasNext()) {
                count++;
                xVals.add("");
                it.next();
            }
        }

    }

    private void setPerfData(int dbType,long time,int numRows) {

        if(yVals == null) {
            yVals = new ArrayList<>(Constants.DB_LIST.size());
            dbList = new ArrayList<>();
        }

        BarEntry barEntry = new BarEntry(time, dbType);
        ArrayList<BarEntry> barEntryVal = new ArrayList<BarEntry>();
        barEntryVal.add(barEntry);
        yVals.add(barEntryVal);
        dbList.add(AppUtils.getInstance().getDbListItem(dbType));
    }

    private void renderData() throws ResultDataNotFoundException{
        prepareChartColumnNames();
        if(yVals==null) {
            throw new ResultDataNotFoundException();
        }
        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        Iterator it = dbList.iterator();
        int count = 0;
        while(it.hasNext()) {
            DbFactoryModel factoryModel = (DbFactoryModel)it.next();
            BarDataSet barDataSet = new BarDataSet(
                    yVals.get(count),
                    factoryModel.getDbName());
            barDataSet.setBarSpacePercent(35f);
            barDataSet.setColor(getResources().getColor(factoryModel.getChartColor()));
            dataSets.add(barDataSet);
            count++;
        }


        BarData barData = new BarData(xVals,dataSets);
        barData.setGroupSpace(80f);
        barData.setValueTextSize(10f);

        barChart.setData(barData);
        barChart.invalidate();
        barChart.notifyDataSetChanged();
        barChart.animateY(2500);
    }

    private void setTestTimes() {
        if(savedData!=null) {
            yVals = null;
            dbList = null;
            Iterator it = savedData.iterator();
            while(it.hasNext()) {
                DbResultsSaverModel saverModel = (DbResultsSaverModel)it.next();
                setPerfData(saverModel.getDbType(),saverModel.getTimeOps(),saverModel.getNumRows());
            }
        }
    }

    public void onRowsSelect(View view) {
        boolean checked = ((RadioButton)view).isChecked();
        switch (view.getId()) {
            case R.id.rowsGroup1 :
                if(checked) {
                    getSavedData(Constants.TEST_LIMIT_VAL[0]);
                }
                break;
            case R.id.rowsGroup2 :
                if(checked) {
                    getSavedData(Constants.TEST_LIMIT_VAL[1]);
                }
                break;
            case R.id.rowsGroup3 :
                if(checked) {
                    getSavedData(Constants.TEST_LIMIT_VAL[2]);
                }
                break;
        }

        setTestTimes();

        try {
            renderData();
        } catch (ResultDataNotFoundException e) {
            new AlertProvider(mContext)
                    .displayErrorMessage("Sorry! No saved result data found!");
        }
    }

}
