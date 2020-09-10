package com.rkm.itunessearch.view;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.rkm.itunessearch.R;
import com.rkm.itunessearch.viewmodel.SearchViewModel;
import com.rkm.itunessearch.adapter.CartListAdapter;
import com.rkm.itunessearch.model.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartActivity extends AppCompatActivity {
    SearchViewModel searchViewModel;
    @BindView(R.id.tv_header_fac)
    TextView tvHeaderFac;
    @BindView(R.id.toolbar_fac)
    Toolbar toolbarFac;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.rvresult)
    RecyclerView rvresult;
    private CartListAdapter demoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.init();
        setuprecycler();
        List<Result> cartList = new ArrayList<>();
        cartList.addAll(searchViewModel.getCartList());
        demoListAdapter.setValue((ArrayList<Result>) cartList);

    }

    private void setuprecycler() {
        demoListAdapter = new CartListAdapter(CartActivity.this, new ArrayList<Result>());
        rvresult.setAdapter(demoListAdapter);

    }
}
