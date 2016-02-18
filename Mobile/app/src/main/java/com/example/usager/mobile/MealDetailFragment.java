package com.example.usager.mobile;

import android.app.Activity;
import android.graphics.Paint;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.usager.mobile.dummy.MealsContent;

/**
 * A fragment representing a single Meal detail screen.
 * This fragment is either contained in a {@link MealListActivity}
 * in two-pane mode (on tablets) or a {@link MealDetailActivity}
 * on handsets.
 */
public class MealDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private MealsContent.Meal mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MealDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = MealsContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }
    }

    @Override //Remplir les champs avec l'item sélectionné.
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_meal_detail, container, false);

        if (mItem != null) {
            //Afficher le nom du repas.
            ((TextView) rootView.findViewById(R.id.txtName)).setText(getString(R.string.name_info, mItem.name));

            //Afficher le prix.
            TextView price = (TextView)rootView.findViewById(R.id.txtPrice);
            price.setText(getString(R.string.price_info, String.format("%.2f", mItem.price)));

            //Si rabais, calculer et afficher.
            TextView discount = (TextView)rootView.findViewById(R.id.txtDiscount);
            if(mItem.discount > 0.0f){
                price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                discount.setText(getString(R.string.discount_info, String.format("%.1f", mItem.discount * 100.0f), String.format("%.2f", mItem.price * (1 - mItem.discount))));
            }
            //Sinon cacher la textView de rabais.
            else{
                discount.setVisibility(View.GONE);
            }

            //Afficher la description.
            ((TextView) rootView.findViewById(R.id.txtDesc)).setText(getString(R.string.description_info, mItem.desc));
        }

        return rootView;
    }
}
