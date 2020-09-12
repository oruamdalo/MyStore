package com.daloski.mystore.repos;

import androidx.lifecycle.MutableLiveData;

import com.daloski.mystore.api.Api;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailRepo {

    public MutableLiveData<String> getDetails(String url){
        MutableLiveData<String> data = new MutableLiveData<>();

        Api.getInstance().getDetails(url, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                data.postValue(null);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.body() != null){
                    String html = response.body().string();
                    String description = Api.parseDetail(html);
                    data.postValue(description);
                }
            }
        });

        return data;
    }
}
