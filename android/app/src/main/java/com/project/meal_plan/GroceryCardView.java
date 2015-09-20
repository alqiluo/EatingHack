package com.project.meal_plan;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;

/**
 * Created by George on 2015-09-19.
 */
public class GroceryCardView extends CardWithList {

    public GroceryCardView(Context context) {
        super(context, R.layout.grocery_item);
    }

    @Override
    protected CardHeader initCardHeader() {
        //Add Header
        CardHeader header = new CardHeader(getContext());

        //Add a popup menu. This method set OverFlow button to visible
//        header.setPopupMenu(R.menu.popup_item, new CardHeader.OnClickCardHeaderPopupMenuListener() {
//            @Override
//            public void onMenuItemClick(BaseCard card, MenuItem item) {
//
//                switch (item.getItemId()){
//
//                }
//            }
//        });
        header.setTitle("Grocery"); //should use R.string.
        return header;
    }

    @Override
    protected void initCard() {
        setSwipeable(false);
//        setOnSwipeListener(new OnSwipeListener() {
//            @Override
//            public void onSwipe(Card card) {
//                Toast.makeText(getContext(), "Swipe on " + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected List<ListObject> initChildren() {
        List<ListObject> mObjects = new ArrayList<ListObject>();
        mObjects.add(makeGroceryItem());
        mObjects.add(makeGroceryItem());
        mObjects.add(makeGroceryItem());

        return mObjects;
    }

    private GroceryItem makeGroceryItem() {
        GroceryItem gi = new GroceryItem(this);
        gi.setSwipeable(true);
        return gi;
    }

    @Override
    public View setupChildView(int i, ListObject listObject, View view, ViewGroup viewGroup) {

        return view;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.grocery_item;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        //It is very important call the super method!!
        super.setupInnerViewElements(parent, view);

        //Your elements
    }
}
