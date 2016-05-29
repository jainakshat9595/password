package in.jainakshat.password;

import android.app.Application;

import com.facebook.stetho.Stetho;

import net.soroushjavdan.lockapplicationsample.applicationlockerlibrary.MyLifecycleHandler;

/**
 * Created by Akshat on 5/24/2016.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        registerActivityLifecycleCallbacks(new MyLifecycleHandler("1234"));
    }
}
