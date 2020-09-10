package com.rkm.itunessearch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rkm.itunessearch.R;
import com.rkm.itunessearch.model.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DemoListAdapter extends RecyclerView.Adapter<DemoListAdapter.ListViewHolder>implements Filterable {

    private ArrayList<Result> listString;
    private ArrayList<Result> listmain;
    private Context context;
    private CheckedListener listener;

    public DemoListAdapter(Context context, ArrayList<Result> listString) {
        this.context = context;
        this.listString = listString;
        this.listmain = listString;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.list_item_search, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, final int position) {


        holder.tvname.setText("" + listString.get(position).getArtistName());
        holder.tvtrackname.setText("" + listString.get(position).getTrackName());
        holder.tvcollectionanme.setText("" + listString.get(position).getCollectionName());
        holder.tvreleasedate.setText("" + listString.get(position).getReleaseDate());
        holder.tvcollectionprice.setText("USD $" + listString.get(position).getCollectionPrice());
        Glide.with(context)
                .load(listString.get(position).getArtworkUrl100())
                .into(holder.imgthum);

        holder.chk_addcart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onItemChecked(position,listString.get(position));

            }
        }
        );



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
        LinearLayout ll_main_familly;
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
            //ll_main_familly = itemView.findViewById(R.id.ll_main_familly);


        }
    }

    public interface CheckedListener {
        void onItemChecked(int position, Result item);
    }

    public void setValue(ArrayList<Result> list) {
        this.listString.clear();
        this.listmain.clear();
        //this.listString.addAll(list);
        this.listmain.addAll(list);
//        Log.e("PatientList--",listString.size()+"");
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listString = listmain;
                } else {
                    ArrayList<Result> filteredList = new ArrayList<>();
                    for (Result row : listmain) {

                        if (row.getArtistName()!=null || row.getTrackName()!=null|| row.getCollectionName()!=null) {

                            if ((row.getArtistName()!=null?row.getArtistName().toLowerCase():"").contains(charString.toLowerCase()) || (row.getTrackName()!=null?row.getTrackName().toLowerCase():"").contains(charString.toLowerCase())|| (row.getCollectionName()!=null?row.getCollectionName().toLowerCase():"").contains(charString.toLowerCase())) {
                                filteredList.add(row);
                            }
                        }

                    }

                    listString = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listString;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listString = (ArrayList<Result>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
