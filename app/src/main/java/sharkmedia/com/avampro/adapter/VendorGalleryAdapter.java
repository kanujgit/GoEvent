package sharkmedia.com.avampro.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import sharkmedia.com.avampro.ModelClass.GridItem;
import sharkmedia.com.avampro.R;

/**
 * Created by Anuj on 05-Dec-17.
 */

public class VendorGalleryAdapter extends ArrayAdapter<GridItem>
{
    private Context mContext;
    private int layoutResourceId;
    private List<GridItem> mGridData;


    public VendorGalleryAdapter(Context mContext, int layoutResourceId, List<GridItem> mGridData)
    {
        super(mContext, layoutResourceId, mGridData);
        this.mContext = mContext;
        this.layoutResourceId = layoutResourceId;
        this.mGridData = mGridData;


    }

    /**
     * Updates grid data and refresh grid items.
     *
     * @param mGridData
     */
    public void setGridData(ArrayList<GridItem> mGridData)
    {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        GridItem item = mGridData.get(position);
        Picasso.with(mContext).load("http://goevent.co.in/local/images/gallery/"+item.getImage()).placeholder(R.drawable.noimage).into(holder.imageView);
        return row;
    }

    static  class ViewHolder
    {
        int id;
        ImageView imageView;

    }

}