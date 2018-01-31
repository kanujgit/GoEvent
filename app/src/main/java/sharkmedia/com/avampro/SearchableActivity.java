package sharkmedia.com.avampro;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sharkmedia.com.avampro.ModelClass.SearchService;
import sharkmedia.com.avampro.ModelClass.ServiceCategory;
import sharkmedia.com.avampro.adapter.SearchAdapter;
import sharkmedia.com.avampro.utils.Constent;

public class SearchableActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{
    ListView listView;

    private ActionBar actionBar;
    private ProgressBar progressBar;
    SearchView searchView;
    List<SearchService> serviceArrayList =new ArrayList<SearchService>();
   String[] services = new String[] { "Accounting", "Advertising", "Data Entry",
        "Digital Marketing", "Illustrating", "Music Engraving", "Sawmilling", "Stained Glass",
        "Animation", "E Commerce","Technical Design","Software Development", "Wedding Officiant",
        "Photo Booth Rental","Event Photography", "Face Painting"};
    String []rank = new String[] {"3", "4", "5", "6", "7", "8", "9", "10" ,"11","12","13", "14", "15", "16", "17", "18", "19", "20" ,"21"};
    SearchAdapter searchAdapter;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Search Services");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_searchable);
        listView =(ListView)findViewById(R.id.listview);
        searchView=(SearchView)findViewById(R.id.search_view);
        initComponent();

    //    loadSubService(0);
        //Generate Data List
        for(int i=0;i<services.length;i++)
        {
            SearchService service =new SearchService(rank[i],services[i]);
            //bind all the data
            serviceArrayList.add(service);
        }
        searchAdapter =new SearchAdapter(this,serviceArrayList);
        listView.setAdapter(searchAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent=new Intent(SearchableActivity.this,ServiceActivty.class);
                String subid=serviceArrayList.get(position).getSubid();
                intent.putExtra("serviceid",subid);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {
        text=newText;
        searchAdapter.filter(text);
        return false;
    }

    private void initComponent()
    {

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }


    public void loadSubService(int id)
    {
        AsyncTask<Integer,Void,Void> asyncTask =new AsyncTask<Integer, Void, Void>()
        {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected Void doInBackground(Integer... params)
            {
                OkHttpClient client =new OkHttpClient();
                Request request = new Request.Builder().url(Constent.SERVICES).build();
                try
                {
                    Response response =client.newCall(request).execute();
                    JSONArray jsonArray =new JSONArray(response.body().string());
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        SearchService service =new SearchService(
                                jsonObject.getString("subid"),
                                jsonObject.getString("subname")
                               ) ;
                                serviceArrayList.add(service);
                                Log.e("value is" , serviceArrayList.get(i).getSubname());
                    }
                   /* for (int i=0;i<=serviceArrayList.size();i++)
                    {
                        Log.e("value is" , serviceArrayList.get(i).toString());
                    }*/
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return  null;
            }

            @Override
            protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);
                progressBar.setVisibility(View.GONE);
                searchAdapter.notifyDataSetChanged();

            }

            /*@Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }*/
        };
        asyncTask.execute(id);
    }

}
