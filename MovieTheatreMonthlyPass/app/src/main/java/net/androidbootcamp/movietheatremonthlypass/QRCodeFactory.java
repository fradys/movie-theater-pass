package net.androidbootcamp.movietheatremonthlypass;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

abstract public class QRCodeFactory {

    private static final int WIDTH = 90;
    private static final int HEIGHT = 90;

    public static Bitmap QRCodify(String message){
        Bitmap image = null;
        MultiFormatWriter formatWriter = new MultiFormatWriter();

        try{

            BitMatrix bitMatrix = formatWriter.encode(message, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
            image = new BarcodeEncoder().createBitmap(bitMatrix);

        } catch (WriterException ex){
            Log.w(formatWriter.getClass().getName(), ex.getMessage());
        }

        return image;
    }
}
