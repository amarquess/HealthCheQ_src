package com.sw551.fairfield.healthcheq;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import com.sw551.fairfield.healthcheq.maphandler.Place;
import com.sw551.fairfield.healthcheq.maphandler.PlacesService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements
        SearchView.OnQueryTextListener{

    // private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    public static String nextPageToken;

    private final String TAG = getClass().getSimpleName();
    private GoogleMap mMap;
    private String[] places;
    private LocationManager locationManager;
    private Location loc;
    private ProgressDialog dialog;
    private TextView tvTitle;
    SqlDbHelper db = new SqlDbHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //View map = inflater.inflate(R.layout.activity_maps, container, false);
        tvTitle = (TextView)findViewById(R.id.txtMapTitle);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading..");
        dialog.isIndeterminate();
        //SearchView searchView = (SearchView) findViewById(R.id.searchView1);
        //searchView.setOnQueryTextListener(this);
        initCompo();

        Intent intent = getIntent();
        tvTitle.setText(intent.getExtras().getString("title"));

        String[] searchItem = new String[1];
        searchItem[0] = intent.getExtras().getString("search_item");
        //places = getResources().getStringArray(R.array.places);
        places = searchItem;
        currentLocation();

        //return map;
    }

    private class GetPlaces extends AsyncTask<Void, Void, ArrayList<Place>> {

        private Context context;
        private String places;

        public GetPlaces(Context context, String places) {
            this.context = context;
            this.places = places;
        }

        @Override
        protected void onPostExecute(ArrayList<Place> result) {
            super.onPostExecute(result);

            for (int i = 0; i < result.size(); i++) {

                mMap.addMarker(new MarkerOptions()
                        .title(result.get(i).getName())
                        .position(
                                new LatLng(result.get(i).getLatitude(), result
                                        .get(i).getLongitude()))
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.pin))
                        .snippet(result.get(i).getVicinity()));

            }
            setHomePin();

            if (nextPageToken != null && !nextPageToken.trim().equals("")) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                new GetPlaces(this.context, places).execute();
            }else{
//				if (dialog.isShowing()) {
//					dialog.dismiss();
//				}
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//			if (!dialog.isShowing()) {
//				dialog.show();
//			}
//
        }

        @Override
        protected ArrayList<Place> doInBackground(Void... arg01) {
            PlacesService service = new PlacesService(
                    "AIzaSyAijDiUD7mG5sbkoFw9BD24yQb6chB7aoc");
            ArrayList<Place> findPlaces = service.findPlaces(loc.getLatitude(), // 28.632808
                    loc.getLongitude(), places); // 77.218276

            for (int i = 0; i < findPlaces.size(); i++) {

                Place placeDetail = findPlaces.get(i);
                Log.e(TAG, "places : " + placeDetail.getName());
            }
            return findPlaces;
        }

    }

    private void initCompo() {

        mMap = ((SupportMapFragment) this.getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();
    }

    private void currentLocation() {
        locationManager = (LocationManager) this.getSystemService(
                Context.LOCATION_SERVICE);

        String provider = locationManager
                .getBestProvider(new Criteria(), false);

        Location location = locationManager.getLastKnownLocation(provider);

        if (location == null) {
            locationManager.requestLocationUpdates(provider, 0, 0, listener);
        } else {
            loc = location;
            new GetPlaces(this, places[0].toLowerCase().replace(
                    "-", "_")).execute();
            Log.e(TAG, "location : " + location);
            moveCameraTolocation();
        }

    }

    private LocationListener listener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "location update : " + location);
            loc = location;
            locationManager.removeUpdates(listener);
            moveCameraTolocation();
        }
    };

    private void setHomePin()
    {
        User user = db.viewUser(1);

        mMap.addMarker(new MarkerOptions()
                .title(user.getFirst_name())
                .position(
                        new LatLng(loc.getLatitude(), loc.getLongitude()))
                .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.user_pin)));
    }

    private void moveCameraTolocation() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(loc.getLatitude(), loc.getLongitude())) // Sets
                        // the
                        // center
                        // of
                        // the
                        // map
                        // to
                        // Mountain View
                .zoom(13) // Sets the zoom
                .tilt(30) // Sets the tilt of the camera to 30 degrees
                .build(); // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onQueryTextChange(String arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String arg0) {
        mMap.clear();
        new GetPlaces(this, arg0).execute();
        return false;
    }
}
