package net.androidbootcamp.movietheatremonthlypass;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class Converters {

    @TypeConverter
    public static ArrayList<String> fromString(String value){
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String arraylistToString(ArrayList<String> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);

        return json;
    }

    @TypeConverter
    public static Date fromTimestamp (Long value){
        return ((value == null) ? null : new Date(value));
    }

    @TypeConverter
    public static Long dateToTimestamp (Date date){
        if(date == null)
            return null;
        else
            return date.getTime();
    }
}
