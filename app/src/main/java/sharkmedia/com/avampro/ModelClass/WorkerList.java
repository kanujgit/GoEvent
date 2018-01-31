package sharkmedia.com.avampro.ModelClass;

import android.widget.Button;
import android.widget.RatingBar;

/**
 * Created by Anuj on 01-Dec-17.
 */

public class WorkerList
{
    private String id;
    private String shop_name;
    private String city;
    private  String country;
    private String shop_phone_no;
    private String cover_photo;
    private String profile_photo;
    private String price;
    private String rating;
    private String time;


    public WorkerList(String id,String shop_name, String city, String country, String shop_phone_no, String cover_photo, String profile_photo, String price, String rating, String time)
    {
        this.id=id;
        this.shop_name = shop_name;
        this.city = city;
        this.country = country;
        this.shop_phone_no = shop_phone_no;
        this.cover_photo = cover_photo;
        this.profile_photo = profile_photo;
        this.price = price;
        this.rating = rating;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getShop_phone_no() {
        return shop_phone_no;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public String getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public String getTime() {
        return time;
    }
}
