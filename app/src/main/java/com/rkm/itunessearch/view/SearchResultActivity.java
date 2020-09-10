package com.rkm.itunessearch.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.rkm.itunessearch.R;
import com.rkm.itunessearch.viewmodel.SearchViewModel;
import com.rkm.itunessearch.adapter.DemoListAdapter;
import com.rkm.itunessearch.edittext.CustomEditText;
import com.rkm.itunessearch.edittext.DrawableClickListener;
import com.rkm.itunessearch.model.ItuneResponse;
import com.rkm.itunessearch.model.Result;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.rkm.itunessearch.utils.AppConstant.listOfcart;

public class SearchResultActivity extends AppCompatActivity {
    ArrayList<Result> resultArrayList = new ArrayList<>();
    SearchViewModel searchViewModel;
    @BindView(R.id.tv_header_fac)
    TextView tvHeaderFac;
    @BindView(R.id.toolbar_fac)
    Toolbar toolbarFac;
    @BindView(R.id.loading)
    ProgressBar loading;
    @BindView(R.id.rvresult)
    RecyclerView rvresult;

    DemoListAdapter demoListAdapter;
    @BindView(R.id.cart)
    ImageView cart;
    @BindView(R.id.sort)
    ImageView sort;
    @BindView(R.id.et_searchview)
    CustomEditText etSearchview;
    @BindView(R.id.img_calender)
    ImageView imgCalender;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
       /* Bundle bundle = getIntent().getExtras();
        String param1 = bundle.getString("saerchparam1","all");
        String param2 = bundle.getString("saerchparam2","");
        String param3 = bundle.getString("saerchparam3","");
        String param4 = bundle.getString("saerchparam4","");*/

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.init();
        setuprecycler();
        requestForSearch("", "", "", "");
        rvresult.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listOfcart.size() > 0) {
                    startActivity(new Intent(SearchResultActivity.this, CartActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "No Cart item", Toast.LENGTH_LONG).show();
                }
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogForOptionselct();
            }
        });

        etSearchview.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                demoListAdapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        etSearchview.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        etSearchview.setText("");
                        break;

                    default:
                        break;
                }
            }
        });


    }

    private void setuprecycler() {
        demoListAdapter = new DemoListAdapter(SearchResultActivity.this, new ArrayList<Result>());
        rvresult.setAdapter(demoListAdapter);
        demoListAdapter.setListener(new DemoListAdapter.CheckedListener() {
            @Override
            public void onItemChecked(int position, Result item) {
                listOfcart.add(item);
            }
        });
    }

    private void requestForSearch(String param1, String param2, String param3, String param4) {

        searchViewModel.getSearchResult(param1, param2).observe(this, new Observer<ItuneResponse>() {
            @Override
            public void onChanged(ItuneResponse ituneResponse) {

                resultArrayList.clear();
                if (ituneResponse.getResults().size() > 0) {
                    rvresult.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    resultArrayList.addAll(ituneResponse.getResults());
                    demoListAdapter.setValue((ArrayList<Result>) ituneResponse.getResults());
                }

            }
        });
    }

    int selectpos = 0;

    private void showDialogForOptionselct() {
        selectpos = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialog);


//        builder.setTitle("Filter");

        final AlertDialog alertDialog;
        View dialogueView = LayoutInflater.from(this).inflate(R.layout.dialog_selection, null, false);
        //Log.e("Dialog","id: "+dialogueView.getId());
        TextView tvChoose = dialogueView.findViewById(R.id.tv_choose);
        final RadioGroup rg = dialogueView.findViewById(R.id.rg);

        builder.setView(dialogueView);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        tvChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                int radioButtonID = rg.getCheckedRadioButtonId();
                View radioButton = rg.findViewById(radioButtonID);
                int idx = rg.indexOfChild(radioButton);
                sortdata(idx);


            }
        });

        alertDialog.show();
    }

    private void sortdata(int position) {
        // Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
        switch (position) {
            case 0:
                sortbyartistname();
                break;
            case 1:
                sortbycollectionname();
                break;
            case 2:
                sortbytrackname();
                break;
            case 3:
                sortbyprice();
                break;
        }
    }

    private void sortbyprice() {
        if (resultArrayList != null && resultArrayList.size() > 0) {
            Collections.sort(resultArrayList, (result, t1) -> result.getCollectionPrice().compareTo(t1.getCollectionPrice()));
        }

        demoListAdapter.setValue(resultArrayList);

    }

    private void sortbytrackname() {
        if (resultArrayList != null && resultArrayList.size() > 0) {
            Collections.sort(resultArrayList, (result, t1) -> {
                if (!TextUtils.isEmpty(result.getTrackName()) && !TextUtils.isEmpty(t1.getTrackName())) {
                    return result.getTrackName().compareTo(t1.getTrackName());
                }
                return 0;
            });
        }

        demoListAdapter.setValue(resultArrayList);
    }

    private void sortbycollectionname() {
        if (resultArrayList != null && resultArrayList.size() > 0) {
            Collections.sort(resultArrayList, (result, t1) -> {
                if (!TextUtils.isEmpty(result.getCollectionName()) && !TextUtils.isEmpty(t1.getCollectionName())) {
                    return result.getCollectionName().compareTo(t1.getCollectionName());
                }
                return 0;
            });
        }
        demoListAdapter.setValue(resultArrayList);
    }

    private void sortbyartistname() {
        if (resultArrayList != null && resultArrayList.size() > 0) {
            Collections.sort(resultArrayList, (result, t1) -> {
                if (!TextUtils.isEmpty(result.getArtistName()) && !TextUtils.isEmpty(t1.getArtistName())) {
                    return result.getArtistName().compareTo(t1.getArtistName());
                }
                return 0;
            });
        }

        demoListAdapter.setValue(resultArrayList);
    }
}
