package com.rkm.itunessearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rkm.itunessearch.R;
import com.rkm.itunessearch.model.Result;

import java.util.ArrayList;


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ListViewHolder> {

    private ArrayList<Result> listString;
    private Context context;
    private CheckedListener listener;

    public CartListAdapter(Context context, ArrayList<Result> listString) {
        this.context = context;
        this.listString = listString;

    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.list_item_cart, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {


        holder.tvname.setText("" + listString.get(position).getArtistName());
        holder.tvtrackname.setText("" + listString.get(position).getTrackName());
        holder.tvcollectionanme.setText("" + listString.get(position).getCollectionName());
        holder.tvreleasedate.setText("" + listString.get(position).getReleaseDate());
        holder.tvcollectionprice.setText("$" + listString.get(position).getCollectionPrice());
        Glide.with(context)
                .load(listString.get(position).getArtworkUrl100())
                .into(holder.imgthum);
    }

    @Override
    public int getItemCount() {
        //return 4;
        if (listString != null) {
            return listString.size();
        } else {
            return 0;
        }
    }

    public void setListener(CheckedListener listener) {
        this.listener = listener;
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {


        TextView tvname, tvtrackname, tvcollectionanme, tvcollectionprice, tvreleasedate;
        CheckBox chk_addcart;
        ImageView imgthum;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.name);
            tvtrackname = itemView.findViewById(R.id.description);
            tvcollectionanme = itemView.findViewById(R.id.description2);
            tvcollectionprice = itemView.findViewById(R.id.price);
            tvreleasedate = itemView.findViewById(R.id.description3);
            imgthum = itemView.findViewById(R.id.thumbnail);
            chk_addcart = itemView.findViewById(R.id.chk_addcart);



        }
    }

    public interface CheckedListener {
        void onItemChecked(int position, Result item);
    }

    public void setValue(ArrayList<Result> list) {
        this.listString.clear();
        this.listString.addAll(list);
        notifyDataSetChanged();
    }
}
