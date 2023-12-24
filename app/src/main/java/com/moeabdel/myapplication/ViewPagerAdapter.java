package com.moeabdel.myapplication;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.lifecycle.LifecycleOwner;

import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;



public class ViewPagerAdapter extends PagerAdapter {



    private String[] mediaUrls;
    private LayoutInflater inflater;
    private Context context;
    private int ratingImage;

    public ViewPagerAdapter(Context context, String[] mediaUrls) {
        this.context = context;
        this.mediaUrls = mediaUrls;
        //this.progressBar = progressBar;
        //this.ratingImage = ratingImage;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mediaUrls.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view;
        if (!mediaUrls[position].contains("game")) {
             view = inflater.inflate(R.layout.page_item_video, container, false);
            YouTubePlayerView youTubePlayerView = view.findViewById(R.id.youtube_player_view);
            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(YouTubePlayer youTubePlayer) {
                    // Video is ready, you can load and play the video here
                    String videoId = mediaUrls[position];

                    youTubePlayer.cueVideo(videoId, 0);
                }

                @Override
                public void onError(YouTubePlayer youTubePlayer, com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerError error) {
                    //Toast.makeText(PagerAdapter.this, "Youtube Player Not Working", Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(view);
        } else {
             view = inflater.inflate(R.layout.pager_item, container, false);
            ImageView photoView = view.findViewById(R.id.imageView);
            ProgressBar progressBar = view.findViewById(R.id.stampImage);

            // Set visibility to VISIBLE before loading image
            progressBar.setVisibility(View.VISIBLE);

            Picasso.get().load(mediaUrls[position]).into(photoView, new Callback() {
                @Override
                public void onSuccess() {
                    // Image loading is successful, hide the ProgressBar
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onError(Exception e) {
                    // Handle error if image loading fails
                    progressBar.setVisibility(View.INVISIBLE);
                    // You may want to show a placeholder image or handle the error in another way
                    photoView.setImageResource(R.drawable.noimagetodisplay);
                }
            });

            container.addView(view);
        }
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}