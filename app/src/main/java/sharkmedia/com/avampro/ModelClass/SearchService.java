package sharkmedia.com.avampro.ModelClass;

/**
 * Created by Anuj on 26-Nov-17.
 */

public class SearchService
{
    private  String subid;
    private  String subname;


    public SearchService(String subid, String subname) {
        this.subid = subid;
        this.subname = subname;

    }

    public String getSubid() {
        return subid;
    }

    public void setSubid(String subid) {
        this.subid = subid;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }


}
