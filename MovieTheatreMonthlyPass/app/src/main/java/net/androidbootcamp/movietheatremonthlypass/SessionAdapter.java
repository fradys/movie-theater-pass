package net.androidbootcamp.movietheatremonthlypass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SessionAdapter extends BaseAdapter {

//    private int clickedView;
    private final Context mContext;
    private final ArrayList<String[]> mData;
    private View selectedView;
    private int selectedPosition;
//    private final ArrayList<View> views;
//    private final int[] movieTypeLogo;
//
//    public SessionAdapter(Context context, ArrayList<String[]> data){
//        views = new ArrayList<View>();
//        this.mContext = context;
//        clickedView = -1;
//        this.mData = data;
//        movieTypeLogo = new int[] {
//                R.drawable.type3d,
//                R.drawable.imax
//        };
//    }

    public SessionAdapter(Context context, ArrayList<String[]> dataset){
        this.mContext = context;
        this.mData = dataset;
        selectedView = null;
        selectedPosition = -1;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final String[] values = mData.get(position);
        final ViewHolder viewHolder;

        //if null, inflate the custom layout
        if(convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.custom_button, null);
            viewHolder = new ViewHolder();
            viewHolder.buttonView = convertView.findViewById(R.id.linearCustomButton);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Get the TextViews
        final TextView txtButtonDate = convertView.findViewById(R.id.txtButtonDate);
        final TextView txtButtonWeekDay = convertView.findViewById(R.id.txtButtonWeekDay);
        final TextView txtButtonHourStart = convertView.findViewById(R.id.txtButtonHourStart);
        final TextView txtButtonHourEnd = convertView.findViewById(R.id.txtButtonHourEnd);
        final TextView txtButtonMovieType = convertView.findViewById(R.id.txtButtonMovieType);
        final ImageView imgButtonMovieType = convertView.findViewById(R.id.imgButtonMovieType);


        //Get the start and end
        txtButtonDate.setText(values[0]); // Date - 20/04
        txtButtonWeekDay.setText(values[1]); // Day of Week - THU
        txtButtonHourStart.setText("Begin: " + values[2]); // Start hour - 11:00 AM
        txtButtonHourEnd.setText("Finish: " + values[3]); // Finish hour - 12:30 PM

        //Change between text 2D, or image showing 3D, IMAX.
        switch (values[4].toUpperCase()){
            case SessionType._3D:
//                txtButtonMovieType.setVisibility(View.GONE);
                txtButtonMovieType.setText(values[4]);
                txtButtonMovieType.setVisibility(View.INVISIBLE);
                imgButtonMovieType.setImageResource(R.drawable.type3d);
                imgButtonMovieType.setVisibility(View.VISIBLE);
                break;
            case SessionType.IMAX:
//                txtButtonMovieType.setVisibility(View.GONE);
                txtButtonMovieType.setText(values[4]);
                txtButtonMovieType.setVisibility(View.INVISIBLE);
                imgButtonMovieType.setImageResource(R.drawable.imax);
                imgButtonMovieType.setVisibility(View.VISIBLE);
                break;
            default:
//                txtButtonMovieType.setVisibility(View.VISIBLE);
                txtButtonMovieType.setText(values[4]);
                txtButtonMovieType.setVisibility(View.VISIBLE);
                imgButtonMovieType.setVisibility(View.GONE);
        }



        //Set listener
        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


                for (int i = 0; i < ((GridView) view.getParent()).getChildCount(); i++) {
                    View vi = ((GridView) view.getParent()).getChildAt(i);
                    vi.setBackgroundResource(R.drawable.rounded_edge_normal);
                }

                viewHolder.buttonView.setBackgroundResource(R.drawable.rounded_edge_pressed);
                selectedView = viewHolder.buttonView;
                selectedPosition = position;
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public View getSelectedView(){
        return selectedView;
    }

    public int getSelectedPosition(){
        return selectedPosition;
    }

    public static class ViewHolder {
        public View buttonView;
    }


}
