package com.example.alysson.marvelcomics;

import android.content.Context;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.alysson.marvelcomics.dummy.DummyContent;
import com.example.alysson.marvelcomics.dummy.DummyContent.DummyItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class heroFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Hero> mHeroes;
    private Handler handler;
    private final HeroService heroService = new HeroService();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public heroFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static heroFragment newInstance(int columnCount) {
        heroFragment fragment = new heroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hero_list, container, false);
        final View view2 = view;
        handler = new Handler();
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mHeroes = ((MainActivity) getActivity()).getHeroes();
            heroService.setLastID(mHeroes.get(mHeroes.size()-1).getId());
            heroService.setLimitID(((MainActivity) getActivity()).getLimitID());
            final MyheroRecyclerViewAdapter myheroRecyclerViewAdapter = new MyheroRecyclerViewAdapter(this.getContext(), mHeroes, mListener, recyclerView);
            recyclerView.setAdapter(myheroRecyclerViewAdapter);
            myheroRecyclerViewAdapter.setOnLoadMoreListener(new MyheroRecyclerViewAdapter.OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    //mHeroes.add(null);
                    //myheroRecyclerViewAdapter.notifyItemInserted(mHeroes.size()-1);
                    //handler.postDelayed
//                    Snackbar.make(view2, "Loading new characteres", Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                    Toast.makeText(getContext(), "Loading", Toast.LENGTH_LONG).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //mHeroes.remove(mHeroes.size()-1);
                            //myheroRecyclerViewAdapter.notifyItemRemoved(mHeroes.size());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        //heroService.getHeroes();
                                        if (heroService.getLastID() == 0) {
                                            mHeroes.addAll(heroService.getHeroes());
                                        }else{
                                            for (int i = 0; i < 10; i++) {
                                                mHeroes.addAll(heroService.getHeroes());
                                            }
                                        }
                                        //mHeroes.add(new Hero("99", "Thread", "Man"));
                                        myheroRecyclerViewAdapter.notifyItemInserted(mHeroes.size());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                            myheroRecyclerViewAdapter.setLoaded();
//                            for(int i = 0; i < 15; i++){
//                                mHeroes.add(new Hero(""+i, "Spider", "Man"));
                                //myheroRecyclerViewAdapter.notifyItemInserted(mHeroes.size());
                            //}
                            //myheroRecyclerViewAdapter.setLoaded();
                        }
                    }, 2000);
                    Log.d("heroFragment", "Load");
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private List<Hero> getFirstData(){
        List<Hero> heroList = new ArrayList<Hero>();
        for(int i = 0; i < 10; i++){
            heroList.add(new Hero(i, "Teste", "Teste"));
        }
        return heroList;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Hero item);
    }
}
