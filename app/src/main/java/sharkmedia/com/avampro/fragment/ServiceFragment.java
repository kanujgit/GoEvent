package sharkmedia.com.avampro.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sharkmedia.com.avampro.ModelClass.ServiceBook;
import sharkmedia.com.avampro.ProfileActivity;
import sharkmedia.com.avampro.R;
import sharkmedia.com.avampro.RequestHandler.RequestHandler;
import sharkmedia.com.avampro.adapter.ServiceBookAdapter;
import sharkmedia.com.avampro.adapter.WorkerListAdapter;
import sharkmedia.com.avampro.utils.Constent;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment
{
    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    Context context;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ServiceBookAdapter serviceBookAdapter;
    List<ServiceBook> serviceBookList;
    String subid;


    public ServiceFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_service,container,false);
        progressBar =(ProgressBar)rootView.findViewById(R.id.progressBar_service);
        recyclerView =(RecyclerView)rootView.findViewById(R.id.service_recycler);
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
        getActivity().setTitle("Services");
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        Toast.makeText(context, ""+subid, Toast.LENGTH_SHORT).show();
        serviceBookList =new ArrayList<>();
        initServices();

    }

    public void initServices()
    {

        PerformNetwork performNetwork =new PerformNetwork(Constent.URL_WORKER_SERVICE+subid,null,CODE_GET_REQUEST);
        Toast.makeText(context, ""+Constent.URL_WORKER_SERVICE+subid, Toast.LENGTH_SHORT).show();
        performNetwork.execute();


    }

    public  void refreshList(JSONArray workers) throws JSONException
    {
        serviceBookList.clear();
        for(int i=0;i<workers.length();i++)
        {
                JSONObject jsonObject =workers.getJSONObject(i);
                serviceBookList.add(new ServiceBook(
                                jsonObject.getString("subservice_id"),
                                jsonObject.getString("price"),
                                jsonObject.getString("time"),
                                jsonObject.getString("user_id"),
                                jsonObject.getString("shop_id"),
                                jsonObject.getString("subname"),
                                jsonObject.getString("subimage"))
                );
        }
        serviceBookAdapter=new ServiceBookAdapter(context,serviceBookList);
        recyclerView.setAdapter(serviceBookAdapter);
        serviceBookAdapter.notifyDataSetChanged();

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
                    refreshList(jsonObject.getJSONArray("workers"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
