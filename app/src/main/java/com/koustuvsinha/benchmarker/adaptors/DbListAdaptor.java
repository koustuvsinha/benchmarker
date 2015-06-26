package com.koustuvsinha.benchmarker.adaptors;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
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
        public ImageView dbLogo;
        public ViewHolder(View v) {
            super(v);
            dbNameView = (TextView)v.findViewById(R.id.dbName);
            dbVersionView = (TextView)v.findViewById(R.id.dbVersion);
            dbLogo = (ImageView)v.findViewById(R.id.db_logo);
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
        ColorGenerator generator = ColorGenerator.MATERIAL;
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(Constants.DB_LIST.get(i).getDbName().substring(0, 1),
                        generator.getRandomColor());
        viewHolder.dbLogo.setImageDrawable(drawable);
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
