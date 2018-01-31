package sharkmedia.com.avampro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import sharkmedia.com.avampro.ModelClass.CheckNetwork;

public class connection extends AppCompatActivity {

    boolean check;
    private  Button check_connection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_connection);

        check_connection=(Button)findViewById(R.id.check_coonection);
        check_connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                check= CheckNetwork.isInternetAvailable(getApplicationContext());
                if(check)
                {
                    finish();
                }
            }
        });
    }
}
