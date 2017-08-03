package com.wq.demo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 折叠布局,只支持在里面装载一个控件,可以是各种layout
 */
public class FoldLayout extends FrameLayout {
    private static final String TAG = "FoldLayout";

    private int mNumOfFolds = 8;    // 需要折叠的段数
    private float mFoldSize;        // 每一段的尺寸(折叠前)
    private float mFoldedSize;      // 每一段的尺寸(折叠后)
    private float mFoldRate = 1f;   // 折叠前后尺寸比例
    private boolean isLeft = true;  // 折叠的方向是否是以左边为基准(左边不动,往右边变化)

    private Matrix[] mMatrix = new Matrix[mNumOfFolds];
    private Paint mShadowPaint;     // 设置渐变阴影
    private Matrix mShadowMatrix;
    private LinearGradient mShadowGradientShader;
    private Paint mSolidPaint;      // 设置纯色阴影
    private float mAlpha = 1f;   // 阴影的透明度

    private Bitmap mBitmap;         // 用于折叠变换
    private Canvas mCanvas = new Canvas();

    private void init() {
        for (int i = 0; i < mNumOfFolds; i++) {
            mMatrix[i] = new Matrix();
        }
        mSolidPaint = new Paint();
        mShadowPaint = new Paint();
        mShadowMatrix = new Matrix();
        this.setWillNotDraw(false);
    }

    public FoldLayout(Context context) {
        super(context);
        init();
    }

    public FoldLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 计算子空间大小,以及自身的大小
        View child = getChildAt(0);
        measureChild(child, widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(child.getMeasuredWidth(), child.getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);
        child.layout(0, 0, child.getMeasuredWidth(), getMeasuredHeight());
        mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);
        updateFold();
    }

    private void updateFold() {
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();

        mFoldSize = w / mNumOfFolds;
        mFoldedSize = mFoldSize * mFoldRate;

        Log.d(TAG, "view w:" + getMeasuredWidth() + ", mFoldSize:" + mFoldSize + ", mFoldedSize:" + mFoldedSize);
        // 设置阳面的渐变色
        // 起始位置(0 ,0),终点位置((1-mFoldRate), 0);起始颜色-黑色,结束颜色-透明;
        // CLAMP表示在规定范区域内,颜色按照规定颜色范围渐变,剩下区域的一直沿用与规定区域接壤的颜色
        // (1-mFoldRate)表示折叠的越多,渐变的阴影部分越多(沟越深,阴影越多)
        mShadowGradientShader = new LinearGradient(0, 0, 1 - mFoldRate, 0,
                Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP);
        // 设置颜色渐变过程的实际大小(0.8*w)
        mShadowMatrix.reset();
        mShadowMatrix.setScale(mFoldSize, 1);
        mShadowGradientShader.setLocalMatrix(mShadowMatrix);
        mShadowPaint.setStyle(Paint.Style.FILL);
        mShadowPaint.setShader(mShadowGradientShader);
        mShadowPaint.setAlpha((int) (mAlpha * 255 * (1 - mFoldRate)));

        // 设置阴面的纯色,没有渐变
        // mAlpha表示最弱透明度,根据折叠强弱动态调整透明度,越展开,阴影透明度越低
        mSolidPaint.setColor(Color.argb((int) (255 * mAlpha * (1 - mFoldRate)), 0, 0, 0));

        //纵轴减小的那个高度，用勾股定理计算下
        float depth = (float) Math.sqrt(mFoldSize * mFoldSize - mFoldedSize * mFoldedSize) / 2;
        // 原图的每一块，对应折叠后的每一块，方向为左上、右上、右下、左下
        // 横向折叠,从左往右,折叠前是矩形,折叠后是梯形,要分别获取各自的顶点坐标
        if (isLeft) {
            for (int i = 0; i < mNumOfFolds; i++) {
                mMatrix[i].reset();
                float[] src = {
                        mFoldSize * i, 0,     // 左上
                        mFoldSize * (i + 1), 0,     // 右上
                        mFoldSize * (i + 1), h,     // 右下
                        mFoldSize * i, h};    // 左下
                boolean isEven = (i % 2 == 0);
                float[] dst = {
                        mFoldedSize * i, isEven ? 0 : depth,
                        mFoldedSize * (i + 1), isEven ? depth : 0,
                        mFoldedSize * (i + 1), isEven ? (h - depth) : (h),
                        mFoldedSize * i, isEven ? (h) : (h - depth)};

                for (int j = 0; j < 8; j++) {
                    dst[j] = Math.round(dst[j]);
                }

                mMatrix[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
            }
        } else {
            for (int i = 0; i < mNumOfFolds; i++) {
                mMatrix[i].reset();
                float[] src = {
                        w - mFoldSize * (i + 1), 0,      // 左上
                        w - mFoldSize * i, 0,      // 右上
                        w - mFoldSize * i, h,      // 右下
                        w - mFoldSize * (i + 1), h};     // 左下
                boolean isEven = (i % 2 == 0);
                float[] dst = {
                        w - mFoldedSize * (i + 1), isEven ? 0 : depth,
                        w - mFoldedSize * i, isEven ? depth : 0,
                        w - mFoldedSize * i, isEven ? (h - depth) : (h),
                        w - mFoldedSize * (i + 1), isEven ? (h) : (h - depth)};

                for (int j = 0; j < 8; j++) {
                    dst[j] = Math.round(dst[j]);
                }
                mMatrix[i].setPolyToPoly(src, 0, dst, 0, src.length >> 1);
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mFoldRate == 0) {
            return;
        } else if (mFoldRate == 1) {
            super.dispatchDraw(canvas);
            return;
        }
        // 通过mCanvas的传入从而获取到Bitmap
        super.dispatchDraw(mCanvas);
        Log.d(TAG, "bitmap(" + mBitmap.getWidth() + ", " + mBitmap.getHeight() + ").");

        for (int i = 0; i < mNumOfFolds; i++) {
            canvas.save();
            canvas.concat(mMatrix[i]);
            // 控制显示的大小
            if (isLeft) {
                canvas.clipRect(mFoldSize * i, 0, mFoldSize * (i + 1), getHeight());

                canvas.drawBitmap(mBitmap, 0, 0, null);
                canvas.translate(mFoldSize * i, 0);
                if (i % 2 == 0) {
                    canvas.drawRect(0, 0, mFoldSize, getHeight(), mSolidPaint);
                } else {
                    canvas.drawRect(0, 0, mFoldSize, getHeight(), mShadowPaint);
                }
                canvas.restore();
            } else {
                canvas.clipRect(getWidth() - mFoldSize * (i + 1), 0, getWidth() - mFoldSize * i, getHeight());
                canvas.drawBitmap(mBitmap, 0, 0, null);
                canvas.translate(getWidth() - mFoldSize * (i + 1), 0);
                if (i % 2 == 0) {
                    canvas.drawRect(0, 0, mFoldSize, getHeight(), mSolidPaint);
                } else {
                    canvas.drawRect(0, 0, mFoldSize, getHeight(), mShadowPaint);
                }
                canvas.restore();
            }
        }
    }

    public void setFoldRate(float rate, boolean isLeft) {
        if (rate < 0) {
            rate = 0;
        } else if (rate > 1) {
            rate = 1;
        }
        mFoldRate = rate;
        this.isLeft = isLeft;
        updateFold();
        invalidate();
    }
}
