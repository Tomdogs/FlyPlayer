package com.bat.tomdog.flyplayer.ijkplayer;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bat.tomdog.flyplayer.R;
import com.bat.tomdog.flyplayer.util.Settings;
import com.bat.tomdog.flyplayer.widget.media.AndroidMediaController;
import com.bat.tomdog.flyplayer.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.player.misc.ITrackInfo;


public class IjkplayerFragment extends Fragment implements TracksFragment.ITrackHolder{

    private static final String TAG = "IjkplayerFragment";

    private String mVideoPath;
    private Uri mVideoUri;

    public AndroidMediaController mMediaController;
    private IjkVideoView mVideoView;
    private TextView mToastTextView;
    private TableLayout mHudView;//屏幕上参数
    private DrawerLayout mDrawerLayout;
    private ViewGroup mRightDrawer;

    private Settings mSettings;
    private boolean mBackPressed;


    private static final String VIDEOPATH = "videoPath";
    private static final String VIDEOTITLE = "videoTitle";

    private String videoPath;
    private String videoTitle;


    public IjkplayerFragment() {
        // Required empty public constructor
    }

    public static IjkplayerFragment newInstance(String videoPath, String videoTitle) {
        IjkplayerFragment fragment = new IjkplayerFragment();
        Bundle args = new Bundle();
        args.putString(VIDEOPATH, videoPath);
        args.putString(VIDEOTITLE, videoTitle);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoPath = getArguments().getString(VIDEOPATH);
            videoTitle = getArguments().getString(VIDEOTITLE);

            mVideoPath = videoPath;
            Log.i(TAG,"接收到的路径："+mVideoPath);
        }

        mMediaController = new AndroidMediaController(getActivity());
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ijkplayer, container, false);
        mVideoView =view.findViewById(R.id.video_view);
        mVideoView.setMediaController(mMediaController);
//        mVideoView.setHudView(mHudView);
        // prefer mVideoPath
        if (mVideoPath != null)
            mVideoView.setVideoPath(mVideoPath);
        else if (mVideoUri != null)
            mVideoView.setVideoURI(mVideoUri);
        else {
            Log.e(TAG, "Null Data Source\n");
        }
        mVideoView.start();
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause 执行了");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.i(TAG,"setUserVisibleHint 执行了:"+isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG,"onHiddenChanged 执行了:"+hidden);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"onStop 执行了");
        if (mBackPressed || !mVideoView.isBackgroundPlayEnabled()) {
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopBackgroundPlay();
        } else {
            mVideoView.enterBackground();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    public ITrackInfo[] getTrackInfo() {
        if (mVideoView == null)
            return null;

        return mVideoView.getTrackInfo();
    }

    @Override
    public int getSelectedTrack(int trackType) {
        if (mVideoView == null)
            return -1;

        return mVideoView.getSelectedTrack(trackType);
    }

    @Override
    public void selectTrack(int stream) {
        mVideoView.selectTrack(stream);
    }

    @Override
    public void deselectTrack(int stream) {
        mVideoView.deselectTrack(stream);
    }
}
