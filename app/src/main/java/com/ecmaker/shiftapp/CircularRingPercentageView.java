package com.ecmaker.shiftapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Matrix;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class CircularRingPercentageView extends View {
    private Paint paint;
    private int circleWidth;
    private int roundBackgroundColor;
    private int textColor;
    private float textSize;
    private float roundWidth;
    private float progress = 0;
    private int[] colors = {0xffff4639, 0xffCDD513, 0xff3CDF5F};
    private int radius;
    private RectF oval;
    private Paint mPaintText;
    private int maxColorNumber = 100;
    private float singlPoint = 9;
    private float lineWidth = 0.3f;
    private int circleCenter;
    private SweepGradient sweepGradient;
    private boolean isLine;
    private String textData;

    /**
     * 分割的數量
     *
     * @param textData 中間文字
     */
    public void setTextData(String textData) {
        this.textData = textData;
        invalidate();
    }

    /**
     * 分割的數量
     *
     * @param maxColorNumber 數量
     */
    public void setMaxColorNumber(int maxColorNumber) {
        this.maxColorNumber = maxColorNumber;
        singlPoint = (float) 360 / (float) maxColorNumber;
        invalidate();
    }

    /**
     * 是否是線條
     *
     * @param line true 是 false否
     */
    public void setLine(boolean line) {
        isLine = line;
        invalidate();
    }

    public int getCircleWidth() {
        return circleWidth;
    }

    public CircularRingPercentageView(Context context) {
        this(context, null);
    }

    public CircularRingPercentageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CircularRingPercentageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularRing);
        maxColorNumber = mTypedArray.getInt(R.styleable.CircularRing_circleNumber, 40);
        circleWidth = mTypedArray.getDimensionPixelOffset(R.styleable.CircularRing_circleWidth, getDpValue(180));
        roundBackgroundColor = mTypedArray.getColor(R.styleable.CircularRing_roundColor, 0xffdddddd);
        textColor = mTypedArray.getColor(R.styleable.CircularRing_circleTextColor, 0xff999999);
        roundWidth = mTypedArray.getDimension(R.styleable.CircularRing_circleRoundWidth, 40);
        textSize = mTypedArray.getDimension(R.styleable.CircularRing_circleTextSize, getDpValue(8));
        colors[0] = mTypedArray.getColor(R.styleable.CircularRing_circleColor1, 0xffff4639);
        colors[1] = mTypedArray.getColor(R.styleable.CircularRing_circleColor2, 0xffcdd513);
        colors[2] = mTypedArray.getColor(R.styleable.CircularRing_circleColor3, 0xff3cdf5f);
        initView();
        mTypedArray.recycle();
    }


    /**
     * 空白出顏色背景
     *
     * @param roundBackgroundColor
     */
    public void setRoundBackgroundColor(int roundBackgroundColor) {
        this.roundBackgroundColor = roundBackgroundColor;
        paint.setColor(roundBackgroundColor);
        invalidate();
    }

    /**
     * 刻度字型顏色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        mPaintText.setColor(textColor);
        invalidate();
    }

    /**
     * 刻度字型大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
        mPaintText.setTextSize(textSize);
        invalidate();
    }

    /**
     * 漸變顏色
     *
     * @param colors
     */
    public void setColors(int[] colors) {
        if (colors.length < 2) {
            throw new IllegalArgumentException("colors length < 2");
        }
        this.colors = colors;
        sweepGradientInit();
        invalidate();
    }


    /**
     * 間隔角度大小
     *
     * @param lineWidth
     */
    public void setLineWidth(float lineWidth) {
        this.lineWidth = lineWidth;
        invalidate();
    }


    private int getDpValue(int w) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, w, getContext().getResources().getDisplayMetrics());
    }

    /**
     * 圓環寬度
     *
     * @param roundWidth 寬度
     */
    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
        if (roundWidth > circleCenter) {
            this.roundWidth = circleCenter;
        }
        radius = (int) (circleCenter - this.roundWidth / 2); // 圓環的半徑
        oval.left = circleCenter - radius;
        oval.right = circleCenter + radius;
        oval.bottom = circleCenter + radius;
        oval.top = circleCenter - radius;
        paint.setStrokeWidth(this.roundWidth);
        invalidate();
    }

    /**
     * 圓環的直徑
     *
     * @param circleWidth 直徑
     */
    public void setCircleWidth(int circleWidth) {
        this.circleWidth = circleWidth;
        circleCenter = circleWidth / 2;

        if (roundWidth > circleCenter) {
            roundWidth = circleCenter;
        }
        setRoundWidth(roundWidth);
        sweepGradient = new SweepGradient(this.circleWidth / 2, this.circleWidth / 2, colors, null);
        //旋轉 不然是從0度開始漸變
        Matrix matrix = new Matrix();
        matrix.setRotate(-90, this.circleWidth / 2, this.circleWidth / 2);
        sweepGradient.setLocalMatrix(matrix);
    }

    /**
     * 漸變初始化
     */
    public void sweepGradientInit() {
        //漸變顏色
        sweepGradient = new SweepGradient(this.circleWidth / 2, this.circleWidth / 2, colors, null);
        //旋轉 不然是從0度開始漸變
        Matrix matrix = new Matrix();
        matrix.setRotate(-90, this.circleWidth / 2, this.circleWidth / 2);
        sweepGradient.setLocalMatrix(matrix);
    }

    public void initView() {

        circleCenter = circleWidth / 2;//半徑
        singlPoint = (float) 360 / (float) maxColorNumber;
        radius = (int) (circleCenter - roundWidth / 2); // 圓環的半徑
        sweepGradientInit();
        mPaintText = new Paint();
        mPaintText.setColor(textColor);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setTextSize(textSize);
        mPaintText.setAntiAlias(true);

        paint = new Paint();
        paint.setColor(roundBackgroundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);

        // 用於定義的圓弧的形狀和大小的界限
        oval = new RectF(circleCenter - radius, circleCenter - radius, circleCenter + radius, circleCenter + radius);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //背景漸變顏色
        paint.setShader(sweepGradient);
        canvas.drawArc(oval, -90, (float) (progress * 3.6), false, paint);
        paint.setShader(null);

        //是否是線條模式
        if (!isLine) {
            float start = -90f;
            float p = ((float) maxColorNumber / (float) 100);
            p = (int) (progress * p);
            for (int i = 0; i < p; i++) {
                paint.setColor(roundBackgroundColor);
                canvas.drawArc(oval, start + singlPoint - lineWidth, lineWidth, false, paint); // 繪製間隔快
                start = (start + singlPoint);
            }
        }
        //繪製剩下的空白區域
        paint.setColor(roundBackgroundColor);
        canvas.drawArc(oval, -90, (float) (-(100 - progress) * 3.6), false, paint);



        float result = (progress * 100.0f / 100 * 1.0f); // 計算進度
        String percent = String.format("%.1f", result) + "%";
        if(textData != null && textData.length() > 0){
            percent = textData;
        }

        mPaintText.setTextAlign(Paint.Align.CENTER); // 設定文字居中，文字的x座標要注意
        mPaintText.setColor(Color.BLACK); // 設定文字顏色
        mPaintText.setTextSize(40); // 設定要繪製的文字大小
        mPaintText.setStrokeWidth(0); // 注意此處一定要重新設定寬度為0,否則繪製的文字會重疊
        Rect bounds = new Rect(); // 文字邊框
        mPaintText.getTextBounds(percent, 0, percent.length(), bounds); // 獲得繪製文字的邊界矩形
        Paint.FontMetricsInt fontMetrics = mPaintText.getFontMetricsInt(); // 獲取繪製Text時的四條線
        int baseline = circleCenter + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        canvas.drawText(percent, circleCenter, baseline, mPaintText); // 繪製表示進度的文字
        //繪製文字刻度
        //for (int i = 1; i <= 10; i++) {
        //    canvas.save();// 儲存當前畫布
        //    canvas.rotate(360 / 10 * i, circleCenter, circleCenter);
        //    canvas.drawText(i * 10 + "", circleCenter, circleCenter - radius + roundWidth / 2 + getDpValue(4) + textSize, mPaintText);
        //    canvas.restore();//
        //}
    }


    OnProgressScore onProgressScore;

    public interface OnProgressScore {
        void setProgressScore(float score);

    }

    public synchronized void setProgress(final float p) {
        progress = p;
        postInvalidate();
    }

    /**
     * @param p
     */
    public synchronized void setProgress(final float p, OnProgressScore onProgressScore) {
        this.onProgressScore = onProgressScore;
        progress = p;
        postInvalidate();
    }
}
