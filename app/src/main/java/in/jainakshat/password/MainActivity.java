package in.jainakshat.password;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Resources mResources;
    private int mFabMargin;
    private boolean isCardAddItemOpened = false;

    private LinearLayout cardAddItem;
    private FloatingActionButton fab;
    private RecyclerView cardList;
    private CardListAdapter cardListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mResources = getBaseContext().getResources();
        mFabMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, mResources.getDisplayMetrics());
        cardAddItem = (LinearLayout) findViewById(R.id.card_add_item);
        cardAddItem.setVisibility(View.GONE);
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
                    cardAddItem.setVisibility(View.GONE);
                    cardList.setVisibility(View.VISIBLE);
                    fab.setLayoutParams(paramsBottomEnd);
                    fab.setImageResource(R.drawable.plus);
                    isCardAddItemOpened = false;
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
        cardList = (RecyclerView) findViewById(R.id.card_list);
        cardList.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        cardListAdapter = new CardListAdapter(getBaseContext());
        ArrayList<String> items = new ArrayList<>();
        items.add("Gmail");
        items.add("Facebook");
        items.add("Linkedin");
        items.add("Reddit");
        items.add("Gmail");
        items.add("Facebook");
        items.add("Linkedin");
        items.add("Reddit");
        cardListAdapter.setItems(items);
        cardList.setAdapter(cardListAdapter);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
