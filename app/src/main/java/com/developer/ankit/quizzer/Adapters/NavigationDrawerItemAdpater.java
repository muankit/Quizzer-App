package com.developer.ankit.quizzer.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.ankit.quizzer.Model.NavigationDrawerItem;
import com.developer.ankit.quizzer.R;

import java.util.Collections;
import java.util.List;

public class NavigationDrawerItemAdpater extends RecyclerView.Adapter<NavigationDrawerItemAdpater.MyViewHolder>{


    private List<NavigationDrawerItem> mDataList = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;


    public NavigationDrawerItemAdpater( Context context , List<NavigationDrawerItem> data) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.navigation_bar_item_list_view, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        NavigationDrawerItem current = mDataList.get(position);

        myViewHolder.imgIcon.setImageResource(current.getImageId());
        myViewHolder.title.setText(current.getTitle());
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Item CLikced", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imgIcon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.navigationTitleText);
            imgIcon = (ImageView) itemView.findViewById(R.id.navigationImgIcon);
        }
    }

}




//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
//
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDataList.size();
//    }
