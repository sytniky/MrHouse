package com.sytniky.mrhouse.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.sytniky.mrhouse.R;
import com.sytniky.mrhouse.adapter.ServiceAdapter;
import com.sytniky.mrhouse.database.DBContracts;

public class ServiceFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_ID = 101;
    ServiceAdapter mServiceAdapter;
    ProgressBar mProgressBar;

    public ServiceFragment() {
        // Required empty public constructor
    }

    public static ServiceFragment newInstance() {
        ServiceFragment fragment = new ServiceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mServiceAdapter = new ServiceAdapter(getContext(), null, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_service, container, false);

        mProgressBar = (ProgressBar) v.findViewById(R.id.pbProgress);
        mProgressBar.setVisibility(View.VISIBLE);

        GridView gvServices = (GridView) v.findViewById(R.id.gvServices);
        gvServices.setAdapter(mServiceAdapter);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getContext(),
                DBContracts.Services.CONTENT_URI,
                DBContracts.Services.DEFAULT_PROJECTION,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mServiceAdapter.changeCursor(data);
        mServiceAdapter.notifyDataSetChanged();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mServiceAdapter.changeCursor(null);
    }
}
