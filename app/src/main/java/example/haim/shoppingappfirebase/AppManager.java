package example.haim.shoppingappfirebase;

import android.app.Application;
import android.graphics.Typeface;

/**
 * Loading the fonts
 */

public class AppManager extends Application {
    public static Typeface bold, light; //!! Carefully use static object - Constantly remaining in memory -
                                        // without garbage collector killing it. LifeCycle = always on).
    @Override
    public void onCreate() {
        super.onCreate();
        bold = Typeface.createFromAsset(getAssets(), "fonts/yehudaclm-bold-webfont.ttf");
        light = Typeface.createFromAsset(getAssets(), "fonts/yehudaclm-light-webfont.ttf");
    }
}
