package sharkmedia.com.avampro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sharkmedia.com.avampro.ModelClass.CheckNetwork;
import sharkmedia.com.avampro.ModelClass.WorkerList;
import sharkmedia.com.avampro.RequestHandler.RequestHandler;
import sharkmedia.com.avampro.adapter.CategoryAdapter;
import sharkmedia.com.avampro.adapter.WorkerListAdapter;
import sharkmedia.com.avampro.utils.Constent;

public class ServiceActivty extends AppCompatActivity {

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<WorkerList> workerListList, workerLists;
    WorkerListAdapter workerListAdapter;
    String subid;
    View no_item;
    private Context context;
    private LinearLayout service_main_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_activty);
        setTitle("Vendor");
        no_item = findViewById(R.id.lyt_no_item_);
        service_main_layout = (LinearLayout) findViewById(R.id.service_main_layout);


        boolean flag = CheckNetwork.isInternetAvailable(getApplicationContext());
        if (!flag) //returns true if internet available
        {
            startActivity(new Intent(this, connection.class));
        }


        try {

            Intent i = getIntent();
            Bundle b = i.getExtras();
            subid = b.getString("serviceid");

        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.bottom_navigation_filter) {
            Toast.makeText(this, "wait", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponent() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar_services);
        workerListList = new ArrayList<>();
        workerLists = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_service);
        no_item = (TextView) findViewById(R.id.no_item_message);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        initWorker();
    }

    public void initWorker() {

        PerformNetwork performNetwork = new PerformNetwork(Constent.URL_WORKER_URL + subid, null, CODE_GET_REQUEST);
        Toast.makeText(this, "ok1" + Constent.URL_WORKER_URL + subid, Toast.LENGTH_SHORT).show();
        performNetwork.execute();

    }

    private void refreshList(JSONArray workers) throws JSONException {
        workerLists.clear();
        for (int i = 0; i < workers.length(); i++) {
            JSONObject jsonObject = workers.getJSONObject(i);
            workerLists.add(new WorkerList(
                    jsonObject.getString("id"),
                    jsonObject.getString("shop_name"),
                    jsonObject.getString("city"),
                    jsonObject.getString("country"),
                    jsonObject.getString("shop_phone_no"),
                    jsonObject.getString("cover_photo"),
                    jsonObject.getString("profile_photo"),
                    jsonObject.getString("price"),
                    jsonObject.getString("rating"),
                    jsonObject.getString("time ")
            ));
        }
        workerListAdapter = new WorkerListAdapter(this, workerLists);
        recyclerView.setAdapter(workerListAdapter);

        workerListAdapter.notifyDataSetChanged();


    }


    private class PerformNetwork extends AsyncTask<Void, Void, String> {


        String url;
        HashMap<String, String> params;
        int requestCode;

        public PerformNetwork(String url, HashMap<String, String> params, int requestCode) {
            this.url = url;
            this.params = params;
            this.requestCode = requestCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... voids) {
            RequestHandler handler = new RequestHandler();
            if (requestCode == CODE_POST_REQUEST) {
                return handler.sendPostRequest(url, params);
            }
            if (requestCode == CODE_GET_REQUEST) {

                return handler.sendGetRequest(url);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject object = new JSONObject(s);
                if (!object.getBoolean("error")) {
                    // Toast.makeText(ServiceActivty.this, ""+object.getString("message"), Toast.LENGTH_SHORT).show();
                    refreshList(object.getJSONArray("workers"));
                } else {

                    Log.e("error1", "" + object.getBoolean("error"));
                    LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater = LayoutInflater.from(getApplicationContext());
                    View v = inflater.inflate(R.layout.include_no_item, null);
                    TextView no_data = (TextView) v.findViewById(R.id.no_item_message);
                    no_data.setText("No Service Available");

                    service_main_layout.addView(v);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }


}
