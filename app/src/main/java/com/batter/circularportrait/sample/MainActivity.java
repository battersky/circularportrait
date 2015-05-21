package com.batter.circularportrait.sample;

import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.batter.circularportrait.CircularPortraitDrawable;
import com.batter.circularportrait.CircularPortraitImageView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @ViewById(R.id.image)
    ImageView mImageView;

    @ViewById(R.id.bigview)
    CircularPortraitImageView mBigView;

    @AfterViews
    void updateImageView() {
        CircularPortraitDrawable drawable = new CircularPortraitDrawable(getResources(), R.drawable.th);
        mImageView.setImageDrawable(drawable);
        mBigView.setImageDrawable(drawable);
    }
}
