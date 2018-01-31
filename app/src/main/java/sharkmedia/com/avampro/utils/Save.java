package sharkmedia.com.avampro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import sharkmedia.com.avampro.R;

/**
 * Created by Anuj on 12-Dec-17.
 */

public class Save
{


    Context context;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public Save(Context context)
    {
        this.context = context;
        pref =context.getSharedPreferences(context.getString(R.string.save_preference_name),0);
        editor=pref.edit();
    }

    /**
     * Save string to preferences
     *
     * @param string
     * @param key
     * @param mContext
     * @return
     *
     */

    private static String TAG=Save.class.getSimpleName();
    public static boolean saveString(String string, String key, Context mContext)
    {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getString(R.string.save_preference_name), 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, string);
        Log.d(TAG, "User login session modified!");
        return editor.commit();
    }

    /**
     * Load string from preferences
     *
     * @param key
     * @param mContext
     * @return
     */
    public  static  String loadString(String key, Context mContext)
    {
        SharedPreferences pref;

         pref = mContext.getSharedPreferences(mContext.getString(R.string.save_preference_name),0);
        Log.d(TAG,"User data load!");
        return pref.getString(key, null);
    }

    /**
     * Save int to preferences
     *
     * @param value
     * @param key
     * @param mContext
     * @return
     */
    public static boolean saveInt(int value, String key, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getString(R.string.save_preference_name), 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * Load int from preferences
     *
     * @param key
     * @param mContext
     * @return
     */
    public static int loadInt(String key, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getString(R.string.save_preference_name), 0);
        return prefs.getInt(key, -1);
    }

    /**
     * Save string array to preferences
     *
     * @param array
     * @param arrayName
     * @param mContext
     * @return
     */
    public static boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(mContext.getString(R.string.save_preference_name), 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.length);
        for (int i = 0; i < array.length; i++)
            editor.putString(arrayName + "_" + i, array[i]);
        return editor.commit();
    }





}
