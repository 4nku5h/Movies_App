package com.ankush.shrivastava.ankush.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ankush.shrivastava.ankush.DataModel;
import com.ankush.shrivastava.ankush.MainActivity;
import com.ankush.shrivastava.ankush.MainFragment.MyListFragment;
import com.ankush.shrivastava.ankush.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

   ArrayList<DataModel> listOfMovies;
   Context context;
   ItemListener itemListener;
   public RecyclerAdapter(Context context,ArrayList<DataModel> listOfMovies){
       this.listOfMovies=listOfMovies;
       this.context=context;
       this.itemListener=itemListener;
   }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.ticket_movie,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
       holder.linearLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               MyListFragment.onItemSelected(listOfMovies.get(position));
               return;
           }

       });

       DataModel dataModel=listOfMovies.get(position);
       holder.tv_name.setText(dataModel.getTitle());
       holder.tv_type.setText(dataModel.getGenres());
       try {
           holder.tv_date.setText(dataModel.getActors());
       }catch (Exception xe){}
        Glide.with(context)
                .load(dataModel.getImageUrl())
                .into(holder.imageView);
       //holder.tv_date.setText(dataModel.getReleaseDate());

    }


    @Override
    public int getItemCount() {
        //if(listOfMovies!=null){
            return listOfMovies.size();
       // }
        //return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout linearLayout;
        ImageView imageView;
        TextView tv_name;
        TextView tv_type;
        TextView tv_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_movie_image);
            tv_name=itemView.findViewById(R.id.tv_movie_name);
            tv_type=itemView.findViewById(R.id.tv_movie_generes);
            linearLayout=itemView.findViewById(R.id.linearLayoutTicket);
        }
    }
}
