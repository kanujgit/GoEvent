package sharkmedia.com.avampro;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import sharkmedia.com.avampro.ModelClass.CheckNetwork;
import sharkmedia.com.avampro.ModelClass.ServiceCategory;
import sharkmedia.com.avampro.ModelClass.UserLogin;
import sharkmedia.com.avampro.adapter.CategoryAdapter;
import sharkmedia.com.avampro.adapter.ImageAdapter;
import sharkmedia.com.avampro.utils.Constent;
import sharkmedia.com.avampro.utils.SessionManager;
import sharkmedia.com.avampro.utils.SharedPrefManager;
import sharkmedia.com.avampro.utils.Tools;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static ViewPager mPager;
    private RecyclerView recyclerView;
    private static int currentPage = 0;
    private List<ServiceCategory> serviceCategoryList = new ArrayList<>();
    ;
    private static final Integer[] XMEN = {R.drawable.ban_one, R.drawable.ban_two, R.drawable.banner, R.drawable.ban_three};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    private CategoryAdapter categoryAdapter;
    private EditText Location, search;
    private LinearLayout main_layout;
    private ProgressBar progressBar;
    private SessionManager session;
    private boolean login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Home");
        search = (EditText) findViewById(R.id.search);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_home);

        main_layout = (LinearLayout) findViewById(R.id.main_layout);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);


        UserLogin user = SharedPrefManager.getInstance(this).getUser();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            login = true;
        } else {
            login = false;
        }


        boolean flag = CheckNetwork.isInternetAvailable(getApplicationContext());

        if (!flag) //returns true if internet available
        {
            startActivity(new Intent(this, connection.class));
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


        categoryAdapter = new CategoryAdapter(this, serviceCategoryList);

        loadServices(0);

        recyclerView.setAdapter(categoryAdapter);


        initImage();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        TextView nav_user = (TextView) header.findViewById(R.id.nav_user);
        if (login) {
            nav_user.setText(user.getUsername());
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    MenuItem setting, logout;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        setting = menu.findItem(R.id.action_settings);
        logout = menu.findItem(R.id.action_logout);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            login = true;
            logout.setVisible(true);
        } else {
            login = false;
            logout.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }


        if (id == R.id.action_logout) {

            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_location) {

        } else if (id == R.id.nav_order) {

        } else if (id == R.id.nav_profile) {
            if (login) {
                startActivity(new Intent(HomeActivity.this, UserProfileActivity.class));
            } else {
                startActivity(new Intent(HomeActivity.this, GoLoginActivity.class));
            }

        } else if (id == R.id.nav_share) {
            Tools.shareAction(HomeActivity.this);
        } else if (id == R.id.nav_offer) {

        } else if (id == R.id.nav_notification) {
            startActivity(new Intent(this, NotificationActivity.class));

        } else if (id == R.id.nav_about) {
            Tools.aboutAction(HomeActivity.this);

        } else if (id == R.id.nav_contact) {
            startActivity(new Intent(this,ContactActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initImage() {
        for (int i = 0; i < XMEN.length; i++) {
            XMENArray.add(XMEN[i]);
        }

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new ImageAdapter(HomeActivity.this, XMENArray));
        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.indicator);
        circleIndicator.setViewPager(mPager);

        // Auto start on View Pager

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);


        circleIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {


            }

            @Override
            public void onPageSelected(int position) {

                currentPage =position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    private void loadServices(int id) {
        AsyncTask<Integer, Void, Void> asyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Nullable
            @Override
            protected Void doInBackground(Integer... params) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(Constent.SERVICES).build();

                try {
                    Response response = client.newCall(request).execute();
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ServiceCategory serviceCategory = new ServiceCategory(
                                jsonObject.getString("subid"),
                                jsonObject.getString("subname"),
                                jsonObject.getString("service"),
                                jsonObject.getString("subimage"));
                        serviceCategoryList.add(serviceCategory);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                categoryAdapter.notifyDataSetChanged();
            }
        };

        asyncTask.execute(id);
    }

    public void search(View v) {
        startActivity(new Intent(HomeActivity.this, SearchableActivity.class));
    }


    private void logout() {
        SharedPrefManager.getInstance(getApplicationContext()).logout();
    }

    private void logoutUser() {
        session.setLogin(false);
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }

}

