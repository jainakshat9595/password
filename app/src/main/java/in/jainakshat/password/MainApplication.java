package in.jainakshat.password;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.soroushjavdan.lockapplicationsample.applicationlockerlibrary.MyLifecycleHandler;

/**
 * Created by Akshat on 5/24/2016.
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("appPasscode");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot.toString(): "+dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("databaseError: "+databaseError);
            }
        });

        registerActivityLifecycleCallbacks(new MyLifecycleHandler("1234"));
    }
}
