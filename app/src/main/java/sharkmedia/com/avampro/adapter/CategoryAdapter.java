package sharkmedia.com.avampro.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

import sharkmedia.com.avampro.ModelClass.ServiceCategory;
import sharkmedia.com.avampro.R;
import sharkmedia.com.avampro.SearchableActivity;
import sharkmedia.com.avampro.ServiceActivty;

/**
 * Created by Anuj on 22-Nov-17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    private List<ServiceCategory> serviceCategoryList;

    public CategoryAdapter(Context context, List<ServiceCategory> serviceCategoryList) {
        this.context = context;
        this.serviceCategoryList = serviceCategoryList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.Title.setText(serviceCategoryList.get(position).getSubname());

        final ServiceCategory category = serviceCategoryList.get(position);

        Picasso.with(context).load("http://goevent.co.in/local/images/subservicephoto/" + category.getSubimage()).placeholder(R.drawable.noimage).into(holder.imageView);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(context, ServiceActivty.class);
                String id = category.getSubid();
                intent.putExtra("serviceid", id);
                context.startActivity(intent);
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServiceActivty.class);
                String id = category.getSubid();
                intent.putExtra("serviceid", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Title;
        public ImageView imageView;
        public CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.thumbnail);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}
