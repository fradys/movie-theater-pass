package net.androidbootcamp.movietheatremonthlypass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.ViewHolder> {

    private ArrayList<String[]> dataset;
    public HashMap<String, Integer> colorStatus;


    public ReservationAdapter(ArrayList<String[]> data, Context context){
        this.dataset = data;

        //Initialize Color Mapping
        colorStatus = new HashMap<>();
        colorStatus.put(TicketStatus.CONFIRMED, ContextCompat.getColor(context, R.color.status_green));
        colorStatus.put(TicketStatus.CONSUMED, ContextCompat.getColor(context, R.color.status_blue));
        colorStatus.put(TicketStatus.NO_SHOW, ContextCompat.getColor(context, R.color.status_red));
        colorStatus.put(TicketStatus.CANCELED, ContextCompat.getColor(context, R.color.status_grey));
    }


    @Override
    public ReservationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_card, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ReservationAdapter.ViewHolder holder, final int position) {

        String statusName = dataset.get(position)[7];
        new DownloadImage(holder.imgPoster).execute(dataset.get(position)[0]);
        holder.txtMovieTitle.setText(dataset.get(position)[2]);
        holder.txtMovieTheater.setText(dataset.get(position)[3]);
        holder.txtMovieDate.setText(dataset.get(position)[4]);
        holder.txtMovieHour.setText(dataset.get(position)[5] + " - " + dataset.get(position)[6]);
        holder.txtReservationStatus.setText(statusName);
        holder.txtReservationStatus.setTextColor(colorStatus.get(statusName));

        //Create QR Code Image
        Bitmap bitmap = QRCodeFactory.QRCodify(dataset.get(position)[8]);
        if(bitmap != null)
            holder.imgQRCode.setImageBitmap(bitmap);
        else
            holder.imgQRCode.setImageResource(R.drawable.blank);

    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgPoster;
        public TextView txtMovieTitle;
        public TextView txtMovieTheater;
        public TextView txtMovieDate;
        public TextView txtMovieHour;
        public TextView txtReservationStatus;
        public ImageView imgQRCode;



        public ViewHolder(View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgMoviePoster);
            txtMovieTitle = itemView.findViewById(R.id.reservationMovieTitle);
            txtMovieTheater = itemView.findViewById(R.id.reservationMovieTheaterName);
            txtMovieDate = itemView.findViewById(R.id.reservationDate);
            txtMovieHour = itemView.findViewById(R.id.reservationHour);
            txtReservationStatus = itemView.findViewById(R.id.reservationStatus);
            imgQRCode = itemView.findViewById(R.id.imgReservationQRCode);
        }
    }
}