package com.koustuvsinha.benchmarker.adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koustuvsinha.benchmarker.R;
import com.koustuvsinha.benchmarker.utils.Constants;

/**
 * Created by koustuvsinha on 26/5/15.
 * DbListAdaptor class is an Adaptor to display the names of DB in the landing page
 * Uses RecyclerView adaptor
 */
public class DbListAdaptor extends RecyclerView.Adapter<DbListAdaptor.ViewHolder> {

    /**
     * RecyclerView Holder method for Adaptor to set the TextViews
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
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

    /**
     * Get the count of db items in ArrayList
     * @return DB ArrayList size
     */
    @Override
    public int getItemCount() {
        return Constants.DB_LIST.size();
    }
}
