package sharkmedia.com.avampro.fragment;


import android.content.Context;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sharkmedia.com.avampro.ModelClass.WorkerProfile;
import sharkmedia.com.avampro.ProfileActivity;
import sharkmedia.com.avampro.R;
import sharkmedia.com.avampro.RequestHandler.RequestHandler;
import sharkmedia.com.avampro.utils.Constent;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
{

    private static final int CODE_GET_REQUEST = 1024;
    private static final int CODE_POST_REQUEST = 1025;
    String Description_,Location_,WorkingDay_,shop_name_;
    TextView Description;
    TextView WorkingDay;
    TextView Location;
    TextView shop_name;
    Context context;
    String subid;
    ProgressBar progressBar;
    List<WorkerProfile> workerProfileList;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_profile,container,false);
        shop_name=(TextView)rootView.findViewById(R.id.shop_name);
        Description =(TextView)rootView.findViewById(R.id.textView_description);
        WorkingDay =(TextView)rootView.findViewById(R.id.textViewDayWorking);
        Location =(TextView)rootView.findViewById(R.id.textView_location);
        progressBar=(ProgressBar)rootView.findViewById(R.id.progressBar);
        workerProfileList =new ArrayList<>();
        ProfileActivity profileActivity=(ProfileActivity)getActivity();

        subid=profileActivity.getMyData();

        return  rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        context=getActivity();
        getActivity().setTitle("Vendor Profile");


        PerformNetwork performNetwork =new PerformNetwork(Constent.URL_WORKER_PROFILE_INFO+subid,null,CODE_GET_REQUEST);
        performNetwork.execute();
        /*for(int j=0;j<=workerProfileList.size();j++)
        {
            shop_name.setText(workerProfileList.get(0).getShop_name());
            Description.setText(workerProfileList.get(0).getDescription());
            Location.setText(workerProfileList.get(0).getState());
            getDays(workerProfileList.get(0).getShop_date());

            // Toast.makeText(context, ""+shop_name_+Description_+Location_+WorkingDay_, Toast.LENGTH_SHORT).show();
        }*/

    }


    public void refreshList(JSONArray descs) throws JSONException {

        workerProfileList.clear();
        for(int i=0;i<descs.length();i++)
        {
            JSONObject jsonObject =descs.getJSONObject(i);
            workerProfileList.add(new WorkerProfile(
                    jsonObject.getString("shop_name"),
                    jsonObject.getString("description"),
                    jsonObject.getString("state"),
                    jsonObject.getString("shop_date")
            ));


        }
    }




    public  class PerformNetwork extends AsyncTask<Void,Void,String>
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
                    refreshList(jsonObject.getJSONArray("descs"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void getDays(String days)
    {
        String total_day = new String();
        char day[]=days.toCharArray();
        for(int i=0;i<day.length;i++)
        {
            if(day[i]=='0')
            {
                total_day=total_day+" Monday\n";
            }
            if(day[i]=='1')
            {
                total_day=total_day+" Tuesday\n";
            }
            if(day[i]=='2')
            {
                total_day=total_day+" Wednesday\n";
            }
            if(day[i]=='3')
            {
                total_day=total_day+" Thursday\n";
            }
            if(day[i]=='4')
            {
                total_day=total_day+" Friday\n";
            }
            if(day[i]=='5')
            {
                total_day=total_day+" Saturday\n";
            }
            if(day[i]=='6')
            {
                total_day=total_day+" Sunday\n";
            }
        }
        WorkingDay.setText(total_day);


    }
}
