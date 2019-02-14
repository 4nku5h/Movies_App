package com.ankush.shrivastava.ankush;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.ViewAnimator;
import com.ankush.shrivastava.ankush.Adapter.ItemListener;
import com.ankush.shrivastava.ankush.Detail.FragmentDetail;
import com.ankush.shrivastava.ankush.GetData.GetJsonTask;
import com.ankush.shrivastava.ankush.GetData.dataRecieved;
import com.ankush.shrivastava.ankush.MainFragment.MyListFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBarUtils;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;


public class MainActivity extends AppCompatActivity implements dataRecieved,ItemListener, SearchView.OnQueryTextListener {
     GetJsonTask getJsonTask;
     public static ArrayList<DataModel> listOfMovies;
     public static Context context;
     SmoothProgressBar smoothProgressBar;
     public static DataModel selectedItem;
     public static Boolean isSearchingHappen=false;
     public static FragmentManager fm;
     public static FrameLayout frameLayout;
     ViewAnimator viewAnimator;
     SearchView searchView;
     public static MenuItem searchViewId;
     final String saveKey="myarrayList";
     final String loading="Loading";
     final String noConnection="nconnection";
     final String connected="connected";
     final String nothingFound="notfound";
     final String somthingWrong="somethingWrng";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initItems();
        context=getApplicationContext();
        listOfMovies=listOfMovies=new ArrayList<DataModel>();
        Boolean network_available=checkInternetAvailable();
        if(network_available.equals(true)){
            showProcessBar();
            getJsonTask = new GetJsonTask(MainActivity.this);
            getJsonTask.execute();
            showAnimatedError(loading);
        }else
            showAnimatedError(noConnection);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        searchView=(SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchViewId=menu.findItem(R.id.menu_search);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_search:{
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void initItems(){
        smoothProgressBar=findViewById(R.id.myProcessbar);
        frameLayout=findViewById(R.id.framelayout);
        viewAnimator=findViewById(R.id.myViewAnimator);
        searchView=findViewById(R.id.menu_search);
        return;
    }


    @Override
    public void onDataRecieved(JSONArray data) {

        if(data==null||data.length()<=0){
            showAnimatedError(somthingWrong);
            return;
        }

        for (int i=0;i<data.length();i++){
            try {
                DataModel dataModel=null;
                String title="";
                String year="";
                String directors="";
                String releaseDate="";
                String rating="";
                String genres="";
                String imageUrl="";
                String plot="";
                String duration="";
                String actors="";
                JSONObject object=data.getJSONObject(i);
                year=String.valueOf(object.getInt("year"));
                title=object.getString("title");
                JSONObject innerObject=object.getJSONObject("info");
                directors=innerObject.getString("directors");
                releaseDate=innerObject.getString("release_date");
                rating="5";//String.valueOf(innerObject.getDouble("rating"));
                JSONArray generesArray=innerObject.getJSONArray("genres");

                for (int k=0;k<generesArray.length();k++){
                    genres+=generesArray.get(k).toString()+" ,";
                }

                imageUrl=innerObject.getString("image_url");
                plot=innerObject.getString("plot");
                duration=String.valueOf(innerObject.getInt("running_time_secs"));
                JSONArray actorsArray= innerObject.getJSONArray("actors");

                for(int j=0;j<actorsArray.length();j++){
                    actors+=String.valueOf(actorsArray.get(j)+"\n");
                    Log.i("dhgfdhcdvcdc-get",String.valueOf(actorsArray.get(j)));
                    Log.i("dhgfdhcdvcdc-0",actorsArray.getString(0));

                }
                dataModel=new DataModel(title,  year, directors,  releaseDate, rating, genres, imageUrl, plot, duration, actors);

                 listOfMovies.add(dataModel);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        runOnUiThread(new Runnable() {
            @Override
           public void run() {
                frameLayout.setVisibility(View.VISIBLE);
                showListFragment();
                if(listOfMovies==null||listOfMovies.isEmpty()){
                        showAnimatedError(somthingWrong);
                        return;
                }
           }
         });
        return;
    }
    public void showAnimatedError(final String id){
        View view;
        viewAnimator.setVisibility(View.VISIBLE);
        switch (id) {
            case loading: {
                view = findViewById(R.id.loadingData);
                break;
            }

            case noConnection: {
                view = findViewById(R.id.noConnection);
                break;
            }

            case connected: {
                view = findViewById(R.id.connected);
                break;
            }
            case nothingFound:{
                view=findViewById(R.id.nothingFound);
                break;
            }
            case somthingWrong:{
                view=findViewById(R.id.somethingWrong);
                break;
            }
            default: {
                view = findViewById(R.id.loadingData);
                break;
            }
        }
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(view));
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                viewAnimator.setVisibility(View.GONE);

            }
        },4000);
    }

