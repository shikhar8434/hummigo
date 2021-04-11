package com.hummo.hummigo.stories;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hummo.hummigo.R;
import com.hummo.hummigo.WebViewActivity;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>{

    private List<Slide> slideList;
    private Context context;

    public ViewPagerAdapter(List<Slide> slideList, Context context) {
        this.slideList = slideList;
        this.context = context;
    }

    @NonNull
    @Override
    public PagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagerViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_slide,parent, false
                )

        );
    }

    @Override
    public void onBindViewHolder(@NonNull PagerViewHolder holder, int position) {
        holder.setPagerViewHolder(slideList.get(position));
    }

    @Override
    public int getItemCount() {
        return slideList.size();
    }

    class PagerViewHolder extends RecyclerView.ViewHolder{
        private TextView author;
        private TextView  date;
        private TextView heading;
        private TextView desc;
        private ImageView image;
        private TextView readURL;
        private TextView likesCount;


        public PagerViewHolder(@NonNull View itemView) {
            super(itemView);

            // author = itemView.findViewById(R.id.slide_item_username);
            //date = itemView.findViewById(R.id.slide_item_timestamp);
            heading = itemView.findViewById(R.id.slide_item_heading);
            desc = itemView.findViewById(R.id.slide_item_description);
            image = itemView.findViewById(R.id.slide_item_image);
            likesCount = itemView.findViewById(R.id.slide_item_like_count);
            TextView linktv=itemView.findViewById(R.id.slide_item_article_link);
            String url="https://hummigo.herokuapp.com/Blogs";
            linktv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("URL",url);
                    itemView.getContext().startActivity(intent);
                }
            });

        }

        void setPagerViewHolder(Slide slide){
            // author.setText(slide.getAuthor());
            //date.setText(slide.getDate());
            heading.setText(slide.getHeading());
            desc.setText(slide.getDesc());
            likesCount.setText(String.valueOf(slide.getLikesCount()));
            Glide.with(context)
                    .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.placeholder))
                    .load(slide.getImageURL())
                    .into(image);
        }

    }


}


