package com.project.meal_plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class GroceryListFragment extends Fragment {
    private static String TAG = GroceryListFragment.class.toString();

    private static String sessionStr;
    private static String email;

    private ScrollView mScrollView;
    private GroceryCardView mGroceryCardView;
    private CardViewNative mCardView;

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

//        Intent intent = getActivity().getIntent();
//        String[] params = intent.getStringArrayExtra(MainActivity.USER_PARAMS);
//        if (params != null) {
//            sessionStr = params[1];
//            email = params[0];
//        } else {
//            getActivity().finish();
//        }
        email = "test@gmail.com";
        sessionStr = "aa69c23f2a158b9ea5caea4eda4e1f7920642ee0a1faeba88e168b27c2ec8e164f3e1dfa4b744ec17a18a5feec63a32987d3f4f191b72a77a308332582adbad3166c476c893e127f28afc5043423e3c056d4b0483ec50925d3c0c2c1d8613b074bec5cf33f89691706608abe4b3a033d57b9d29efce2eeffbfe7ca1d16bffb3";

        View view = inflater.inflate(R.layout.fragment_grocery_list, container, false);

        mScrollView = (ScrollView) view.findViewById(R.id.scrollView);

        mGroceryCardView = new GroceryCardView(getActivity(), mScrollView);

        mCardView = (CardViewNative) view.findViewById(R.id.groceryCard);

        try {
            readReceipes();

//            groceryCard.setGroceryItems();
//            groceryCard.init();
        } catch (UnsupportedEncodingException e) {

        } catch (JSONException e) {

        }

        mCardView.setVisibility(View.INVISIBLE);

        return view;
    }

    private void readReceipes() throws  UnsupportedEncodingException, JSONException{

        final JSONObject user = new JSONObject();
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("sessionStr", sessionStr);

        user.put("user", json);

        JSONObject json1 = new JSONObject();
        json1.put("start", System.currentTimeMillis());
        json1.put("end", System.currentTimeMillis() + 17*24*60*60*1000);

        user.put("calendarRecipes", json1);

        ApiClient.post(getActivity().getApplicationContext(), "/readWeeklyRecipes", new StringEntity(user.toString()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                List<Long> recipeIds = new ArrayList<Long>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        Long l = obj.getLong("id");
                        if (l != null) {
                            recipeIds.add(l);
                        }
                    }

                    populateUserGroceryList(recipeIds);
                } catch (JSONException e) {
                    Log.e(TAG, ">.> Alex");
                } catch (UnsupportedEncodingException e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Toast.makeText(getActivity(), "Read Fail", Toast.LENGTH_SHORT).show();
                Log.d(TAG, response.toString());
                Log.d(TAG, user.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "Read Fail", Toast.LENGTH_SHORT).show();
                Log.d(TAG, responseString);
                Log.d(TAG, user.toString());
            }

        });
    }

    private void populateUserGroceryList(List<Long> receipeIds) throws UnsupportedEncodingException, JSONException {
        final JSONObject user = new JSONObject();
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("sessionStr", sessionStr);

        user.put("user", json);

        JSONArray arr = new JSONArray();
        for (Long l : receipeIds) {
            JSONObject json1 = new JSONObject();
            json1.put("id", l.intValue());
            json1.put("multiplier", 1);
            arr.put(json1);
        }

        user.put("recipes", arr);

        ApiClient.post(getActivity(), "/recipeIngredients", new StringEntity(user.toString()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                List<CardWithList.ListObject> userGroceryList = new ArrayList<>();
                try {
                    Log.d(TAG, user.toString());
                    Log.d(TAG, response.toString());

                    JSONArray arr = response.names();
                    Log.d(TAG, arr.toString());

                    for (int i = 0; i < arr.length(); i++) {
                        String repName = (String)arr.get(i);
                        int quantity = response.getInt(repName);

                        if (quantity >= 0) {
                            GroceryItem item = new GroceryItem(mGroceryCardView, quantity + " " + repName);
                            userGroceryList.add(item);
                        }
                    }

                    mGroceryCardView.setGroceryItems(userGroceryList);
                    mGroceryCardView.init();
                    mCardView.setCard(mGroceryCardView);
                    mCardView.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    Log.e(TAG, ">.> Alex");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject response) {
                Toast.makeText(getActivity(), "Read Fail", Toast.LENGTH_SHORT).show();
                Log.d(TAG, response.toString());
                Log.d(TAG, user.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "Read Fail", Toast.LENGTH_SHORT).show();
                Log.d(TAG, responseString);
                Log.d(TAG, user.toString());
            }

        });
    }
}
