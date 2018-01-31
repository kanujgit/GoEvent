package sharkmedia.com.avampro.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import sharkmedia.com.avampro.ModelClass.ServiceBook;
import sharkmedia.com.avampro.R;
import sharkmedia.com.avampro.fragment.ServiceFragment;

/**
 * Created by Anuj on 24-Dec-17.
 */

public class ServiceBookAdapter extends RecyclerView.Adapter<ServiceBookAdapter.MyViewHolder>
{

    private Context context;
    private List<ServiceBook> serviceBookList;


    public ServiceBookAdapter(Context context, List<ServiceBook> serviceBookList)
    {
        this.context = context;
        this.serviceBookList = serviceBookList;
    }

    @Override
    public ServiceBookAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(context).inflate(R.layout.service_cardview,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ServiceBookAdapter.MyViewHolder holder, int position)
    {
        Picasso.with(context).load("http://goevent.co.in/local/images/subservicephoto/"+serviceBookList.get(position).getSubimage()).placeholder(R.drawable.noimage).into(holder.imageView_profile);
        holder.textView_service.setText(serviceBookList.get(position).getSubname());
        holder.textView_price.setText(serviceBookList.get(position).getPrice());
        holder.textView_time.setText(serviceBookList.get(position).getTime());
        holder.button_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Dialog dialog =new Dialog(context);
                dialog.setContentView(R.layout.book_service_dialoge);
                dialog.setCancelable(true);
                Toast.makeText(context, "Booking done..", Toast.LENGTH_SHORT).show();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return serviceBookList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public ImageView imageView_profile;
        public TextView textView_service;
        public TextView textView_price,textView_time;
        public Button button_book;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            imageView_profile=(ImageView)itemView.findViewById(R.id.imageView_profile);
            textView_service = (TextView)itemView.findViewById(R.id.textView_service);
            textView_price = (TextView)itemView.findViewById(R.id.textView_price);
            textView_time = (TextView)itemView.findViewById(R.id.textView_time);
            button_book = (Button)itemView.findViewById(R.id.button_book);

        }


    }
}
