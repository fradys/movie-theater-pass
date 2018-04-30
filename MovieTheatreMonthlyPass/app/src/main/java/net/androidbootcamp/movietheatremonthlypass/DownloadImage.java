package net.androidbootcamp.movietheatremonthlypass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {

    ImageView mImage;

    public DownloadImage(ImageView mImage){
        this.mImage = mImage;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        String targetURL = url[0];
        Bitmap tempImage = null;

        try{
            InputStream in = new java.net.URL(targetURL).openStream();
            tempImage = BitmapFactory.decodeStream(in);
        } catch (MalformedURLException ex){
            Log.e(ex.getClass().getName(), ex.getMessage());
        } catch (IOException io){
            Log.e(io.getClass().getName(), io.getMessage());
        }

        return tempImage;
    }

    protected void onPostExecute(Bitmap result){
        mImage.setImageBitmap(result);
    }
}
