package sharkmedia.com.avampro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sharkmedia.com.avampro.ModelClass.SearchService;
import sharkmedia.com.avampro.R;

/**
 * Created by Anuj on 26-Nov-17.
 */

public class SearchAdapter extends BaseAdapter
{
    private Context mcontext;
    private  List<SearchService> servicesList=null;
    ArrayList<SearchService> serviceArrayList;
    LayoutInflater inflater;

    public SearchAdapter(Context context, List<SearchService> servicesList)
    {
        mcontext = context;
        this.servicesList   = servicesList;
        inflater=LayoutInflater.from(mcontext);
        this.serviceArrayList = new ArrayList<>();

        this.serviceArrayList.addAll(servicesList);
    }


    public  class ViewHolder
    {
        TextView name;
    }

    @Override
    public int getCount() {
        return servicesList.size();
    }

    @Override
    public SearchService getItem(int position) {
        return servicesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent)
    {
        final ViewHolder holder;
        if(view==null)
        {
            holder=new ViewHolder();
            view = inflater.inflate(R.layout.search_view_layout,null);

            //locate textView in list view item
            holder.name=(TextView)view.findViewById(R.id.name);
            view.setTag(holder);
        }else{
            holder=(ViewHolder)view.getTag();
        }
        //set resource in text view
        holder.name.setText(servicesList.get(position).getSubname());
        return view;
    }


    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        servicesList.clear();
        if (charText.length() == 0)
        {
            servicesList.addAll(serviceArrayList);
        } else {
            for (SearchService wp : serviceArrayList)
            {
                if (wp.getSubname().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    servicesList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }


}
