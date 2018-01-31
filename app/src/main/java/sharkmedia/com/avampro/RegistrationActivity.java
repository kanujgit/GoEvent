package sharkmedia.com.avampro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import sharkmedia.com.avampro.ModelClass.UserLogin;
import sharkmedia.com.avampro.ModelClass.UserRegister;
import sharkmedia.com.avampro.RequestHandler.RequestHandler;
import sharkmedia.com.avampro.utils.SharedPrefManager;

import static sharkmedia.com.avampro.utils.Constent.URL_REGISTER;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email, username, password, phone;
    private Button signup;
    private RadioGroup gender_id;
    private Spinner user_type;
    private String gender;
    private ProgressDialog progressDialog;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        //get data from xml file
        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        gender_id = (RadioGroup) findViewById(R.id.gender);
        user_type = (Spinner) findViewById(R.id.spinnerType);
        phone = (EditText) findViewById(R.id.phone);
        signup = (Button) findViewById(R.id.register_button);

        gender_id.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton checkedRadioButton = (RadioButton) findViewById(checkedId);
                gender = checkedRadioButton.getText().toString();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }


    private void signUp() {
        final String s_email = email.getText().toString().trim();
        final String s_username = username.getText().toString().trim();
        final String s_password = password.getText().toString().trim();
        final String s_phone = phone.getText().toString().trim();
        final String usertype = user_type.getSelectedItem().toString();

        if (usertype.equals("Customer")) {
            type = "1";
        } else if (usertype.equals("Service Provider")) {
            type = "2";
        }

        class Register extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(RegistrationActivity.this, "Loading Data", null, true, true);
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();

                params.put("name", s_username);
                params.put("email", s_email);
                params.put("password", s_password);
                params.put("phone", s_phone);
                params.put("gender", gender);
                params.put("user_type", type);

                //returning the response
                return requestHandler.sendPostRequest(URL_REGISTER, params);
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
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("user");

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

                        //starting the profile activity
                        Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        //                     finish();
//                       startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    } else {

                        Toast.makeText(getApplicationContext(), obj.getString("message_p") + " Some Error Occurred", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Register register = new Register();
        register.execute();
    }

}
