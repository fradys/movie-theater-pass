package net.androidbootcamp.movietheatremonthlypass;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefSingle {

    private static PrefSingle mInstance;
    private Context mContext;
    private SharedPreferences mPreferences;

    private static final String LOGGED = "LOGGED";
    private static final String USER = "USER";
    private static final String PLAN = "PLAN_SET";
    private static final String DATABASE_CREATED = "DATABASE_CREATED";
    private static final String PLAN_TYPE = "PLAN_TYPE";
    private static final String SEND_EMAIL = "SEND_EMAIL";

    private PrefSingle(){}

    public static PrefSingle getInstance(){
        if(mInstance == null)
            mInstance = new PrefSingle();
        return mInstance;
    }

    public void initialize(Context context){
        this.mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void writePreference(String key, String value){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void writePreference(String key, boolean value){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void writePreference(String key, long value){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public String getPreference(String key){
        return mPreferences.getString(key, "");
    }


    public String userLogged(){
        return getPreference(USER);
    }

    public void setLogged(boolean logged){
        writePreference(LOGGED, logged);
    }

    public void setUserLogged(String username){
        writePreference(USER, username);
    }

    public boolean isLogged(){
        return mPreferences.getBoolean(LOGGED, false);
    }

    public boolean isLogged(String username){
        String loggedUser;
        loggedUser = userLogged();
        if(loggedUser.equals(username))
            return true;
        else
            return false;
    }

    public boolean isPlanSet(){
        return mPreferences.getBoolean(PLAN, false);
    }

    public void setPlan(boolean planSet){
        writePreference(PLAN, planSet);
    }

    public void setPlanType(long type){
        writePreference(PLAN_TYPE, type);
    }

    public long getPlanType(){
        return mPreferences.getLong(PLAN_TYPE, - 1);
    }

    public boolean isDatabaseCreated(){
        return mPreferences.getBoolean(DATABASE_CREATED, false);
    }

    public void setDatabaseCreated(boolean databaseCreated){
        writePreference(DATABASE_CREATED, databaseCreated);
    }

    public void setSendEmail(boolean send){
        writePreference(SEND_EMAIL, send);
    }

    public boolean getSendEmail(){
        return mPreferences.getBoolean(SEND_EMAIL, false);
    }

}
