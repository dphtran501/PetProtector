package edu.orangecoastcollege.cs273.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity allows the user to add to-be-adopted pets to the database. The user needs to provide
 * a name, details about the pet, and a number to call in order to add the pet to the database.
 * Optionally, the user can upload a picture of the pet from their device's camera or external
 * storage.
 * <p>
 * This activity also displays a list of to-be-adopted pets in the database. Clicking on a list
 * item will launch <code>PetDetailsActivity</code>, which will display information about the
 * pet associated with the clicked item.
 * </p>
 *
 * @author Derek Tran
 * @version 1.1
 * @since November 5, 2017
 */
public class PetListActivity extends AppCompatActivity
{
    // Constants for permissions:
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final int DENIED = PackageManager.PERMISSION_DENIED;

    // Database
    private DBHelper db;
    // Widgets in the activity
    private ImageView petImageView;
    private EditText petNameEditText;
    private EditText petDetailsEditText;
    private EditText petPhoneEditText;
    // ListView
    private List<Pet> petList;
    private PetListAdapter petListAdapter;
    private ListView petListView;

    private Uri imageURI;

    /**
     * Initializes <code>PetListActivity</code> by inflating its UI.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        // Connect to database
        db = new DBHelper(this);

        // Connect to widgets in layout
        petImageView = (ImageView) findViewById(R.id.petImageView);
        petNameEditText = (EditText) findViewById(R.id.nameEditText);
        petDetailsEditText = (EditText) findViewById(R.id.detailsEditText);
        petPhoneEditText = (EditText) findViewById(R.id.phoneEditText);
        petListView = (ListView) findViewById(R.id.petListView);

        // Connect to and setup ListView
        petList = db.getAllPets();
        petListAdapter = new PetListAdapter(this, R.layout.pet_list_item, petList);
        petListView.setAdapter(petListAdapter);

        // Show default image for pet
        imageURI = getURIFromResource(this, R.drawable.none);
        petImageView.setImageURI(imageURI);
    }

    /**
     * Adds a <code>Pet</code> object to the database and the ListView.
     *
     * @param v The view that called this method.
     */
    public void addPet(View v)
    {
        String name = petNameEditText.getText().toString();
        String details = petDetailsEditText.getText().toString();
        String phone = petPhoneEditText.getText().toString();

        // Add pet to database only if EditTexts aren't empty
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(details) || TextUtils.isEmpty(phone))
            Toast.makeText(this, "All information must be provided.", Toast.LENGTH_SHORT).show();
        else
        {
            int newPetID = db.addPet(new Pet(name, details, phone, imageURI));
            // Update ListView
            petList.add(new Pet(newPetID, name, details, phone, imageURI));
            petListAdapter.notifyDataSetChanged();

            // Reset EditTexts and ImageView for next pet to add
            petNameEditText.setText("");
            petDetailsEditText.setText("");
            petPhoneEditText.setText("");
            imageURI = getURIFromResource(this, R.drawable.none);
            petImageView.setImageURI(imageURI);
        }
    }

    /**
     * Launches <code>PetDetailsActivity</code> showing information about the <code>Pet</code>
     * object that was clicked in the ListView.
     *
     * @param v The view that called this method.
     */
    public void viewPetDetails(View v)
    {
        // Retrieve pet object in tag of selected item
        LinearLayout selectedItem = (LinearLayout) v;
        Pet selectedPet = (Pet) selectedItem.getTag();

        Intent detailsIntent = new Intent(this, PetDetailsActivity.class);
        detailsIntent.putExtra("Name", selectedPet.getName());
        detailsIntent.putExtra("Details", selectedPet.getDetails());
        detailsIntent.putExtra("Phone", selectedPet.getPhone());
        detailsIntent.putExtra("ImageURI", selectedPet.getImageURI());

        startActivity(detailsIntent);
    }

    /**
     * Sets the image of pet.
     *
     * @param v The view that called this method.
     */
    public void selectPetImage(View v)
    {
        List<String> permsList = new ArrayList<>();

        // Check each permission individually
        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (hasCameraPerm == DENIED) permsList.add(Manifest.permission.CAMERA);
        int hasReadStoragePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadStoragePerm == DENIED) permsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasWriteStoragePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePerm == DENIED)
            permsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // Some permissions have not been granted
        if (permsList.size() > 0)
        {
            // Convert the permsList to an array
            String[] permsArray = new String[permsList.size()];
            permsList.toArray(permsArray);

            // Ask user for them
            ActivityCompat.requestPermissions(this, permsArray, 1337);
        }

        // Let's make sure we have all the permissions, then start up the Image Gallery
        if (hasCameraPerm == GRANTED && hasReadStoragePerm == GRANTED && hasWriteStoragePerm == GRANTED)
        {
            // Let's open up the image gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            // Start activity for a result (picture)
            startActivityForResult(galleryIntent, 1);
        }

    }

    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it. The resultCode will be
     * <code>RESULT_CANCELED</code> if the activity explicitly returned that, didn't return any
     * result, or crashed during its operation.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its
     *                    setResult().
     * @param data        An Intent, which can return result data to the caller (various data can be
     *                    attached to Intent "extras").
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            // data = data from GalleryIntent (the URI of some image)
            imageURI = data.getData();
            petImageView.setImageURI(imageURI);
        }
    }

    /**
     * Get URI to any resource type within an Android Studio project. Method is public static to
     * allow other classes to use it as a helper function.
     *
     * @param context The current context
     * @param resID   The resource identifier of the drawable
     * @return Uri to resource by given ID
     * @throws Resources.NotFoundException If the given ID does not exist.
     */
    public static Uri getURIFromResource(Context context, int resID) throws Resources.NotFoundException
    {
        Resources res = context.getResources();
        // Build a String in the form:
        // android.resource://edu.orangecoastcollege.cs273.petprotector/drawable/none
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + res.getResourcePackageName(resID) + "/" + res.getResourceTypeName(resID) + "/" + res.getResourceEntryName(resID);
        // Parse to Uri object
        return Uri.parse(uri);
    }

}
