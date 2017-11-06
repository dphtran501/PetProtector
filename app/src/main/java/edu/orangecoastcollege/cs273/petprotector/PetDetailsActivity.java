package edu.orangecoastcollege.cs273.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This activity displays information about a to-be-adopted pet. The information is specified by
 * which list item was clicked in <code>PetListActivity</code>.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 5, 2017
 */
public class PetDetailsActivity extends AppCompatActivity
{

    /**
     * Initializes <code>PetDetailsActivity</code> by inflating its UI.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        // Connect to widgets
        ImageView petDetailsImageView = (ImageView) findViewById(R.id.petDetailsImageView);
        TextView petDetailsNameTextView = (TextView) findViewById(R.id.petDetailsNameTextView);
        TextView petDetailsDetailsTextView = (TextView) findViewById(R.id.petDetailsDetailsTextView);
        TextView petDetailsPhoneTextView = (TextView) findViewById(R.id.petDetailsPhoneTextView);

        // Retrieve intent data from PetListActivity
        Intent detailsIntent = getIntent();
        Uri imageURI = Uri.parse(detailsIntent.getStringExtra("ImageURI"));
        String name = detailsIntent.getStringExtra("Name");
        String details = detailsIntent.getStringExtra("Details");
        String phone = detailsIntent.getStringExtra("Phone");

        // Set up widgets
        petDetailsImageView.setImageURI(imageURI);
        petDetailsNameTextView.setText(name);
        petDetailsDetailsTextView.setText(details);
        petDetailsPhoneTextView.setText(phone);

    }
}
