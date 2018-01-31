package sharkmedia.com.avampro.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import sharkmedia.com.avampro.GoLoginActivity;
import sharkmedia.com.avampro.HomeActivity;
import sharkmedia.com.avampro.LoginActivity;
import sharkmedia.com.avampro.ModelClass.UserLogin;
import sharkmedia.com.avampro.ModelClass.UserRegister;

/**
 * Created by Anuj on 12-Jan-18.
 */

public class SharedPrefManager {
    //the constants
    private static final String SHARED_PREF_NAME = "GoEventLogin";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_GENDER = "keygender";
    private static final String KEY_ID = "keyid";
    private static final String KEY_PHOTO = "keyphoto";
    private static final String KEY_PHONE = "keyphone";
    private static final String KEY_USER_TYPE = "keyusertype";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(UserLogin user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_GENDER, user.getGender());
        editor.putString(KEY_PHOTO, user.getPhoto());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_USER_TYPE, user.getUser_type());
        editor.apply();
    }

    public void userRegister(UserRegister register) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, register.getId());
        editor.putString(KEY_USERNAME, register.getName());
        editor.putString(KEY_EMAIL, register.getEmail());
        editor.putString(KEY_GENDER, register.getGender());
        editor.putString(KEY_PHONE, register.getPhone());
        editor.putString(KEY_USER_TYPE, register.getUser_type());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public UserLogin getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserLogin(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getString(KEY_PHOTO, null),
                sharedPreferences.getString(KEY_USER_TYPE, null)

        );
    }

    //this method will logout the user

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, GoLoginActivity.class));
    }
}