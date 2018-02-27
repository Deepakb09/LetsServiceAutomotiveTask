package com.deepak.letsservicetask.models;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deepak.letsservicetask.R;
import com.deepak.letsservicetask.views.MapsActivity;

import java.util.ArrayList;

/**
 * Created by Deepak on 27-Feb-18.
 */

public class LocationAdapter extends BaseAdapter {

    Context context;
    ArrayList<LocationDetails> locationDetailsArrayList;

    public LocationAdapter(MapsActivity mapsActivity, ArrayList<LocationDetails> locationDetailsArrayList){
        this.context = mapsActivity;
        this.locationDetailsArrayList = locationDetailsArrayList;
    }

    @Override
    public int getCount() {
        return locationDetailsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return locationDetailsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LocationDetails locDetArrayList = (LocationDetails) getItem(i);
        //locDetArrayList = (ArrayList<LocationDetails>) getItem(i);

        View view1 = View.inflate(context, R.layout.list_row, null);
        TextView latlng = (TextView) view1.findViewById(R.id.latlngView);
        TextView time = (TextView) view1.findViewById(R.id.timeView);
        TextView speed = (TextView) view1.findViewById(R.id.speedView);

        latlng.setText("Lat: "+locDetArrayList.getLatitude()+", Lng: "+locDetArrayList.getLongitude());
        time.setText("Time: "+locDetArrayList.getTime());
        speed.setText("Speed: "+locDetArrayList.getSpeed());

        return view1;
    }
}
