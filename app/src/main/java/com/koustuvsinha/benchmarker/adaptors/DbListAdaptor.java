package com.koustuvsinha.benchmarker.adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.utils.Constants;

/**
 * Created by koustuv on 26/5/15.
 */
public class DbListAdaptor extends RecyclerView.Adapter<DbListAdaptor.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView dbNameView;
        public TextView dbVersionView;
        public ViewHolder(View v) {
            super(v);
            dbNameView = (TextView)v.findViewById(R.id.dbName);
            dbVersionView = (TextView)v.findViewById(R.id.dbVersion);
        }
    }


    @Override
    public DbListAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.db_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(DbListAdaptor.ViewHolder viewHolder, int i) {
        viewHolder.dbNameView.setText(Constants.DB_LIST.get(i).getDbName());
        viewHolder.dbVersionView.setText(Constants.DB_LIST.get(i).getDbVersion());
    }

    @Override
    public int getItemCount() {
        return Constants.DB_LIST.size();
    }
}
