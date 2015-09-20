package com.project.meal_plan;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.CardWithList.ListObject;
import it.gmariotti.cardslib.library.prototypes.CardWithList.OnItemSwipeListener;
import it.gmariotti.cardslib.library.prototypes.CardWithList.OnItemClickListener;

public class GroceryItem implements ListObject {
    public static String TAG = GroceryItem.class.toString();

    public boolean isFooter;

    private String text;

    private boolean isSwipeable;

    private Card mParentCard;
    private OnItemClickListener mClickListener;
    private OnItemSwipeListener mSwipeListener;

    public GroceryItem(Card parentCard, String text) {
        mParentCard = parentCard;
        this.text = text;
    }

    @Override
    public String getObjectId() {
        return text;
    }

    @Override
    public Card getParentCard() {
        return mParentCard;
    }

    @Override
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mClickListener = onItemClickListener;
    }

    @Override
    public CardWithList.OnItemClickListener getOnItemClickListener() {
        return mClickListener;
    }

    @Override
    public boolean isSwipeable() {
        return isSwipeable;
    }

    @Override
    public void setSwipeable(boolean b) {
        isSwipeable = b;
    }

    @Override
    public CardWithList.OnItemSwipeListener getOnItemSwipeListener() {
        return mSwipeListener;
    }

    @Override
    public void setOnItemSwipeListener(OnItemSwipeListener onItemSwipeListener) {
        mSwipeListener = onItemSwipeListener;
    }
}
