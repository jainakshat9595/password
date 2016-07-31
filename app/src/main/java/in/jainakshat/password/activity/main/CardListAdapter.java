package in.jainakshat.password.activity.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.jainakshat.password.activity.detail.DetailActivity;
import in.jainakshat.password.R;
import in.jainakshat.password.model.Entity;

/**
 * Created by Akshat on 5/25/2016.
 */
public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardListViewHolder> {

    private Context mContext;
    private ArrayList<Entity> mItems;

    public CardListAdapter(Context context) { this.mContext = context;}

    public class CardListViewHolder extends RecyclerView.ViewHolder {

        private TextView cardListTitle;
        private FrameLayout cardListBackground;

        public CardListViewHolder(View itemView) {
            super(itemView);
            Typeface karlaFont = Typeface.createFromAsset(mContext.getAssets(), "Karla-Regular.ttf");
            cardListTitle = (TextView) itemView.findViewById(R.id.card_list_title);
            cardListBackground = (FrameLayout) itemView.findViewById(R.id.card_list_background);
            cardListTitle.setTypeface(karlaFont);
        }
    }

    @Override
    public CardListAdapter.CardListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_list_item, parent, false);
        return new CardListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CardListAdapter.CardListViewHolder holder, final int position) {
        holder.cardListTitle.setText(mItems.get(position).getName());
        holder.cardListBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("entity", mItems.get(position));
                mContext.startActivity(intent);
            }
        });
        holder.cardListBackground.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                StringBuilder pass = new StringBuilder(mItems.get(position).getPassword());
                for(int i=1; i<+pass.length(); i+=2) {
                    pass.setCharAt(i, '_');
                }
                Snackbar.make(holder.itemView, pass, Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setItems(ArrayList<Entity> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }
}
