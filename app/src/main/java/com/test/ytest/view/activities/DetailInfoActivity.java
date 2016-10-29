package com.test.ytest.view.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.test.ytest.R;
import com.test.ytest.view.fragments.DetailInfoFragment;

public class DetailInfoActivity extends AppCompatActivity {

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, DetailInfoActivity.class);
        intent.putExtra(DETAIL_URL, url);
        context.startActivity(intent);
    }

    private static final String DETAIL_URL = "DETAIL_URL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        handleIntent();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if(intent.hasExtra(DETAIL_URL)) {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.detail_info_fragment);
            if(fragment instanceof DetailInfoFragment) {
                ((DetailInfoFragment) fragment)
                        .setUrl(intent.getStringExtra(DetailInfoActivity.DETAIL_URL));
            }
        }
    }
}
