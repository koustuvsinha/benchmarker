package com.koustuvsinha.benchmarker.views;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.models.DbStatusMessageModel;
import com.koustuvsinha.benchmarker.utils.BusProvider;
import com.koustuvsinha.benchmarker.utils.Constants;
import com.squareup.otto.Subscribe;


public class DbTestResultStatus extends Fragment {

    public static final String PAGE_NAME = "Status";
    private OnFragmentInteractionListener mListener;
    private int dbType;
    private int numRecords;
    private TextView testingStatusHeader;
    private TextView testDbName;
    private TextView testDbNumRecords;
    private ArcProgress arcProgress;
    private ImageView insertStatusImg;
    private ImageView readStatusImg;
    private ImageView deleteStatusImg;
    private ImageView updateStatusImg;
    private TextView insertResult;
    private TextView readResult;
    private TextView updateResult;
    private TextView deleteResult;
    private Drawable checkMark;



    public static DbTestResultStatus newInstance(int dbType,int numRecords) {
        DbTestResultStatus fragment = new DbTestResultStatus();
        Bundle args = new Bundle();
        args.putInt(Constants.DB_TYPE,dbType);
        args.putInt(Constants.DB_NUM_RECORDS,numRecords);
        fragment.setArguments(args);
        return fragment;
    }

    public DbTestResultStatus() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().getBus().register(this);
        if(savedInstanceState!=null) {
            dbType = savedInstanceState.getInt(Constants.DB_TYPE);
            numRecords = savedInstanceState.getInt(Constants.DB_NUM_RECORDS);
        } else {
            Bundle args = getArguments();
            dbType = args.getInt(Constants.DB_TYPE);
            numRecords = args.getInt(Constants.DB_NUM_RECORDS);
        }

        Log.i(Constants.APP_NAME,"-------------> " + dbType);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_db_test_result_status, container, false);

        testingStatusHeader = (TextView)v.findViewById(R.id.testingStatusHeader);
        testDbName = (TextView)v.findViewById(R.id.testDbName);
        testDbNumRecords = (TextView)v.findViewById(R.id.testDbNumRecords);

        arcProgress = (ArcProgress)v.findViewById(R.id.arc_progress);

        insertStatusImg = (ImageView)v.findViewById(R.id.insertStatusImg);
        readStatusImg = (ImageView)v.findViewById(R.id.readStatusImg);
        updateStatusImg = (ImageView)v.findViewById(R.id.updateStatusImg);
        deleteStatusImg = (ImageView)v.findViewById(R.id.deleteStatusImg);

        insertResult = (TextView)v.findViewById(R.id.insertResult);
        readResult = (TextView)v.findViewById(R.id.readResult);
        updateResult = (TextView)v.findViewById(R.id.updateResult);
        deleteResult = (TextView)v.findViewById(R.id.deleteResult);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            checkMark = getResources().getDrawable(R.drawable.ic_check_green_600_18dp, null);
        }
        else {
            checkMark = getResources().getDrawable(R.drawable.ic_check_green_600_18dp);
        }
        //initialize values
        testDbName.setText(Constants.DB_LIST.get(dbType).getDbName());
        testDbNumRecords.setText(numRecords + " Records");
        arcProgress.setProgress(0);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Subscribe
    public void processStatusMessages(DbStatusMessageModel statusMessageModel) {

        switch(statusMessageModel.getStatusCode()) {
            case Constants.TESTING_START :
                testingStatusHeader.setText("Testing in progress ...");
                break;
            case Constants.TESTING_END :
                testingStatusHeader.setText("Testing finished");
                break;
            case Constants.RECEIVE_INSERT_TIME :
                insertStatusImg.setImageDrawable(checkMark);
                insertResult.setText(calculatePerf(statusMessageModel.getStatusMessage()));
                break;
            case Constants.RECEIVE_READ_TIME :
                readStatusImg.setImageDrawable(checkMark);
                readResult.setText(calculatePerf(statusMessageModel.getStatusMessage()));
                break;
            case Constants.RECEIVE_UPDATE_TIME :
                updateStatusImg.setImageDrawable(checkMark);
                updateResult.setText(calculatePerf(statusMessageModel.getStatusMessage()));
                break;
            case Constants.RECEIVE_DELETE_TIME :
                deleteStatusImg.setImageDrawable(checkMark);
                deleteResult.setText(calculatePerf(statusMessageModel.getStatusMessage()));
                break;
            case Constants.PERCENT_STATUS :
                arcProgress.setProgress(statusMessageModel.getStatusPercent());
                break;
        }
    }

    private String calculatePerf(String perf) {
        return String.format("%.2f",((double)numRecords / Integer.parseInt(perf))*1000);
    }

}
