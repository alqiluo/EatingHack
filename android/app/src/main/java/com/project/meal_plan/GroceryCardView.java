package com.project.meal_plan;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;

public class GroceryCardView extends CardWithList implements CardWithList.OnItemClickListener {

    private static Object mutex = new Object();

    private GroceryItem footerItem;
    private ScrollView mScrollView;

    private List<ListObject> groceryItems = new ArrayList<>();

    public GroceryCardView(Context context, ScrollView scrollView) {
        super(context, R.layout.grocery_item);
        mScrollView = scrollView;
    }

    @Override
    protected CardHeader initCardHeader() {
        //Add Header
        CardHeader header = new CardHeader(getContext());

        header.setPopupMenu(R.menu.menu_main, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
            }
        });
        header.setTitle("Grocery List");
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

        for (ListObject item : groceryItems) {
            item.setOnItemClickListener(this);
            item.setSwipeable(true);
            mObjects.add(item);
        }

        footerItem = new GroceryItem(this, "Add a new grocery item");
        footerItem.isFooter = true;
        footerItem.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(LinearListView linearListView, View view, int i, ListObject listObject) {
                final LinearListAdapter mListAdapter = getLinearListAdapter();

                // TODO

                new MaterialDialog.Builder(getContext())
                        .title(R.string.add_item)
                        .content("")
                        .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
                        .cancelable(true)
                        .cancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                dialog.dismiss();
                            }
                        })
                        .input("", "", new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                mListAdapter.remove(footerItem);
                                if (input.length() != 0) {
                                    mListAdapter.add(makeGroceryItem(input.toString()));
                                }
                                mListAdapter.add(footerItem);
                                mScrollView.fullScroll(View.FOCUS_DOWN);
                            }
                        }).show();


            }
        });
        mObjects.add(footerItem);

        return mObjects;
    }

    @Override
    public View setupChildView(int i, ListObject listObject, View view, ViewGroup viewGroup) {

        TextView textView = (TextView) view.findViewById(R.id.textView);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        if (listObject instanceof  GroceryItem) {
            if (((GroceryItem) listObject).isFooter) {
                textView.setHint( listObject.getObjectId() );
                checkBox.setVisibility(View.INVISIBLE);
            } else {
                textView.setText(listObject.getObjectId());
            }
        }

        return view;
    }

    public void setGroceryItems(List<ListObject> list) {
        groceryItems = list;
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

    @Override
    public void onItemClick(LinearListView linearListView, View view, int i, ListObject listObject) {

        synchronized (mutex) {
            final TextView textView = (TextView) view.findViewById(R.id.textView);

            new MaterialDialog.Builder(getContext())
                    .title(R.string.edit_item)
                    .content("")
                    .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
                    .cancelable(true)
                    .input("", textView.getText(), new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {
                            textView.setText(input);
                        }
                    }).show();
        }
    }

    private GroceryItem makeGroceryItem(String text) {
        GroceryItem gi = new GroceryItem(this, text);
        gi.setSwipeable(true);      // TODO undo swipe

        // TODO add OnClickListener
        gi.setOnItemClickListener(this);

        return gi;
    }
}
