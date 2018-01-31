package sharkmedia.com.avampro.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import sharkmedia.com.avampro.R;

/**
 * Created by Anuj on 21-Nov-17.
 */

public class ImageAdapter extends  PagerAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Integer> images;

    public ImageAdapter(Context context, ArrayList<Integer> images)
    {
        this.context = context;
        this.images = images;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return images.size();
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View MyImagelayout =inflater.inflate(R.layout.slide_image,container,false);
        ImageView imageView =(ImageView)MyImagelayout.findViewById(R.id.image);
        imageView.setImageResource(images.get(position));
        container.addView(MyImagelayout,0);
        return MyImagelayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view.equals(object);
    }
}
