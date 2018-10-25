package com.futeg.livemarket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.futeg.livemarket.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    String[] indexArray;
    String[] dateArray;
    String[] valueArray;
    String hangsang_index;
    String hangsang_index_date;
    CountDownTimer x;
    String kospi;
    String sensex;
    String dowjones;
    String dax;
    String kospi_date;
    String sensex_date;
    String dowjones_date;
    String dax_date;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    ProgressDialog progressDialog;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<DataModel> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    private String mJSONURLString = "http://futegsolutions.com/api/data.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final int secs = 3;
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        myOnClickListener = new MyOnClickListener(this);
        ProgressDialog progressDialog;
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        // new LoginAsyncTask ().execute();
        mContext = getApplicationContext();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        final String url = "http://futegsolutions.com/api/data.json";

        final Button h_button = (Button) findViewById(R.id.history_button);
        h_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent activityChangeIntent = new Intent(MainActivity.this, HistoryActivity.class);
                // currentContext.startActivity(activityChangeIntent);
                MainActivity.this.startActivity(activityChangeIntent);
            }
        });
        //here
        x = new CountDownTimer((secs + 1) * 1000, 1000) // Wait 5 secs, tick every 1 sec
        {
            @Override
            public final void onTick(final long millisUntilFinished) {
                //Log.d("ggg","sss");
                // prepare the Request
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // display response
                                try {
                                    JSONObject market_json = response.getJSONObject("result");
                                    //indexArray = new String[]{"HANGSENG", "DAX", "KOSPI", "SENSEX", "DOWJONES"};
                                    indexArray = new String[]{"KOSPI", "HANGSENG", "SENSEX", "DAX", "DOWJONES"};
                                    hangsang_index = market_json.getJSONObject("hangseng").getString("index");
                                    dax = market_json.getJSONObject("dax").getString("index");
                                    kospi = market_json.getJSONObject("kospi").getString("index");
                                    sensex = market_json.getJSONObject("sensex").getString("index");
                                    dowjones = market_json.getJSONObject("dowjones").getString("index");
                                    //valueArray = new String[]{hangsang_index, dax, kospi, sensex, dowjones};
                                    valueArray = new String[]{kospi, hangsang_index, sensex, dax, dowjones};

                                    hangsang_index_date = market_json.getJSONObject("hangseng").getString("date");
                                    dax_date = market_json.getJSONObject("dax").getString("date");
                                    kospi_date = market_json.getJSONObject("kospi").getString("date");
                                    sensex_date = market_json.getJSONObject("sensex").getString("date");
                                    dowjones_date = market_json.getJSONObject("dowjones").getString("date");
                                    // dateArray = new String[]{hangsang_index_date, dax_date, kospi_date, sensex_date, dowjones_date};
                                    dateArray = new String[]{kospi_date, hangsang_index_date, sensex_date, sensex_date, dowjones_date};

                                    Log.d("Response222", String.valueOf(market_json.getJSONObject("hangseng").getString("index")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                //Log.d("ggg", String.valueOf(indexArray));
                                data = new ArrayList<DataModel>();
                                for (int i = 0; i < indexArray.length; i++) {
                                    data.add(new DataModel(
                                            indexArray[i],
                                            valueArray[i],
                                            dateArray[i]
                                    ));
                                }

                                progressDialog.hide();

                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                requestQueue.add(getRequest);

            }

            @Override
            public final void onFinish() {
                adapter = new CustomAdapter(data);
                recyclerView.setAdapter(adapter);
                x.start();
                Log.d("ppggg", "sss");
            }
        }.start();
        //end

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //Here set a flag to know that your
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next activity.
                // goToNextActivity();
                Intent myIntent = new Intent(MainActivity.this, MainActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }

    private static class MyOnClickListener implements View.OnClickListener {
        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
//            removeItem(v);
        }

//        private void removeItem(View v) {
//            int selectedItemPosition = recyclerView.getChildPosition(v);
//            RecyclerView.ViewHolder viewHolder
//                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
//            TextView textViewName
//                    = (TextView) viewHolder.itemView.findViewById(R.id.index_name);
//            String selectedName = (String) textViewName.getText();
//            int selectedItemId = -1;
//            for (int i = 0; i < MyData.nameArray.length; i++) {
//                if (selectedName.equals(MyData.nameArray[i])) {
//                    selectedItemId = MyData.id_[i];
//                }
//            }
////            removedItems.add(selectedItemId);
//          //  data.remove(selectedItemPosition);
//           // adapter.notifyItemRemoved(selectedItemPosition);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.add_item) {
            //check if any items to add

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
            Toast.makeText(this, "Data Updated.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    private void addRemovedItemToList() {
//        int addItemAtListPosition = 3;
//        data.add(addItemAtListPosition, new DataModel(
//                MyData.nameArray[removedItems.get(0)],
//                MyData.versionArray[removedItems.get(0)],
//                MyData.id_[removedItems.get(0)],
//                MyData.drawableArray[removedItems.get(0)]
//        ));
//        adapter.notifyItemInserted(addItemAtListPosition);
//        removedItems.remove(0);
    }
}