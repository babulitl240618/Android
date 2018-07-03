package com.imagine.bd.hayvenapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imagine.bd.hayvenapp.Activity.CalingActivity;
import com.imagine.bd.hayvenapp.Model.DataModel;
import com.imagine.bd.hayvenapp.R;
import com.imagine.bd.hayvenapp.utils.RoundedCornersTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class JoinGroupSequenceAdapter extends RecyclerView.Adapter<JoinGroupSequenceAdapter.ViewHolder>{
    private ArrayList<DataModel> list;
    public Context context;
    ViewHolder viewHolder;
    int lastPosition = -1;
    public JoinGroupSequenceAdapter(ArrayList<DataModel> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void onBindViewHolder(final ViewHolder viewHolder,
                                 final int position) {

         viewHolder.tvDate.setText(list.get(position).getName());


        if (position > lastPosition) {

            /*Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.up_from_bottom);
            viewHolder.itemView.startAnimation(animation);*/
            lastPosition = position;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        //Inflate the layout, initialize the View Holder
        if (lastPosition==0){ LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemLayoutView=inflater.inflate(R.layout.new_chat_list_sequence, parent,false);


            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,CalingActivity.class));
                }
            });
//
//        View itemLayoutView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_contacts_list, false);
//
//

            viewHolder = new ViewHolder(itemLayoutView);}else { LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemLayoutView=inflater.inflate(R.layout.new_chat_list_sequence, parent,false);


            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context,CalingActivity.class));
                }
            });
//
//        View itemLayoutView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.activity_contacts_list, false);
//
//

            viewHolder = new ViewHolder(itemLayoutView);}




        return viewHolder;
    }

    // initializes textview and imageview to be used by RecyclerView.
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDate;


        public ViewHolder(View view) {
            super(view);

            tvDate = (TextView) view.findViewById(R.id.name);


        }
    }
}
