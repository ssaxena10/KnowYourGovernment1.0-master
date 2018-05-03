package com.example.sharul.knowyourgovernment10;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;



public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView office;
    public TextView name;
    public TextView party;

    public MyViewHolder(View view) {
        super(view);

        office = (TextView) view.findViewById(R.id.office);
        name = (TextView) view.findViewById(R.id.name);
        party = (TextView) view.findViewById(R.id.party);
    }
}
