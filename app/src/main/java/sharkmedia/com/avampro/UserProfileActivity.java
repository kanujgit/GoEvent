package sharkmedia.com.avampro;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import sharkmedia.com.avampro.ModelClass.UserLogin;
import sharkmedia.com.avampro.utils.SharedPrefManager;

public class UserProfileActivity extends AppCompatActivity {

    CollapsingToolbarLayout collapsingToolbar;
    private EditText email,name,password,phone;
    private ImageView profile;
    private RadioGroup radioGroup;
    private String gender;
    private UserLogin user;
    private RadioButton gender_male,gender_female;
    private Button updateButton;
    private String type;
    private  RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_user);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.t);
        collapsingToolbar.setTitle("Profile");
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);

        //get User Details
        user = SharedPrefManager.getInstance(this).getUser();

        initComponent();
        setUpdate();


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                update();
            }
        });






    }
    public void initComponent()
    {
        email=(EditText)findViewById(R.id.edit_email);
        name=(EditText)findViewById(R.id.edit_name);
        password=(EditText)findViewById(R.id.edit_password);
        phone=(EditText)findViewById(R.id.edit_phone);
        radioGroup=(RadioGroup)findViewById(R.id.gender);

        gender_male=(RadioButton)findViewById(R.id.gender_male);
        gender_female=(RadioButton)findViewById(R.id.gender_female);
        updateButton=(Button)findViewById(R.id.updateButton);

    }

    public void setUpdate()
    {
        name.setText(user.getUsername());
        email.setText(user.getUsername());
        phone.setText(user.getPhone());
        type=user.getGender().toString();

        if(type.equalsIgnoreCase("Male"))
        {
            gender_male.setChecked(true);
        }
        else if(type.equalsIgnoreCase("FeMale"))
        {
            gender_female.setChecked(true);
        }

    }



    public void update()
    {
        String u_name       = name.getText().toString().trim();
        String u_email      = email.getText().toString().trim();
        String u_password   = password.getText().toString().trim();
        String u_contact_no = phone.getText().toString().trim();

        int selectId=radioGroup.getCheckedRadioButtonId();
        radioButton=(RadioButton)findViewById(selectId);
        //gender=radioButton.getText().toString();
        String type=radioButton.getText().toString();



        if (TextUtils.isEmpty(u_name)) {
            name.setError("Please enter name");
            name.requestFocus();
        }else {
            if (TextUtils.isEmpty(u_email )) {
                email.setError("Please enter Email");
                email.requestFocus();
            }else{
                if(TextUtils.isEmpty(u_password)){
                    password.setError("Please enter Password");
                    password.requestFocus();
                }else{
                    if(TextUtils.isEmpty(u_contact_no)){
                        phone.setError("Please Enter Contact number");
                        phone.requestFocus();
                    }else{
                        if(type.isEmpty())
                        {
                            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, u_name+" "+u_email+" "+u_password+" "+type, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

        }
    }
}
