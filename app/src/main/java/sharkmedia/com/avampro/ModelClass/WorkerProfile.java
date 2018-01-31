package sharkmedia.com.avampro.ModelClass;

/**
 * Created by Anuj on 27-Dec-17.
 */

public class WorkerProfile
{
    private String shop_name,description,state,shop_date;

    public WorkerProfile(String shop_name, String description, String state, String shop_date) {
        this.shop_name = shop_name;
        this.description = description;
        this.state = state;
        this.shop_date = shop_date;
    }

    public String getShop_name() {
        return shop_name;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public String getShop_date() {
        return shop_date;
    }
}
