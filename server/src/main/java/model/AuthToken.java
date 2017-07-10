package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Objects;
import java.util.logging.*;

/**
 *  <h1>Authorization Model</h1>
 *  Mirrors auth database table rows for passing data between the facade and data access objects.
 *
 *  @author     Nathan Finch
 *  @since      7/10/2017
 */
public class AuthToken
{

    //Data Members

    //TODO: Implement logging with the logger used on the server
    //private static Logger logger;
    //static{ logger = Logger.getLogger(""); }

    private static final long HOUR = 60 * 60 * 1000;

    private String mAuthToken;
    private String mUsername;
    private Timestamp mExpires;

    //Constructors

    public AuthToken(String username)
    {

        setUsername(username);

        //Two calls to get date and time, plus one hour
        setExpires(new Timestamp(Calendar.getInstance().getTime().getTime() + HOUR));

        //Used to create unique hash
        String attributes = username + getExpires().toString();

        String token = createToken(attributes);

        setAuthToken(token);

    }

    //Utility Methods

    /**
     *  Create Token
     *  Hashes the username, personID and timestamp to create an auth token
     *
     *  @param          attributes          The username, personID and timestamp combined
     *
     *  @return                             The unique token/hash
     */
    private String createToken(String attributes)
    {

        StringBuffer tokenBuffer = new StringBuffer();

        try
        {
            //Generates a hash based on the attributes
            MessageDigest hashDigest = MessageDigest.getInstance("MD5");
            hashDigest.update(attributes.getBytes());
            byte[] dgst = hashDigest.digest();
            for (byte cur_byte : dgst)
            {
                tokenBuffer.append(String.format("%02x", cur_byte & 0xff));
            }

        }
        catch (NoSuchAlgorithmException ex)
        {
            //logger.log(Level.SEVERE, "New Authorization Failed");
            //logger.log(Level.SEVERE, ex.getMessage(), ex);
        }

        return tokenBuffer.toString();

    }

    //Access Methods

    public String getUsername()
    {

        return mUsername;

    }

    public String getAuthToken()
    {

        return mAuthToken;

    }

    public Timestamp getExpires()
    {

        return mExpires;

    }

    //Mutator Methods

    private void setUsername(String username)
    {

        mUsername = username;

    }

    private void setAuthToken(String authToken)
    {

        mAuthToken = authToken;

    }

    private void setExpires(Timestamp expires)
    {

        mExpires = expires;

    }

    //Equality

    @Override
    public boolean equals(Object o)
    {

        if (this == o)
        {

            return true;

        }
        if (o == null || getClass() != o.getClass())
        {

            return false;

        }

        AuthToken current_auth = (AuthToken) o;

        return Objects.equals(this.getUsername(), current_auth.getUsername()) &&
                Objects.equals(this.getAuthToken(), current_auth.getAuthToken()) &&
                Objects.equals(this.getExpires(), current_auth.getExpires());

    }

    @Override
    public int hashCode()
    {

        return Objects.hash(getUsername(), getAuthToken(), getExpires());

    }

}
