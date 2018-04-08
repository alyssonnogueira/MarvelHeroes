package com.example.alysson.marvelcomics;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alysson.marvelcomics.heroFragment.OnListFragmentInteractionListener;
import com.example.alysson.marvelcomics.dummy.DummyContent.DummyItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type. MyheroRecyclerViewAdapter.ViewHolder
 */
public class MyheroRecyclerViewAdapter extends RecyclerView.Adapter<MyheroRecyclerViewAdapter.ViewHolder> {

    private List<Hero> mHeroes;
    private OnListFragmentInteractionListener mListener;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;
    private int lastVisibleItem, totalItemCount;
    private int visibleThreshold = 5;
    protected Context mContext;

    public MyheroRecyclerViewAdapter(Context context, List<Hero> items, OnListFragmentInteractionListener listener, RecyclerView recyclerView) {
        this.mHeroes = items;
        mListener = listener;
        this.mContext = context;
        if(recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mHeroes.get(position) != null ? 1 : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = null;
        if (viewType == 1){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_hero, parent, false);
            viewHolder = new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_hero, parent, false);
            viewHolder = new ProgressViewHolder(view);
        }
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_hero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (holder instanceof ViewHolder){
            holder.hero = mHeroes.get(position);
            try {
                if (mHeroes.get(position) != null) {
                    holder.mContentView.setText(mHeroes.get(position).getName());
                    Picasso.get().load(mHeroes.get(position).getThumbnail()).into(holder.mPerfilHero);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        } else {
            Log.d("Hero", "MyProgress");
            ((ProgressViewHolder)holder).progressBar.setIndeterminate(true);
        }
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.hero);
//                }
//            }
//        });
    }

    public void setLoad(){
        loading = false;
    }

    @Override
    public int getItemCount() {
        return mHeroes.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener){
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoaded(){
        loading = false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mContentView;
        public final ImageView mPerfilHero;
        public Hero hero;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mView.setOnClickListener(this);
            mContentView = (TextView) view.findViewById(R.id.content);
            mPerfilHero = (ImageView) view.findViewById(R.id.perfilHero);
            //http://www.blogdoselback.com.br/wp-content/uploads/2017/06/Marvel-Comics-800x420-710x373.jpg
        }

        @Override
        public void onClick(View view){
            //Toast.makeText(mContext, "Click", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(mContext, HeroPageActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("HERO", this.hero);
            mContext.startActivity(intent);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public class ProgressViewHolder extends ViewHolder{
        public ProgressBar progressBar;
        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
        }
    }
}
