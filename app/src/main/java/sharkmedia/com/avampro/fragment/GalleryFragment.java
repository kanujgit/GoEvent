package sharkmedia.com.avampro.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sharkmedia.com.avampro.ModelClass.GridItem;
import sharkmedia.com.avampro.ProfileActivity;
import sharkmedia.com.avampro.R;
import sharkmedia.com.avampro.adapter.VendorGalleryAdapter;
import sharkmedia.com.avampro.utils.Constent;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment
{
    private Context context;
    private GridView mgridView;
     ProgressBar mprogressBar;
    private VendorGalleryAdapter mGridAdapter;
    //private ArrayList<GridItem> mGridData;
     private  List<GridItem> mGridData= new ArrayList<>();
    String subid;





    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView= inflater.inflate(R.layout.fragment_gallery,container,false);
        // Inflate the layout for this fragment
        mgridView =(GridView)rootView.findViewById(R.id.gridView);
        mprogressBar=(ProgressBar)rootView.findViewById(R.id.progressBar_gallery);
        ProfileActivity profileActivity=(ProfileActivity)getActivity();
        subid=profileActivity.getMyData();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        context =getActivity();
        getActivity().setTitle("Gallery");
        mGridData = new ArrayList<>();
        mGridAdapter=new VendorGalleryAdapter(getContext(),R.layout.gallery_grid_item_layout,mGridData);
        loadData(0);
        mgridView.setAdapter(mGridAdapter);

        mprogressBar.setVisibility(View.VISIBLE);
    }
    // Downloading data asynchronously

    public void loadData(int id)
    {
        AsyncTask<Integer,Void ,Void> asyncTask= new AsyncTask<Integer, Void, Void>()
        {

            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                mprogressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Integer... params)
            {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(Constent.URL_WORKER_GALLERY+subid).build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        GridItem gridItem = new GridItem(
                                jsonObject.getString("user_id"),
                                jsonObject.getString("image"));
                        mGridData.add(gridItem);


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                mprogressBar.setVisibility(View.GONE);
                mGridAdapter.notifyDataSetChanged();
            }
        };
        asyncTask.execute(id);
    }
}
