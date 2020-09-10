package com.rkm.itunessearch.repository;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.rkm.itunessearch.model.ItuneResponse;
import com.rkm.itunessearch.model.Result;
import com.rkm.itunessearch.rest.APIClient;
import com.rkm.itunessearch.rest.ApiService;
import com.rkm.itunessearch.utils.DateUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
public class SearchRepositry {
    private static SearchRepositry newsRepository;
    ApiService apiService;

    public static SearchRepositry getInstance(){
        if (newsRepository == null){
            newsRepository = new SearchRepositry();
        }
        return newsRepository;
    }
    public SearchRepositry(){
        apiService = APIClient.cteateService(ApiService.class);
    }

    public MutableLiveData<ItuneResponse> getSearch(String param1, String param2){
        final MutableLiveData<ItuneResponse> newsData = new MutableLiveData<>();
        apiService.getSearchResult(param1).enqueue(new Callback<ItuneResponse>() {
            @Override
            public void onResponse(Call<ItuneResponse> call,
                                   Response<ItuneResponse> response) {
                if (response.isSuccessful()){
                    ItuneResponse ituneResponse = response.body();
                    if (ituneResponse != null && ituneResponse.getResultCount() > 0) {
                        ituneResponse = removeDuplicates(ituneResponse);
                        sortAscendingDates(ituneResponse.getResults());
                        newsData.setValue(ituneResponse);
                    }

                }
            }

            @Override
            public void onFailure(Call<ItuneResponse> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private ItuneResponse removeDuplicates(ItuneResponse response) {
        List<Result> results = response.getResults();
        if (results != null && results.size() > 0) {
            List<Result> unique = results.stream()
                    .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Result::getTrackName))),
                            ArrayList::new));
            response.setResults(unique);
            response.setResultCount(unique.size());
        }
        return response;
    }
    private List<Result> sortAscendingDates(List<Result> results) {
        Collections.sort(results, (o1, o2) -> {
            if (TextUtils.isEmpty(o1.getReleaseDate()) || TextUtils.isEmpty(o2.getReleaseDate()))
                return 0;
            Date prevReleaseDate = DateUtils.getInstance().convertUTCDateStringToDateObject(o1.getReleaseDate(), DateUtils.DATE_FORMAT_8);
            Date nextReleaseDate = DateUtils.getInstance().convertUTCDateStringToDateObject(o2.getReleaseDate(), DateUtils.DATE_FORMAT_8);
            if (prevReleaseDate != null && nextReleaseDate != null) {
                return prevReleaseDate.compareTo(nextReleaseDate);
            }
            return 0;
        });
        return results;
    }

}
