package edu.orangecoastcollege.cs273.petprotector;

import android.net.Uri;

/**
 * A class to represent a pet to adopt, with information about the pet's name and details, a phone
 * number to call in regards to adopting the pet, and an image of the pet.
 *
 * @author Derek Tran
 * @verison 1.0
 * @since November 5, 2017
 */

public class Pet
{
    private int mID;
    private String mName;
    private String mDetails;
    private String mPhone;
    private Uri mImageURI;

    /**
     * Instantiates a <code>Pet</code> object using a given ID, name, details, phone number, and
     * image URI.
     *
     * @param ID       The ID of the <code>Pet</code> in the database.
     * @param name     The name of the <code>Pet</code>.
     * @param details  Details about the <code>Pet</code>.
     * @param phone    The phone number to call in regards to the <code>Pet</code>.
     * @param imageURI The URI of the image of the <code>Pet</code>.
     */
    public Pet(int ID, String name, String details, String phone, Uri imageURI)
    {
        mID = ID;
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageURI = imageURI;
    }

    /**
     * Instantiates a <code>Pet</code> object using a given name, details, phone number, and image
     * URI.
     *
     * @param name     The name of the <code>Pet</code>.
     * @param details  Details about the <code>Pet</code>.
     * @param phone    The phone number to call in regards to the <code>Pet</code>.
     * @param imageURI The URI of the image of the <code>Pet</code>.
     */
    public Pet(String name, String details, String phone, Uri imageURI)
    {
        this(-1, name, details, phone, imageURI);
    }

    /**
     * Gets the database ID of the <code>Pet</code> object.
     *
     * @return The database ID of the <code>Pet</code> object.
     */
    public int getID()
    {
        return mID;
    }

    /**
     * Gets the name of the <code>Pet</code> object.
     *
     * @return The name of the <code>Pet</code> object.
     */
    public String getName()
    {
        return mName;
    }

    /**
     * Sets the name of the <code>Pet</code> object.
     *
     * @param name The name of the <code>Pet</code> object.
     */
    public void setName(String name)
    {
        mName = name;
    }

    /**
     * Gets the details about the <code>Pet</code> object.
     *
     * @return The details about the <code>Pet</code> object.
     */
    public String getDetails()
    {
        return mDetails;
    }

    /**
     * Sets the details about the <code>Pet</code> object.
     *
     * @param details The details about the <code>Pet</code> object.
     */
    public void setDetails(String details)
    {
        mDetails = details;
    }

    /**
     * Gets the phone number in regards to the <code>Pet</code> object.
     *
     * @return The phone number in regards to the <code>Pet</code> object.
     */
    public String getPhone()
    {
        return mPhone;
    }

    /**
     * Sets the phone number in regards to the <code>Pet</code> object.
     *
     * @param phone The phone number in regards to the <code>Pet</code> object.
     */
    public void setPhone(String phone)
    {
        mPhone = phone;
    }

    /**
     * Gets the URI of the image of the <code>Pet</code> object.
     *
     * @return The URI of the image of the <code>Pet</code> object.
     */
    public Uri getImageURI()
    {
        return mImageURI;
    }

    /**
     * Sets the URI of the image of the <code>Pet</code> object.
     *
     * @param imageURI The URI of the image of the <code>Pet</code> object.
     */
    public void setImageURI(Uri imageURI)
    {
        mImageURI = imageURI;
    }

    /**
     * Creates a String representation of the <code>Pet</code> object.
     *
     * @return A String representation of the <code>Pet</code> object.
     */
    @Override
    public String toString()
    {
        return "Pet{" + "ID=" + mID + ", Name='" + mName + '\'' + ", Details='" + mDetails + '\'' + ", Phone='" + mPhone + '\'' + ", ImageURI=" + mImageURI + '}';
    }
}
