package sharkmedia.com.avampro.ModelClass;

/**
 * Created by Anuj on 22-Nov-17.
 */

public class ServiceCategory
{

    private  String subid;
    private  String subname;
    private  String service;
    private  String subimage;

    public ServiceCategory(String subid, String subname, String service, String subimage)
    {
        this.subid = subid;
        this.subname = subname;
        this.service = service;
        this.subimage = subimage;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSubimage() {
        return subimage;
    }

    public void setSubimage(String subimage) {
        this.subimage = subimage;
    }
}
