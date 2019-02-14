package com.ankush.shrivastava.ankush.MainFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.ankush.shrivastava.ankush.Adapter.ItemListener;
import com.ankush.shrivastava.ankush.Adapter.RecyclerAdapter;
import com.ankush.shrivastava.ankush.DataModel;
import com.ankush.shrivastava.ankush.MainActivity;
import com.ankush.shrivastava.ankush.R;

import java.util.ArrayList;


public class MyListFragment extends Fragment{
    RecyclerAdapter adapter;
    static RecyclerView myrecycler;
    public static ItemListener itemListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.itemListener=(ItemListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View view=inflater.inflate(R.layout.fragment_list,container,false);
         initViews(view);
         setAdapter();
         return view;
    }

    public void initViews(View view){
       myrecycler=view.findViewById(R.id.myRecycler);
        myrecycler.setLayoutManager(new LinearLayoutManager(MainActivity.context));
        myrecycler.addItemDecoration(new DividerItemDecoration(myrecycler.getContext(),DividerItemDecoration.VERTICAL));
    }

    public void setAdapter(){
        if(MainActivity.listOfMovies==null||MainActivity.listOfMovies.isEmpty())
            return;
            adapter = new RecyclerAdapter(MainActivity.context, MainActivity.listOfMovies);
            myrecycler.setAdapter(adapter);
        return;
    }

    public static void onItemSelected(DataModel selectedItem){
        itemListener.onItemSelected(selectedItem);
    }

    public static void requestAdapterUpdate(ArrayList<DataModel> listOfMovies_TEMP){
        myrecycler.setAdapter(new RecyclerAdapter(MainActivity.context,listOfMovies_TEMP));
    }

}
