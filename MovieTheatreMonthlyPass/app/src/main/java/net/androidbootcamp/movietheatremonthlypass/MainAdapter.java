package net.androidbootcamp.movietheatremonthlypass;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by Daniel on 3/23/2018.
 */

 class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<Movie> dataset;

    public MainAdapter(ArrayList<Movie> dataset){
        this.dataset = dataset;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {

        new DownloadImage(holder.imageView).execute(dataset.get(position).getImageUrl());
        holder.textMovieID.setText(dataset.get(position).getId());
        holder.textView.setText(dataset.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;
        public TextView textMovieID;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgPoster);
            textView = itemView.findViewById(R.id.movieTitle);
            textMovieID = itemView.findViewById(R.id.movieID);
        }
    }
}
