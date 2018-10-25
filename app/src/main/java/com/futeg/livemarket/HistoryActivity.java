package com.futeg.livemarket;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private Context mContext;
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
        setContentView(R.layout.data_history);
        final int secs = 3;
        // Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

//        AdView adView = new AdView(this);
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        //   mAdView = findViewById(R.id.adView);
        //  AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        myOnClickListener = new MyOnClickListener(this);
        ProgressDialog progressDialog;
        //   recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//        recyclerView.setHasFixedSize(true);
        //  layoutManager = new LinearLayoutManager(this);
        //  recyclerView.setLayoutManager(layoutManager);
        //   recyclerView.setItemAnimator(new DefaultItemAnimator());
        progressDialog = new ProgressDialog(HistoryActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        // new LoginAsyncTask ().execute();
        mContext = getApplicationContext();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        final String url = "http://futegsolutions.com/api/data_history.php";

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        try {
                            JSONArray jArray2 = response.getJSONArray("result");
                            Log.d("Response222", String.valueOf(jArray2));
                            //  JSONObject market_json = response.getJSONObject("result");
                            TableLayout stk = (TableLayout) findViewById(R.id.tlTable01);
                            //indexArray = new String[]{"HANGSENG", "DAX", "KOSPI", "SENSEX", "DOWJONES"};
                            //  JSONArray array = new JSONArray(jArray2);
                            for (int i = 0; i < jArray2.length(); i++) {
                                JSONObject row = jArray2.getJSONObject(i);
                                String kospi = row.getString("KOSPI");
                                String hangseng = row.getString("HANGSENG");
                                String sensex = row.getString("SENSEX");
                                String dax = row.getString("DAX");
                                String dowjones = row.getString("DOWJONES");
                                String week = row.getString("WEEK");
                                TableRow tbrow = new TableRow(mContext);

                                TextView kospidata = new TextView(mContext);
                                kospidata.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                kospidata.setGravity(Gravity.CENTER_HORIZONTAL);
                                kospidata.setTextColor(Color.parseColor("black"));
                                kospidata.setText(kospi);
                                tbrow.addView(kospidata);


                                TextView hangsengdata = new TextView(mContext);
                                hangsengdata.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                hangsengdata.setGravity(Gravity.CENTER_HORIZONTAL);
                                hangsengdata.setTextColor(Color.parseColor("black"));
                                hangsengdata.setText(hangseng);
                                tbrow.addView(hangsengdata);

                                TextView sensexdata = new TextView(mContext);
                                sensexdata.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                sensexdata.setGravity(Gravity.CENTER_HORIZONTAL);
                                sensexdata.setTextColor(Color.parseColor("black"));
                                sensexdata.setText(sensex);
                                tbrow.addView(sensexdata);

                                TextView daxdata = new TextView(mContext);
                                daxdata.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                daxdata.setGravity(Gravity.CENTER_HORIZONTAL);
                                daxdata.setTextColor(Color.parseColor("black"));
                                daxdata.setText(dax);
                                tbrow.addView(daxdata);

                                TextView dowjonesdata = new TextView(mContext);
                                dowjonesdata.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                dowjonesdata.setGravity(Gravity.CENTER_HORIZONTAL);
                                dowjonesdata.setTextColor(Color.parseColor("black"));
                                dowjonesdata.setText(dowjones);
                                tbrow.addView(dowjonesdata);

                                TextView weekdata = new TextView(mContext);
                                weekdata.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                                weekdata.setGravity(Gravity.CENTER_HORIZONTAL);
                                weekdata.setTextColor(Color.parseColor("black"));
                                weekdata.setText(week);
                                tbrow.addView(weekdata);

                                stk.addView(tbrow);

                                Log.d("Response222", String.valueOf(row));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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

        //end


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