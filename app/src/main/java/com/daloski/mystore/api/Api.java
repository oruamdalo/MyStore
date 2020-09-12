package com.daloski.mystore.api;

import android.util.Log;

import com.daloski.mystore.models.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Api {

    private static final String BASE_URL = "https://play.google.com/store/";
    private static final String TAG = "Api";
    // For searching: https://play.google.com/store/search?q={name}&c=apps

    private static Api INSTANCE = null;

    public static Api getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Api();
        }
        return INSTANCE;
    }


    public void searchByName(String name, Callback callback) {
        String query = BASE_URL + "search?q=" + name + "&c=apps";
        Log.d(TAG, "searchByName: query: " + query);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(query).get().build();
        // Regex: AF_initDataCallback[\s\S]*?<\/script
        client.newCall(request).enqueue(callback);
    }

    public void getDetails(String url, Callback callback){
        Log.d(TAG, "searchByName: query: " + url);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url(url).get().build();
        client.newCall(request).enqueue(callback);
    }

    public static String parse(String html) {
        Pattern pattern = Pattern.compile("AF_initDataCallback[\\s\\S]*?<\\/script");
        Matcher matcher = pattern.matcher(html);

        String output = null;
        int counter = 0;
        while(matcher.find()){
//            Log.d(TAG, "parse: counter: " + counter);
            if(counter++ == 5){
//                Log.d(TAG, "parse: group: " + matcher.group(0));
                output = matcher.group(0);
            }
        }
        return output;
    }

    public static List<Item> fetchJson(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        //AF_initDataChunkQueue[6]["data"][0][1][0][0][0]
        String query = BASE_URL + "apps/details?id=";
        List<Item> items = new ArrayList<>();

        JSONArray dataArray = obj.getJSONArray("data");
        JSONArray itemsArray = dataArray.getJSONArray(0).getJSONArray(1).getJSONArray(0).getJSONArray(0).getJSONArray(0);
        Log.d(TAG, "fetchJson: itemsSize: " + itemsArray.length());

        for(int i=0; i<itemsArray.length(); i++){
            JSONArray item = itemsArray.getJSONArray(i);

            String name  = item.getString(2);
            String image = item.getJSONArray(1).getJSONArray(1).getJSONArray(0).getJSONArray(3).getString(2);
            String pack  = item.getJSONArray(12).getString(0);

            items.add(new Item(name, null, query.concat(pack), image));
        }

        return items;

    }


    public static String parseDetail(String html){
        String output = null;

        Document doc = Jsoup.parse(html);
        Element description = doc.selectFirst("meta[name=\"description\"]");

        if(description != null){
            output = description.attr("content");
        }

        return output;
    }

}
