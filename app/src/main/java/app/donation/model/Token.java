package app.donation.model;

/**
 * Created by gary on 28/12/17.
 */

public class Token
{
    public boolean success;
    public String token;
    public User user;

    public Token(boolean success, String token)
    {
        this.success = success;
        this.token = token;
    }
}
