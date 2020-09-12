package com.daloski.mystore.vmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.daloski.mystore.models.Item;
import com.daloski.mystore.repos.MainRepo;

import java.util.List;

public class MainVModel extends ViewModel {

    public MutableLiveData<List<Item>> getItems(String name){
        MainRepo repo = new MainRepo();
        return repo.getItems(name);
    }
}
