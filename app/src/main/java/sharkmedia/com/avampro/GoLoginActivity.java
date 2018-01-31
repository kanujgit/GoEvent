package sharkmedia.com.avampro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import sharkmedia.com.avampro.ModelClass.UserLogin;
import sharkmedia.com.avampro.RequestHandler.RequestHandler;
import sharkmedia.com.avampro.utils.Constent;
import sharkmedia.com.avampro.utils.SessionManager;
import sharkmedia.com.avampro.utils.SharedPrefManager;

public class GoLoginActivity extends AppCompatActivity {

    private TextView skip_button;
    private EditText email;
    private EditText password;
    private Button login;
    private String user_email, user_password;
    public ProgressDialog progressDialog;
    private static final String TAG = GoLoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

/*
        ImageView background = (ImageView) findViewById(R.id.background);
        Picasso.with(this).load(R.drawable.app_name).fit().into(background);*/


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_button);
        skip_button = (TextView) findViewById(R.id.skip_button);

        // Progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);


        // create a new Account

        final TextView registerBtn = (TextView) findViewById(R.id.register_text);
      /*  registerBtn.setOnTouchListener(new View.OnTouchListener() {
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
        });*/


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(GoLoginActivity.this, RegistrationActivity.class));
            }
        });


        skip_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        ((TextView) view).setTextColor(ContextCompat.getColor(GoLoginActivity.this, R.color.colorPrimary)); // white
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
                Intent intent = new Intent(GoLoginActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        // login button click

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginClick();
            }
        });
    }


    public void loginClick() {
        user_email = email.getText().toString().trim();
        user_password = password.getText().toString().trim();

        if (TextUtils.isEmpty(user_email)) {
            email.setError("Please enter name");
            email.requestFocus();
        } else {
            if (TextUtils.isEmpty(user_password)) {
                password.setError("Please enter Password");
                password.requestFocus();
            } else {
                checkLogin(user_email, user_password);
            }
        }
    }

    public void checkLogin(final String email, final String password) {

        class UserLoginClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(GoLoginActivity.this, "Loading Data", null, true, true);

            }


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("name", email);
                params.put("password", password);


                //returning the response
                return requestHandler.sendPostRequest(Constent.URL_LOGIN, params);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");
                        Log.e("error", obj.getString("message"));
                        Toast.makeText(GoLoginActivity.this, s.toString(), Toast.LENGTH_LONG).show();
                        //creating a new user object
                        UserLogin user = new UserLogin(
                                userJson.getInt("id"),
                                userJson.getString("name"),
                                userJson.getString("email"),
                                userJson.getString("gender"),
                                userJson.getString("phone"),
                                userJson.getString("photo"),
                                userJson.getString("admin")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                        //starting the Home activity
                        finish();
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(GoLoginActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }

        UserLoginClass ul = new UserLoginClass();
        ul.execute();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
