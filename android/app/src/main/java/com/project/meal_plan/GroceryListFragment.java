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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardViewNative;

public class GroceryListFragment extends Fragment {
    private static String TAG = GroceryListFragment.class.toString();

    private static String sessionStr;
    private static String email;

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

        Intent intent = getActivity().getIntent();
        String[] params = intent.getStringArrayExtra(MainActivity.USER_PARAMS);
        if (params != null) {
            sessionStr = params[1];
            email = params[0];
        } else {
            getActivity().finish();
        }

        View view = inflater.inflate(R.layout.fragment_grocery_list, container, false);

        mScrollView = (ScrollView) view.findViewById(R.id.scrollView);

        mGroceryCardView = new GroceryCardView(getActivity(), mScrollView);

        try {
            readReceipes();

//            groceryCard.setGroceryItems();
//            groceryCard.init();
        } catch (UnsupportedEncodingException e) {

        } catch (JSONException e) {

        }


        CardViewNative cardView = (CardViewNative) view.findViewById(R.id.groceryCard);
        cardView.setCard(mGroceryCardView);

        return view;
    }

    private void readReceipes() throws  UnsupportedEncodingException, JSONException{

        final JSONObject user = new JSONObject();
        JSONObject json = new JSONObject();
        json.put("email", email);
        json.put("sessionStr", sessionStr);

        user.put("user", json);

        JSONObject json1 = new JSONObject();
        json1.put("start", new Date().getTime()*1000);
        json1.put("end", new Date().getTime() + 7*24*60*60*1000);

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
            json1.put("id", l.longValue());
            json1.put("multiplier", 1);
            arr.put(json1);
        }

        user.put("receipes", arr);

        ApiClient.post(getActivity(), "/recipeIngredients", new StringEntity(user.toString()), new JsonHttpResponseHandler() {
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

                    Log.d(TAG, response.toString());
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
