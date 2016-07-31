package in.jainakshat.password;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Akshat on 6/2/2016.
 */
public class Utils {

    public static String TAG = "Password";

    public static String FIREBASE_URL = "https://password-99e68.firebaseio.com/";

    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager)  context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
