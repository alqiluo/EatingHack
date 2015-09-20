package com.project.meal_plan;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class GroceryListFragment extends Fragment {

    private ScrollView mScrollView;

    private GroceryCardView mGroceryCardView;

    public static GroceryListFragment newInstance() {
        GroceryListFragment fragment = new GroceryListFragment();
        return fragment;
    }

    public GroceryListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grocery_list, container, false);

        GroceryCardView groceryCard = new GroceryCardView(getActivity());
        groceryCard.init();

        CardViewNative cardView = (CardViewNative) view.findViewById(R.id.groceryCard);
        cardView.setCard(groceryCard);
        //cardV

        return view;
    }
}
