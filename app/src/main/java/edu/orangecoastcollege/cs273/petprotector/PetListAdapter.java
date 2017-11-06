package edu.orangecoastcollege.cs273.petprotector;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class to provide custom adapter for the <code>Pet</code> list.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 5, 2017
 */
public class PetListAdapter extends ArrayAdapter<Pet>
{
    private Context mContext;
    private int mResourceId;
    private List<Pet> mPetsList = new ArrayList<>();

    /**
     * Instantiates a new <code>PetListAdapter</code> object given a context, resource ID, and a
     * list of <code>Pet</code> objects.
     *
     * @param context    The context for which the adapter is being used (typically an activity).
     * @param resourceId The resource ID (typically the layout file name).
     * @param petsList   The list of <code>Pet</code> objects to display.
     */
    public PetListAdapter(Context context, int resourceId, List<Pet> petsList)
    {
        super(context, resourceId, petsList);
        mContext = context;
        mResourceId = resourceId;
        mPetsList = petsList;
    }

    /**
     * Gets the view associated with the layout.
     *
     * @param position    The position of the <code>Pet</code> object selected in the list.
     * @param convertView The converted view.
     * @param parent      The parent - ArrayAdapter.
     * @return The new view with all content set.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        // Inflates the list item layout (pet_list_item.xml) for each item in the list
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        // Retrieve widgets of the list item (pet_list_item.xml)
        LinearLayout listItemLinearLayout = (LinearLayout) view.findViewById(R.id.petListLinearLayout);
        ImageView listItemImageView = (ImageView) view.findViewById(R.id.petListImageView);
        TextView listItemNameTextView = (TextView) view.findViewById(R.id.petListNameTextView);
        TextView listItemDetailsTextView = (TextView) view.findViewById(R.id.petListDetailsTextView);

        // Set widgets of the list item based on selected item
        Pet selectedPet = mPetsList.get(position);
        listItemImageView.setImageURI(selectedPet.getImageURI());
        listItemNameTextView.setText(selectedPet.getName());
        listItemDetailsTextView.setText(selectedPet.getDetails());

        // Set tag of list item layout to selected pet object
        listItemLinearLayout.setTag(selectedPet);

        return view;
    }
}
