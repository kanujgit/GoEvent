package sharkmedia.com.avampro.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Toast;

import sharkmedia.com.avampro.R;

/**
 * Created by Anuj on 09-Dec-17.
 */

public class Tools
{
    public static boolean isLollypopOrHigher()
    {
        return (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP);
    }


    public static void rateAction(Activity activity)
    {
       Uri uri=Uri.parse("market://details?id=" +activity.getPackageName());
        Intent market= new Intent(Intent.ACTION_VIEW, uri);
        try{

        }catch (ActivityNotFoundException e)
        {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("##")));
        }
    }

    public static void aboutAction(Activity activity)
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle("About");
        builder.setCancelable(false);
        builder.setMessage(R.string.Aboutinfo);
        builder.setNegativeButton("Ok",null);
        builder.show();

    }

    public static void shareAction(Activity activity)
    {
        try
        {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,"GoEvent");
            String send="\n Let me recommend this Application";
                    send=send+"\nwait for Link";
            intent.putExtra(Intent.EXTRA_TEXT,send);
            activity.startActivity(Intent.createChooser(intent,"choose one"));
        }catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    public static void noInternetAlert(Activity activity)
    {
        //error connecting
        final AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setMessage(R.string.no_internet_error);
        builder.show();
    }

    public static void errorAlert(Activity activity, String error ) {
        //error connecting

        //error connecting
        final AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        builder.setTitle(R.string.error_title);
        builder.setMessage(R.string.no_internet_error);
        builder.show();

    }

}
