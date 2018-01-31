package sharkmedia.com.avampro;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sharkmedia.com.avampro.ModelClass.CheckNetwork;
import sharkmedia.com.avampro.ModelClass.SearchService;
import sharkmedia.com.avampro.ModelClass.ServiceBook;
import sharkmedia.com.avampro.RequestHandler.RequestHandler;
import sharkmedia.com.avampro.fragment.ContectVendorFragment;
import sharkmedia.com.avampro.fragment.GalleryFragment;
import sharkmedia.com.avampro.fragment.ProfileFragment;
import sharkmedia.com.avampro.fragment.RatingFragment;
import sharkmedia.com.avampro.fragment.ServiceFragment;
import sharkmedia.com.avampro.utils.Constent;

public class ProfileActivity extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {


    private static final String TAG = ProfileActivity.class.getSimpleName();
    private Fragment fragment;
    private FragmentManager fragmentManager;
    String shopid;
    AHBottomNavigation bottomNavigation_ahb;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_service:
                    fragment = new ServiceFragment();
                    break;

                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    break;

                case R.id.navigation_rating:
                    fragment = new RatingFragment();
                    break;

                case R.id.navigation_gallery:
                    fragment = new GalleryFragment();
                    break;

                case R.id.navigation_contact_vendor:
                    fragment = new ContectVendorFragment();
                    break;
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        boolean flag = CheckNetwork.isInternetAvailable(getApplicationContext());

        if (!flag) //returns true if internet available
        {
            startActivity(new Intent(this, connection.class));
        }

        try {
            Bundle b = getIntent().getExtras();
            shopid = b.getString("shopid");
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManager = getSupportFragmentManager();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_container, new ServiceFragment());
        transaction.commit();

        bottomNavigation_ahb = (AHBottomNavigation) findViewById(R.id.ahb);
        bottomNavigation_ahb.setOnTabSelectedListener(this);
        bottomNavigation_ahb.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        this.createNavItems();
    }


    public void createNavItems() {
        AHBottomNavigationItem home = new AHBottomNavigationItem("Service", R.drawable.ic_menu_manage);
        AHBottomNavigationItem myMenu = new AHBottomNavigationItem("Profile", R.drawable.users);
        AHBottomNavigationItem search = new AHBottomNavigationItem("Reviews", R.drawable.ic_stat_star);
        AHBottomNavigationItem track = new AHBottomNavigationItem("Gallery", R.drawable.ic_menu_gallery);
        AHBottomNavigationItem wallet = new AHBottomNavigationItem("Contact Vendor", R.drawable.contect);

        bottomNavigation_ahb.addItem(home);
        bottomNavigation_ahb.addItem(myMenu);
        bottomNavigation_ahb.addItem(search);
        bottomNavigation_ahb.addItem(track);
        bottomNavigation_ahb.addItem(wallet);


        // bottomNavigation_ahb.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));
        // bottomNavigation_ahb.setColored(true);
        //  bottomNavigation_ahb.setForceTint(true);
        bottomNavigation_ahb.setCurrentItem(0);


        //set color after click
        bottomNavigation_ahb.setAccentColor(Color.parseColor("#0288D1"));
        bottomNavigation_ahb.setInactiveColor(Color.parseColor("#747474"));


    }


    public String getMyData() {
        return shopid;
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        if (position == 0) {

            fragment = new ServiceFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();

        } else if (position == 1) {

            fragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();

        } else if (position == 2) {

            fragment = new RatingFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();

        } else if (position == 3) {

            fragment = new GalleryFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();

        } else if (position == 4) {

            fragment = new ContectVendorFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
        }
        // checkMenuVisibility();
        return true;
    }
}
