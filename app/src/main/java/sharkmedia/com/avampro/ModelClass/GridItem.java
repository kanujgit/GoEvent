package sharkmedia.com.avampro.ModelClass;

/**
 * Created by Anuj on 05-Dec-17.
 */

public class GridItem
{
        private String user_id;
        private  String image;

    public GridItem(String user_id, String image)
    {
        this.user_id = user_id;
        this.image = image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
