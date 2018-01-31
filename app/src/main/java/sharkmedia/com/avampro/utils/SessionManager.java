package sharkmedia.com.avampro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Anuj on 11-Dec-17.
 */

public class SessionManager
{

    private static String TAG=SessionManager.class.getSimpleName();
    //sharedPreference
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context _context;
    //Share preference mode
    int PRIVATE_MODE=0;

    //shared preferences file name
    private static final String PREF_NAME = "GoEventLogin";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";


    public SessionManager(Context context) {
        this._context = context;
        pref =_context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
