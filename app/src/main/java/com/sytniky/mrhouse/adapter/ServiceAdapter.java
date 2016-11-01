package com.sytniky.mrhouse.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sytniky.mrhouse.R;
import com.sytniky.mrhouse.database.DBContracts;

/**
 * Created by yuriy on 30.10.16.
 */
public class ServiceAdapter extends CursorAdapter {

    Context mContext;
    LayoutInflater mInflater;

    public ServiceAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);

        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.item_service, parent, false);
        setServiceData(view, cursor);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        setServiceData(view, cursor);
    }

    private void setServiceData(View view, Cursor cursor) {
        ImageView ivThumb = (ImageView) view.findViewById(R.id.ivThumb);
        TextView tvTitle = (TextView) view.findViewById(R.id.tvService);

        int thumb = cursor.getInt(cursor.getColumnIndex(DBContracts.Services.COLUMN_THUMB));
        String title = cursor.getString(cursor.getColumnIndex(DBContracts.Services.COLUMN_TITLE));

        ivThumb.setImageResource(thumb);
        tvTitle.setText(title);
    }
}
