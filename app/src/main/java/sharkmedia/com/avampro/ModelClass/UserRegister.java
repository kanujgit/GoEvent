package sharkmedia.com.avampro.ModelClass;

/**
 * Created by Anuj on 15-Jan-18.
 */

public class UserRegister
{
    int id;
    private String name,email,password,gender,phone,user_type;

    public UserRegister(int id,String name, String email, String gender,String phone, String user_type) {
        this.phone=phone;
        this.id=id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.user_type = user_type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}
