package sharkmedia.com.avampro.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sharkmedia.com.avampro.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContectVendorFragment extends Fragment {


    Context context;
    public ContectVendorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_contect_vendor,container,false);
        final EditText YourName=(EditText)rootView.findViewById(R.id.YourName);
        final EditText  phoneNumber=(EditText)rootView.findViewById(R.id.phonenumber);
        final EditText YourEmail=(EditText)rootView.findViewById(R.id.YourEmail);
        final  EditText yourmessage=(EditText)rootView.findViewById(R.id.message);
        Button book=(Button) rootView.findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name =YourName.getText().toString().trim();
                String email=YourEmail.getText().toString().trim();
                String phone=phoneNumber.getText().toString().trim();
                String message=yourmessage.getText().toString().trim();
                if(name.isEmpty())
                {
                    YourName.setError("Enter the Name");
                    YourName.requestFocus();
                }else{
                    if(email.isEmpty())
                    {
                        YourEmail.setError("Enter the Email");
                        YourEmail.requestFocus();
                    }else{
                        if(phone.isEmpty())
                        {
                            phoneNumber.setError("Enter the Phone Number");
                            phoneNumber.requestFocus();
                        }else{
                            if(message.isEmpty())
                            {
                                yourmessage.setError("Enter the Message");
                                yourmessage.requestFocus();
                            }else{
//                                contactVendor(name,email,phone,message);
                                Toast.makeText(context, "Query Successfully Submit", Toast.LENGTH_SHORT).show();
                                YourName.setText("");
                                YourEmail.setText("");
                                yourmessage.setText("");
                                phoneNumber.setText("");
                            }
                        }
                    }
                }


            }
        });


        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        context =getActivity();
        getActivity().setTitle("Contact Vendor");
        initServices();

    }

    public void initServices()
    {

        //PerformNetwork performNetwork =new PerformNetwork(Constent.URL_WORKER_SERVICE+subid,null,CODE_GET_REQUEST);
       //oast.makeText(context, ""+Constent.URL_WORKER_SERVICE+subid, Toast.LENGTH_SHORT).show();
        //performNetwork.execute();


    }

    public void contactVendor(String name,String phone,String email,String message)
    {


    }
}
