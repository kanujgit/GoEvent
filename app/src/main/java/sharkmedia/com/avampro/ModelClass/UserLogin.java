package sharkmedia.com.avampro.ModelClass;

/**
 * Created by Anuj on 12-Jan-18.
 */

public class UserLogin
{

    private int id;
    private String username, email,phone,gender,photo,user_type;


    public UserLogin(int id, String username, String email, String phone, String gender, String photo, String user_type) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.photo = photo;
        this.user_type = user_type;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
