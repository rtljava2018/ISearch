package com.rkm.itunessearch.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rkm.itunessearch.model.ItuneResponse;
import com.rkm.itunessearch.model.Result;
import com.rkm.itunessearch.repository.SearchRepositry;
import com.rkm.itunessearch.utils.AppConstant;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<ItuneResponse> mutableLiveData;
    private SearchRepositry searchRepositry;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        searchRepositry=SearchRepositry.getInstance();
        mutableLiveData=new MediatorLiveData<>();
        mutableLiveData=searchRepositry.getSearch("all","");
    }

    public LiveData<ItuneResponse> getSearchResult(String param1,String param2){
        return mutableLiveData;
    }

    public List<Result> getCartList(){
        return AppConstant.listOfcart;
    }


}
