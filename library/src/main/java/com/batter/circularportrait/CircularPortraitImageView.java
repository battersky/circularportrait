package com.batter.circularportrait;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircularPortraitImageView extends ImageView {

    private int mBorderWidth = 0;
    private int mBorderColor = Color.BLACK;

    public CircularPortraitImageView(Context context) {
        super(context);
    }

    public CircularPortraitImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    public CircularPortraitImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CircularPortraitImageView, 0, 0);
        if (typedArray != null) {
            mBorderWidth = typedArray.
                    getDimensionPixelSize(R.styleable.CircularPortraitImageView_borderWidth, 0);

            mBorderColor = typedArray.getColor(R.styleable.CircularPortraitImageView_borderColor, Color.BLACK);
            typedArray.recycle();
        }
    }

    public void setBorderWidth(int width) {
        mBorderWidth = width;
    }

    public void setBorderColor(int color) {
        mBorderColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();

        if (drawable != null && drawable instanceof CircularPortraitDrawable) {
            CircularPortraitDrawable circularDrawable = (CircularPortraitDrawable) drawable;
            if (mBorderWidth > 0) {
                circularDrawable.setBorderWidth(mBorderWidth);
                circularDrawable.setBorderColor(mBorderColor);
            }
        }
        super.onDraw(canvas);
    }
}
