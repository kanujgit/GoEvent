package sharkmedia.com.avampro.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sharkmedia.com.avampro.ModelClass.WorkerRating;
import sharkmedia.com.avampro.ProfileActivity;
import sharkmedia.com.avampro.R;
import sharkmedia.com.avampro.RequestHandler.RequestHandler;
import sharkmedia.com.avampro.adapter.RatingAdapter;
import sharkmedia.com.avampro.utils.Constent;

/**
 * A simple {@link Fragment} subclass.
 */
public class RatingFragment extends Fragment
{
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;

    ProgressBar progressBar;
    RecyclerView recyclerView;
    String subid;
    Context context;
    List<WorkerRating> ratingList;
    RatingAdapter ratingAdapter;
    private LinearLayout service_main_layout;


    public RatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView=inflater.inflate(R.layout.fragment_service,container,false);
        progressBar =(ProgressBar)rootView.findViewById(R.id.progressBar_service);
        recyclerView =(RecyclerView)rootView.findViewById(R.id.service_recycler);
        service_main_layout=(LinearLayout)rootView.findViewById(R.id.service_main_layout);
        ratingList =new ArrayList<>();
        //det data from
        ProfileActivity profileActivity=(ProfileActivity)getActivity();
        subid=profileActivity.getMyData();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        context=getActivity();
        getActivity().setTitle("Rating");
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        initServices();


    }
    public void initServices()
    {
        PerformNetwork performNetwork =new PerformNetwork(Constent.URL_WORKER_RATING+subid,null,CODE_GET_REQUEST);
        performNetwork.execute();

    }

    public  void refreshList(JSONArray service) throws JSONException
    {
        ratingList.clear();
        for(int i=0;i<service.length();i++)
        {
            JSONObject jsonObject =service.getJSONObject(i);
            ratingList.add(new WorkerRating(
                    jsonObject.getString("email"),
                    jsonObject.getString("comment"),
                    jsonObject.getString("rating"),
                    jsonObject.getString("rid")
            ));
        }
        ratingAdapter =new RatingAdapter(context,ratingList);
        recyclerView.setAdapter(ratingAdapter);
    }

    private  class PerformNetwork extends AsyncTask<Void,Void,String>
    {
        String url;
        HashMap<String, String> params;
        int requestCode;

        public PerformNetwork(String url, HashMap<String, String> params, int requestCode)
        {
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
        protected String doInBackground(Void... voids)
        {
            RequestHandler requestHandler =new RequestHandler();
            if(requestCode ==CODE_POST_REQUEST)
            {
                return requestHandler.sendPostRequest(url,params);
            }

            if(requestCode == CODE_GET_REQUEST)
            {
                return requestHandler.sendGetRequest(url);
            }
            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject jsonObject =new JSONObject(s);
                if (!jsonObject.getBoolean("error"))
                {
                    refreshList(jsonObject.getJSONArray("rating"));
                }
                else{


                    Log.e("error1",""+jsonObject.getBoolean("error"));
                    LayoutInflater inflater=(LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    inflater=LayoutInflater.from(getActivity());
                    View v=inflater.inflate(R.layout.include_no_item,null);
                    TextView no_data=(TextView)v.findViewById(R.id.no_item_message);
                    no_data.setText("No Rating Available");

                    service_main_layout.addView(v);



                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
