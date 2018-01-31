package sharkmedia.com.avampro.adapter;

import android.content.Context;
import android.media.Rating;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import sharkmedia.com.avampro.ModelClass.WorkerRating;
import sharkmedia.com.avampro.R;

/**
 * Created by Anuj on 24-Dec-17.
 */

public class RatingAdapter extends RecyclerView.Adapter<RatingAdapter.MyViewHolder>
{

    Context context;
    List<WorkerRating> ratingList;

    public RatingAdapter(Context context, List<WorkerRating> ratingList)
    {
        this.context = context;
        this.ratingList = ratingList;
    }

    @Override
    public RatingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.rating_cardview,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.rating.setRating(Float.parseFloat(ratingList.get(position).getRating()));
        holder.textView_comment.setText(ratingList.get(position).getComment());
    }


    @Override
    public int getItemCount() {
        return ratingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        public RatingBar rating;
        public TextView textView_comment;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            rating=(RatingBar)itemView.findViewById(R.id.Rating_worker);
            textView_comment=(TextView)itemView.findViewById(R.id.textView_comment);
        }
    }
}
