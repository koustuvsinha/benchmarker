package com.koustuvsinha.benchmarker.views;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.adaptors.DbResultAdaptor;
import com.koustuvsinha.benchmarker.models.DbResultModel;
import com.koustuvsinha.benchmarker.services.DbTestResultsReceiverService;
import com.koustuvsinha.benchmarker.utils.BusProvider;
import com.squareup.otto.Subscribe;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;


public class DbTestResultDetails extends Fragment {
    
    private RecyclerView mRecyclerView;
    private DbResultAdaptor mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private OnFragmentInteractionListener mListener;
    public static final String PAGE_NAME = "Logs";


    public static DbTestResultDetails newInstance() {
        DbTestResultDetails fragment = new DbTestResultDetails();

        return fragment;
    }

    public DbTestResultDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().getBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_db_test_result_details, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.resultListView);
        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(500);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new DbResultAdaptor();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setResults(new DbResultModel("Starting Application..."));

        return v;
    }

    @Subscribe
    public void updateResultList(DbResultModel result) {
        mAdapter.setResults(result);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);
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

}
