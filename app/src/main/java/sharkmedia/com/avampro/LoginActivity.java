package sharkmedia.com.avampro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import sharkmedia.com.avampro.RequestHandler.AppController;
import sharkmedia.com.avampro.utils.Constent;
import sharkmedia.com.avampro.utils.Save;
import sharkmedia.com.avampro.utils.SessionManager;
import sharkmedia.com.avampro.utils.User;

public class LoginActivity extends AppCompatActivity {

    Activity activity;
    private Toolbar toolbar;
    private TextView registration_text,skip_button;
    private EditText email;
    private  EditText password;
    private Button login;
    private  String user_email,user_password;
    private ProgressDialog pDialog;
    private SessionManager session;

    private static final String TAG = LoginActivity.class.getSimpleName();
   // Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;

//        toolbar =((Toolbar)findViewById(R.id.toolbar));
        email=(EditText)findViewById(R.id.email);
        password =(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login_button);
        skip_button =(TextView)findViewById(R.id.skip_button);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        session =new SessionManager(getApplicationContext());


        Save save =new Save(activity);
        //go directly to Home Activity if user already logged in.
        if (User.isUserLoggedIn(activity)) {
            Intent intent = new Intent(activity, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }



        // Check if user is already logged in or not
     /*   if (session.isLoggedIn())
        {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, UserProfileActivity.class);
            startActivity(intent);
            finish();
        }*/
        /*
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });*/


        //create a new account
        final TextView registerBtn = (TextView) findViewById(R.id.register_text);
        registerBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((TextView) view).setTextColor(0xFFFFFFFF);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        ((TextView) view).setTextColor(ContextCompat.getColor(activity, R.color.colorWhite));
                        break;
                    case MotionEvent.ACTION_UP:
                        ((TextView) view).setTextColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
                        break;
                }
                return false;
            }
        });

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        login();
                    }
                });

            registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
             startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });




        skip_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((TextView) view).setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorPrimary)); // white
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        ((TextView) view).setTextColor(0xFFFFFFFF); // lightblack
                        break;
                    case MotionEvent.ACTION_UP:
                        ((TextView) view).setTextColor(0xFFFFFFFF); // lightblack
                        break;
                }
                return false;
            }
        });

        skip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,UserProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    //login method
    public  void login()
    {
        user_email =email.getText().toString().trim();
        user_password=password.getText().toString().trim();

        if (TextUtils.isEmpty(user_email)) {
            email.setError("Please enter name");
            email.requestFocus();
        }else {
            if (TextUtils.isEmpty(user_password)) {
                password.setError("Please enter Password");
                password.requestFocus();
            }
            else{
                checkLogin(user_email,user_password);
            }
        }
    }

    public void checkLogin(final String u_email,final  String u_password)
    {
        User.login(activity, u_email, u_password, new User.onLoginListener(){
            @Override
            public void onLogin(String email)
            {
                Intent intent = new Intent(activity, HomeActivity.class);//HomeActivity.class
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void checkLogin1(final String u_email, final String u_password)
    {
        //Tag user to cancel request
       // RequestQueue queue = Volley.newRequestQueue(this);
        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
        showDialog();
        StringRequest stringRequest =new StringRequest(Request.Method.POST, Constent.LOGIN_USER, new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error =jsonObject.getBoolean("error");

                    //check error node in json
                    if(!error){
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);
                        Toast.makeText(activity, "Login SuccessFull", Toast.LENGTH_SHORT).show();
                        String errorMsg = jsonObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        // Error in login. Get the error message
                        String errorMsg = jsonObject.getString("error_msg");
                        Toast.makeText(getApplicationContext(),errorMsg, Toast.LENGTH_LONG).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    Log.e(TAG, "Login Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show();
                    hideDialog();

                }
            })  {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String,String>();
                params.put("email", u_email);
                params.put("password", u_password);
                return  params;
            }
        };

        //adding request to queue
        AppController.getInstance().addToRequestQueue(stringRequest,tag_string_req);
    }

    private void showDialog()
    {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog()
    {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
