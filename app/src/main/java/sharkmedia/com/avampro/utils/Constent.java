package sharkmedia.com.avampro.utils;

import java.io.Serializable;

/**
 * Created by Anuj on 05-Dec-17.
 */

public class Constent implements Serializable {

    private static final String ROOT_URL = "http://goevent.co.in/app/GoEventApi/v1/api.php?apicall=";
    public static final String VENDOR_GALLERY_IMAGE = "http://goevent.co.in/app/api.php";
    public static final String SERVICES = "http://goevent.co.in/app/home.php";
    public static final String REGISTER_USER = "http://goevent.co.in/app/androidApi/register.php";
    public static final String LOGIN_USER = "http://goevent.co.in/app/androidApi/login.php";


    public static final String URL_WORKER_URL = ROOT_URL + "worker&id=";
    public static final String URL_WORKER_SERVICE = ROOT_URL + "workerservice&id=";
    public static final String URL_WORKER_RATING = ROOT_URL + "rating&id=";
    public static final String URL_WORKER_GALLERY = ROOT_URL + "workerGalerry&id=";
    public static final String URL_WORKER_PROFILE_INFO = ROOT_URL + "workerDesc&id=";
    // authentication URL's
    private static final String AUTH_URL = "http://goevent.co.in/app/GoEventApi/include/Auth.php?apicall=";
    public static final String URL_REGISTER = AUTH_URL + "signup";
    public static final String URL_LOGIN = AUTH_URL + "login";


}