    public void showListFragment(){
        fm=getFragmentManager();
        fm.findFragmentById(R.id.fraglist);
        MyListFragment myListFragment =new MyListFragment();
        FragmentTransaction ft=fm.beginTransaction();
        ft.add(R.id.fraglist, myListFragment);
        ft.commit();
        stopProcessBar();
    }


//++++++++++++++++++++++++++++++++++++++++++++++++++// setAdapter

    @SuppressLint("ResourceType")
    @Override
    public void onItemSelected(DataModel dataModel){
        searchViewId.setVisible(false);
        selectedItem=dataModel;
        FragmentDetail fragmentDetail=new FragmentDetail();
        FragmentTransaction ft=fm.beginTransaction().addToBackStack(null);
        ft.replace(R.id.fraglist,fragmentDetail);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right,android.R.anim.slide_in_left,android.R.anim.slide_out_right);
        ft.commit();
    }

//+++++++++++++++++++++++++++++++++++++++++++++++++//Process Bar

    public void showProcessBar(){
        smoothProgressBar.setVisibility(View.VISIBLE);
        smoothProgressBar.setIndeterminateDrawable(new SmoothProgressDrawable.Builder(getApplicationContext())
                .interpolator(new AccelerateInterpolator())
                .progressiveStart(true)
                .build());
        smoothProgressBar.setSmoothProgressDrawableBackgroundDrawable(
                SmoothProgressBarUtils.generateDrawableWithColors(
                        getResources().getIntArray(R.array.pocket_background_colors),
                        ((SmoothProgressDrawable) smoothProgressBar.getIndeterminateDrawable()).getStrokeWidth()));
    }

    public void stopProcessBar(){
        smoothProgressBar.setSmoothProgressDrawableBackgroundDrawable(
                SmoothProgressBarUtils.generateDrawableWithColors(
                        getResources().getIntArray(R.array.pocket_background_stop),
                        ((SmoothProgressDrawable) smoothProgressBar.getIndeterminateDrawable()).getStrokeWidth()));
        smoothProgressBar.progressiveStop();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
                smoothProgressBar.animate().alpha(0.0f).setDuration(6000).translationY(-3000);
                smoothProgressBar.setVisibility(View.GONE);
            }
        },4000);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList(saveKey, (ArrayList<? extends Parcelable>) listOfMovies);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        if(query.trim().length()>0){
            sortData(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.trim().length()<=0 && listOfMovies!=null && !listOfMovies.isEmpty())
            MyListFragment.requestAdapterUpdate(listOfMovies);
        return false;
    }

    public void sortData(String query){
        ArrayList<DataModel> listOfMovies_TEMP=new ArrayList<DataModel>();
        for(int i=0;i<listOfMovies.size();i++){
            if(listOfMovies.get(i).getTitle().indexOf(query)!=-1?true:false)
            {
                listOfMovies_TEMP.add(listOfMovies.get(i));
            }
        }
        if(listOfMovies_TEMP!=null&& !listOfMovies_TEMP.isEmpty()){
            MyListFragment.requestAdapterUpdate(listOfMovies_TEMP);
        }
        else showAnimatedError(nothingFound);


    }
    public Boolean checkInternetAvailable(){
        ConnectivityManager cm=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if(cm.getActiveNetworkInfo()!=null && cm.getActiveNetworkInfo().isConnected()){
            return true;
        }
        return false;
    }
}
