package com.bat.tomdog.flyplayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bat.tomdog.flyplayer.ijkplayer.FlyPlayerFragment;

public class MainActivity extends AppCompatActivity {

    //CCTV1
    private String url = "http://ivi.bupt.edu.cn/hls/cctv1hd.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment vedioFragment = FlyPlayerFragment.newInstance(url,"在线视频");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.body,vedioFragment);
        transaction.commit();
    }
}
