package sharkmedia.com.avampro.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import sharkmedia.com.avampro.ModelClass.ServiceCategory;
import sharkmedia.com.avampro.ModelClass.WorkerList;
import sharkmedia.com.avampro.ProfileActivity;
import sharkmedia.com.avampro.R;

/**
 * Created by Anuj on 01-Dec-17.
 */

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.MyViewHolder>
{

    private Context context;
    private List<WorkerList> workerListList;

    public WorkerListAdapter(Context context, List<WorkerList> workerListList) {
        this.context = context;
        this.workerListList = workerListList;
    }

    @Override
    public WorkerListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View itemView = LayoutInflater.from(context).inflate(R.layout.book_worker_card_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WorkerListAdapter.MyViewHolder holder, final int position)
    {
        Glide.with(context).load("http://goevent.co.in/local/images/shop/"+workerListList.get(position).getCover_photo()).placeholder(R.drawable.noimage).into(holder.Banner);
        Glide.with(context).load("http://goevent.co.in/local/images/shop/"+workerListList.get(position).getProfile_photo()).placeholder(R.drawable.noimage).into(holder.profile);

        holder.name.setText(workerListList.get(position).getShop_name());
        holder.location.setText(workerListList.get(position).getCity());

        holder.time.setText(workerListList.get(position).getTime());
        holder.price.setText(workerListList.get(position).getPrice());
        holder.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String id=workerListList.get(position).getId();
                Intent intent=new Intent(context,ProfileActivity.class);
                intent.putExtra("shopid",id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return workerListList.size();
    }

    public class MyViewHolder  extends RecyclerView.ViewHolder
    {
        public ImageView Banner,profile;
        public TextView name,location,price,time;
        public RatingBar ratingBar;
        public Button book;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            Banner =(ImageView) itemView.findViewById(R.id.imageView_banner);
            profile =(ImageView) itemView.findViewById(R.id.imageview_profile);
            name =(TextView)itemView.findViewById(R.id.textView_profile);
           // ratingBar=(RatingBar)itemView.findViewById(R.id.rating);
            location =(TextView)itemView.findViewById(R.id.textView_location);
            price =(TextView)itemView.findViewById(R.id.textView_price);
            time =(TextView)itemView.findViewById(R.id.textView_time);
            book = (Button)itemView.findViewById(R.id.button_book);


        }
    }
}



