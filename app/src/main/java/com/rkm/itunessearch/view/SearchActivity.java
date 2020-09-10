package com.rkm.itunessearch.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.rkm.itunessearch.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.tv_header_fac)
    TextView tvHeaderFac;
    @BindView(R.id.toolbar_fac)
    Toolbar toolbarFac;
    @BindView(R.id.et_artistname)
    EditText etArtistname;
    @BindView(R.id.et_trackname)
    EditText etTrackname;
    @BindView(R.id.et_collectionname)
    EditText etCollectionname;
    @BindView(R.id.et_collectionprice)
    EditText etCollectionprice;
    @BindView(R.id.et_releasedate)
    EditText etReleasedate;
    @BindView(R.id.btn_search)
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SearchActivity.this,SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("saerchparam1", etArtistname.getText().toString());
                bundle.putString("saerchparam2", etTrackname.getText().toString());
                bundle.putString("saerchparam3", etCollectionname.getText().toString());
                bundle.putString("saerchparam4", etCollectionprice.getText().toString());
                i.putExtras(bundle);
                startActivity(i);
            }
        });
    }


}
