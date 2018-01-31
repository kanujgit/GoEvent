package sharkmedia.com.avampro.ModelClass;

/**
 * Created by Anuj on 24-Dec-17.
 */

public class WorkerRating
{
    String email;
    String comment;
    String rating;
    String rid;

    public WorkerRating(String email, String comment, String rating, String rid)
    {
        this.email = email;
        this.comment = comment;
        this.rating = rating;
        this.rid = rid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
