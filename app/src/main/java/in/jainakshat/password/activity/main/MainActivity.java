package in.jainakshat.password.activity.main;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import in.jainakshat.password.R;
import in.jainakshat.password.Utils;
import in.jainakshat.password.activity.login.LoginActivity;
import in.jainakshat.password.model.Entity;

public class MainActivity extends AppCompatActivity {

    private Resources mResources;
    private int mFabMargin;
    private boolean isCardAddItemOpened = false;

    private LinearLayout cardAddItem;
    private FloatingActionButton fab;
    private LinearLayout cardClose;
    private RecyclerView cardList;
    private CardListAdapter cardListAdapter;

    private EditText cardName;
    private EditText cardPassword;
    private EditText cardInformation;

    private ArrayList<Entity> items;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference entityRef = database.getReference("entity");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    getBaseContext().startActivity(new Intent(getBaseContext(), LoginActivity.class));
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        entityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateItemList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(Utils.TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

        mResources = getBaseContext().getResources();
        mFabMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, mResources.getDisplayMetrics());
        cardAddItem = (LinearLayout) findViewById(R.id.card_add_item);
        cardAddItem.setVisibility(View.GONE);
        cardClose = (LinearLayout) findViewById(R.id.card_close);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        final CoordinatorLayout.LayoutParams paramsBottom = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
        paramsBottom.setMargins(mFabMargin, mFabMargin, mFabMargin, mFabMargin);
        paramsBottom.gravity = Gravity.BOTTOM;
        final CoordinatorLayout.LayoutParams paramsBottomEnd = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
        paramsBottomEnd.setMargins(mFabMargin, mFabMargin, mFabMargin, mFabMargin);
        paramsBottomEnd.gravity = Gravity.BOTTOM | Gravity.END;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isCardAddItemOpened) {
                    addEntity();
                    cardAddItem.setVisibility(View.GONE);
                    cardList.setVisibility(View.VISIBLE);
                    fab.setLayoutParams(paramsBottomEnd);
                    fab.setImageResource(R.drawable.plus);
                    isCardAddItemOpened = false;
                    cardName.clearFocus();
                    cardPassword.clearFocus();
                    cardInformation.clearFocus();
                    cardName.setText("");
                    cardPassword.setText("");
                    cardInformation.setText("");
                    Utils.hideSoftKeyboard(getBaseContext(), getCurrentFocus());
                }
                else {
                    cardAddItem.setVisibility(View.VISIBLE);
                    cardList.setVisibility(View.GONE);
                    fab.setLayoutParams(paramsBottom);
                    fab.setImageResource(R.drawable.check);
                    isCardAddItemOpened = true;
                }

            }
        });
        cardClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isCardAddItemOpened) {
                    cardAddItem.setVisibility(View.GONE);
                    cardList.setVisibility(View.VISIBLE);
                    fab.setLayoutParams(paramsBottomEnd);
                    fab.setImageResource(R.drawable.plus);
                    isCardAddItemOpened = false;
                    cardName.clearFocus();
                    cardPassword.clearFocus();
                    cardInformation.clearFocus();
                    Utils.hideSoftKeyboard(getBaseContext(), getCurrentFocus());
                }
            }
        });
        cardList = (RecyclerView) findViewById(R.id.card_list);
        cardList.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        cardListAdapter = new CardListAdapter(getBaseContext());
        if(items==null) {
            items = new ArrayList<>();
        }
        cardListAdapter.setItems(items);
        cardList.setAdapter(cardListAdapter);
        cardName = (EditText) findViewById(R.id.card_name);
        cardPassword = (EditText) findViewById(R.id.card_password);
        cardInformation = (EditText) findViewById(R.id.card_information);
    }

    @Override
    public void onBackPressed() {
        if(isCardAddItemOpened) {
            CoordinatorLayout.LayoutParams paramsBottomEnd = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
            paramsBottomEnd.setMargins(mFabMargin, mFabMargin, mFabMargin, mFabMargin);
            paramsBottomEnd.gravity = Gravity.BOTTOM | Gravity.END;
            cardAddItem.setVisibility(View.GONE);
            cardList.setVisibility(View.VISIBLE);
            fab.setLayoutParams(paramsBottomEnd);
            fab.setImageResource(R.drawable.plus);
            isCardAddItemOpened = false;
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete_all) {
            Snackbar.make(cardList, "Please be sure!", Snackbar.LENGTH_LONG).setAction("Delete All", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteAllEntities();
                }
            }).setActionTextColor(Color.RED).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addEntity() {
        Entity entity = new Entity();
        entity.setName(cardName.getText().toString());
        entity.setPassword(cardPassword.getText().toString());
        entity.setInformation(cardInformation.getText().toString());

        entityRef.child(cardName.getText().toString()).setValue(entity).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(cardList, "Entity added!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    public void updateItemList(DataSnapshot dataSnapshot) {
        items.clear();
        for (DataSnapshot childSnapshot:dataSnapshot.getChildren()) {
            Entity entity = childSnapshot.getValue(Entity.class);
            items.add(entity);
        }
        if(cardListAdapter!=null) {
            cardListAdapter.setItems(items);
        }
    }

    public void deleteAllEntities() {
        entityRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Snackbar.make(cardList, "Success!", Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}

