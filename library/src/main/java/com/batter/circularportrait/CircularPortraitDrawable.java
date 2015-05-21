package com.batter.circularportrait;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class CircularPortraitDrawable extends Drawable {
    private static final int DEFAULT_PAINT_FLAGS =
            Paint.FILTER_BITMAP_FLAG | Paint.DITHER_FLAG;

    private Paint mPaint = new Paint(DEFAULT_PAINT_FLAGS);
    private Paint mBorderPaint = new Paint(DEFAULT_PAINT_FLAGS);
    private String mResourcePath = null;
    private int mResourceId = 0;
    private int mOrignalBitMapWidth = 0;
    private int mOrignalBitMapHeight = 0;
    private Bitmap mDrawableBitMap = null;
    private Resources mResources = null;
    private BitmapFactory.Options mBitMapOptions = new BitmapFactory.Options();
    private int mBorderWidth = 0;
    private int mBorderColor = Color.BLACK;
    private int mInternalPadding = 10;

    public CircularPortraitDrawable(String resourcePath, Resources resources) {
        mResources = resources;
        mResourcePath = resourcePath;
        init();
    }

    public CircularPortraitDrawable(Resources resources, int resourceId) {
        mResources = resources;
        mResourceId = resourceId;
        init();
    }

    private void init() {
        mBitMapOptions.inJustDecodeBounds = true;
        if (mResourcePath != null) {
            BitmapFactory.decodeFile(mResourcePath, mBitMapOptions);
        } else if (mResources != null && mResourceId != 0) {
            BitmapFactory.decodeResource(mResources, mResourceId, mBitMapOptions);
        }

        float density = mResources.getDisplayMetrics().scaledDensity;

        mOrignalBitMapWidth = (int) (mBitMapOptions.outWidth * density);
        mOrignalBitMapHeight = (int) (mBitMapOptions.outHeight * density);

        mInternalPadding *= density;

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect drawRect = getBounds();
        if (mDrawableBitMap == null) {
            Bitmap bitmap = null;

            if (mResourcePath != null && mResources != null) {
                bitmap = BitmapFactory.decodeFile(mResourcePath);
            } else if (mResourceId != 0) {
                bitmap = BitmapFactory.decodeResource(mResources, mResourceId);
            }

            if (bitmap != null) {
                mDrawableBitMap = bitmap;
            }
        }

        BitmapShader shader;
        shader = new BitmapShader(mDrawableBitMap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(shader);

        canvas.drawCircle(drawRect.centerX(),
                drawRect.centerY(), drawRect.height() / 2 - mInternalPadding, mPaint);
        if (mBorderWidth > 0) {
            canvas.drawCircle(drawRect.centerX(),
                    drawRect.centerY(), drawRect.height() / 2 - mInternalPadding, mBorderPaint);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    public int getIntrinsicHeight() {
        return mOrignalBitMapHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return mOrignalBitMapWidth;
    }

    public void setBorderWidth(int width) {
        mBorderPaint.setStrokeWidth(width);
        mBorderWidth = width;
    }

    public void setBorderColor(int color) {
        mBorderPaint.setColor(color);
        mBorderColor = color;
    }

    private int getInternalPadding() {
        return mInternalPadding;
    }
}
