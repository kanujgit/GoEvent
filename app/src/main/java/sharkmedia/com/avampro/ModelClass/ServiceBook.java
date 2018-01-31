package sharkmedia.com.avampro.ModelClass;

/**
 * Created by Anuj on 23-Dec-17.
 */

public class ServiceBook
{

    private String subservice_id;
    private String price;
    private String time;
    private String user_id;
    private String shop_id;
    private String subname;
    private String subimage;


    public ServiceBook(String subservice_id, String price, String time, String user_id, String shop_id, String subname, String subimage)
    {
        this.subservice_id = subservice_id;
        this.price = price;
        this.time = time;
        this.user_id = user_id;
        this.shop_id = shop_id;
        this.subname = subname;
        this.subimage = subimage;
    }

    public String getSubservice_id() {
        return subservice_id;
    }

    public String getPrice() {
        return price;
    }

    public String getTime() {
        return time;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public String getSubname() {
        return subname;
    }

    public String getSubimage() {
        return subimage;
    }
}
