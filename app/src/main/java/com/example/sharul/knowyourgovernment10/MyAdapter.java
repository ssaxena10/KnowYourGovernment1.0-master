package com.example.sharul.knowyourgovernment10;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Sharul on 30-03-2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private static final String TAG = "OfficialAdapter";
    private List<Official> OList;
    private MainActivity mainActivity;

    public MyAdapter(List<Official> List, MainActivity ma) {
        this.OList = List;
        mainActivity = ma;
    }

    public void updateListData(List<Official> list) {
        this.OList.clear();
        this.OList.addAll(list);
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: MAKING NEW");
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.official, parent, false);
        itemView.setOnClickListener(mainActivity);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.office.setText(this.OList.get(position).getOffice());
        holder.name.setText(this.OList.get(position).getName());
        holder.party.setText("(" + this.OList.get(position).getParty() + ")");

    }

    @Override
    public int getItemCount() {
        return this.OList.size();
    }

    @Override
    public int getItemViewType(int pos){return pos;}
}
