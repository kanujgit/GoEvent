package sharkmedia.com.avampro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import sharkmedia.com.avampro.LoginActivity;

/**
 * Created by Anuj on 13-Dec-17.
 */

public class User
{
    int id;
    String username;
    String email;

    public interface onLoginListener {
        void onLogin(String email);
    }

    interface onUserListener {
        void onUser(User user);
    }

    interface onDoneListener {
        void onDone();

        void onError();
    }

    /**
     * Login user with email an password
     * @param activity
     * @param email
     * @param password
     * @param loginListener
     */

    public static void login(final Activity activity, final String email, final String password, final onLoginListener loginListener)
    {

        // Instantiate the RequestQueue.
        String url = Constent.LOGIN_USER + "?email=" + email + "&password=" + password;
        loginWithUrl(activity, url, new onLoginListener() {
            @Override
            public void onLogin(String email) {
                setCurrentUserCredentials(activity, email, password);
                loginListener.onLogin(email);
            }
        });
    }




    public static void loginWithUrl(final Activity activity, String url, final onLoginListener loginListener)
    {
        RequestQueue queue = Volley.newRequestQueue(activity);
        JsonObjectRequest arrayreq = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        System.out.println(response.toString(2));
                        loginListener.onLogin(response.getString("email"));
                    } else {
                        Tools.errorAlert(activity, response.getString("error"));
                    }
                }
                // Try and catch are included to handle any errors due to JSON
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            // Handles errors that occur due to Volley
            public void onErrorResponse(VolleyError error) {
                Tools.noInternetAlert(activity);
                Log.e("Volley", "Error");
                error.printStackTrace();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(arrayreq);
    }

    /*
    * save user to local storage
    * @param activity
    * @param  email
    * @param  password
    * */

    private static void setCurrentUserCredentials(Context context, String email, String password)
    {
        Save.saveString(email, "current_user_email", context);
        Save.saveString(password, "current_user_password", context);
    }


    public static void setCurrentUserCredentials(Context context, String email) {
        Save.saveString(email, "current_user_email", context);
    }


    /**
     * Get user email from local storage
     *
     * @param context
     * @return
     */
    public static String getCurrentUserEmail(Context context) {
        return Save.loadString("current_user_email",context);
    }

    /**
     * Get user password from local storage
     *
     * @param context
     * @return
     */
    public static String getCurrentUserPassword(Context context) {
        return Save.loadString("current_user_password", context);
    }


    /**
     * User is logged in if the token/password and email are saved locally
     * @param
     * @return
     */
    public static boolean isUserLoggedIn(Context context) {
        if (getCurrentUserEmail(context) != null) {
            if (getCurrentUserEmail(context).length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * If user is not logged in go to login page
     * @param context
     * @return
     */
    public static boolean isUserLoggedInElseTry(Context context) {
        if (isUserLoggedIn(context)) {
            return true;
        }

        //go to login page
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        return false;
    }


    /**
     * Call this function to logout user
     *
     * @param context
     */
    public static void logout(Context context) {
        setCurrentUserCredentials(context, "", "");
       // LoginManager.getInstance().logOut();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

    }



}
