package com.daloski.mystore.vmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.daloski.mystore.repos.DetailRepo;

public class DetailVModel extends ViewModel {

    public MutableLiveData<String> getDetails(String url){
        DetailRepo repo = new DetailRepo();
        return repo.getDetails(url);
    }
}
