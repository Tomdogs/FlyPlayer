package com.bat.tomdog.flyplayer.ijkplayer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bat.tomdog.flyplayer.R;
import com.bat.tomdog.flyplayer.widget.player.FlyPlayer;


public class FlyPlayerFragment extends Fragment {
    private static final String TAG = "FlyPlayerFragment";
    private static final String ARG_PARAM1 = "url";
    private static final String ARG_PARAM2 = "name";

    // TODO: Rename and change types of parameters
    private String url;
    private String name;

    public FlyPlayer flyPlayer;

    public FlyPlayerFragment() {
        // Required empty public constructor
    }

    public static FlyPlayerFragment newInstance(String url, String name) {
        FlyPlayerFragment fragment = new FlyPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, url);
        args.putString(ARG_PARAM2, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(ARG_PARAM1);
            name = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate_view = inflater.inflate(R.layout.fly_player, container, false);
        flyPlayer = new FlyPlayer(getActivity(),inflate_view);
        flyPlayer.play(url);
        flyPlayer.setTitle(name);
        return inflate_view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(flyPlayer != null){
            flyPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(flyPlayer != null){
            flyPlayer.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(flyPlayer != null){
            flyPlayer.onDestroy();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i(TAG,"onConfigurationChanged:");
        if(flyPlayer != null){
            flyPlayer.onConfigurationChanged(newConfig);
        }
    }

}
