package com.daloski.mystore.repos;

import androidx.lifecycle.MutableLiveData;

import com.daloski.mystore.api.Api;
import com.daloski.mystore.models.Item;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainRepo {

    public MutableLiveData<List<Item>> getItems(String name){
        MutableLiveData<List<Item>> data = new MutableLiveData<>();

        Api.getInstance().searchByName(name, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                data.postValue(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.body() != null){
                    String html = response.body().string();

                    String json = Api.parse(html);
                    if(json != null) {
                        json = json.replaceAll("AF_initDataCallback", "").replaceAll(";</script", "").substring(1);

                        try {
                            List<Item> items = Api.fetchJson(json);
                            data.postValue(items);
                        } catch (JSONException e) {
                            //e.printStackTrace();
                            data.postValue(null);
                        }
                    }else{
                        data.postValue(null);
                    }
                }
            }
        });

        return data;
    }
}
