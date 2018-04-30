package net.androidbootcamp.movietheatremonthlypass;
import android.app.Activity;
import android.content.Context;

public final class MenuActionPerformed {

    private static final Class<?> SETTINGS = SettingsActivity.class;
    private static final Class<?> PROFILE = ProfileActivity.class;
    private static final Class<?> BILLING = PlansActivity.class;
    private static final Class<?> RESERVATIONS = ReservationsActivity.class;
    private static final Class<?> MAIN = MainActivity.class;

    public static void menuItemAction(Context context, int itemSelected){
        switch(itemSelected){
            case R.id.item_settings:
                loadActivity(context, SETTINGS);
                break;
            case R.id.item_profile:
                loadActivity(context, PROFILE);
                break;
            case R.id.item_billing:
                loadActivity(context, BILLING);
                break;
            case R.id.item_reservations:
                loadActivity(context, RESERVATIONS);
                break;
            case R.id.item_logout:
                PrefSingle.getInstance().initialize(context.getApplicationContext());
                PrefSingle.getInstance().setLogged(false);
                PrefSingle.getInstance().setUserLogged(null);
                loadActivity(context, MAIN);
                break;
        }
    }

    private static void loadActivity(Context context, Class<?> target){
        if(context.getClass().equals(target))
            return;

        context.startActivity(new android.content.Intent(context, target));
    }

}
