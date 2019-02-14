package com.ankush.shrivastava.ankush.Detail;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ankush.shrivastava.ankush.DataModel;
import com.ankush.shrivastava.ankush.MainActivity;
import com.ankush.shrivastava.ankush.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class FragmentDetail extends Fragment {
    TextView tv_title;
    TextView tv_date;
    TextView tv_generes;
    TextView tv_plot;
    TextView tv_rating;
    TextView tv_actors;
    TextView tv_duration;
    ImageView iv_image;
    ImageView iv_blur;
    DataModel dataModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View view=inflater.inflate(R.layout.fragment_detail,container,false);
         initViews(view);
          dataModel= MainActivity.selectedItem;
        try {
            ShowData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  view;
    }
    public void initViews(View view){
         tv_title=view.findViewById(R.id.tv_frag_title);
         tv_date=view.findViewById(R.id.tv_frag_date);
         tv_generes=view.findViewById(R.id.tv_frag_generes);
         tv_plot=view.findViewById(R.id.tv_frag_plot);
         tv_rating=view.findViewById(R.id.tv_frag_rating);
         tv_actors=view.findViewById(R.id.tv_frag_actors);
         tv_duration=view.findViewById(R.id.tv_frag_duration);
         iv_image= view.findViewById(R.id.iv_frag_image);
         iv_blur=view.findViewById(R.id.iv_frag_blur);
    }
    public void ShowData() throws IOException {
        String[] split_dateTimeDetail=dataModel.getReleaseDate().split("T");
        String date=split_dateTimeDetail[0];
       if(dataModel!=null){
           tv_title.setText(dataModel.getTitle());
           Glide.with(getActivity())
                   .load(dataModel.getImageUrl())
                   .into(iv_image);

           Glide.with(getActivity())
                   .load(dataModel.getImageUrl())
                   .apply(RequestOptions.fitCenterTransform())
                   .apply(RequestOptions.bitmapTransform(new BlurTransformation(20,3)))
                   .into(iv_blur);
           try {
               tv_date.setText(date);
               tv_actors.setText(dataModel.getActors());
               tv_duration.setText(dataModel.getDuration()+" sec");
               tv_generes.setText(dataModel.getGenres());
               tv_plot.setText(dataModel.getPlot());
               tv_rating.setText(dataModel.getRating());
           }catch (Exception e){}

       }
    }

    @Override
    public void onDestroy() {
        MainActivity.searchViewId.setVisible(true);
        super.onDestroy();
    }
}
