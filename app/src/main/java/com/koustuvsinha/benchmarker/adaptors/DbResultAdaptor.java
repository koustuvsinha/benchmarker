package com.koustuvsinha.benchmarker.adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.models.DbResultModel;

import java.util.ArrayList;

/**
 * Created by koustuv on 30/5/15.
 */
public class DbResultAdaptor extends RecyclerView.Adapter<DbResultAdaptor.ViewHolder> {

    private ArrayList<DbResultModel> results;

    public DbResultAdaptor() {
        this.results = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView resultMessageView;
        public TextView resultTimeView;
        public ViewHolder(View v) {
            super(v);
            resultMessageView = (TextView)v.findViewById(R.id.resultMessage);
            resultTimeView = (TextView)v.findViewById(R.id.resultTime);
        }
    }

    public void setResults(DbResultModel result) {
        results.add(result);
    }

    @Override
    public DbResultAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DbResultAdaptor.ViewHolder holder, int position) {
        holder.resultMessageView.setText(results.get(position).getResultMessage());
        holder.resultTimeView.setText(results.get(position).getResultTime());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
