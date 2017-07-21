package in.jainakshat.password;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Akshat on 26-06-2017.
 */

public class SharedPreferencesManager {

    public static SharedPreferences mSharedPreferences;

    public static void setPassKey(int passKey) {
        mSharedPreferences.edit().putInt("PassKey", passKey).apply();
    }

    public static int getPassKey(Context context) {
        if(mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences(Utils.TAG, Context.MODE_PRIVATE);
        }
        return mSharedPreferences.getInt("PassKey", -1);
    }

}
