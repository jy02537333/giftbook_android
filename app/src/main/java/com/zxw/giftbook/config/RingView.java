package com.zxw.giftbook.config;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

public class RingView extends View {

	private static final String TAG = ">>>>>RingView<<<<<";

	/**
	 * 圆环宽度
	 */
	private final static int CIRCLE_WIDTH = 60;

	/**
	 *各个圆弧之间的间隔
	 */
	private final static int INTERVAL_WIDTH = 5;

	/**
	 * 外部折线的弯曲长度
	 */
	private final static int EXTERNAL_LINE_FIRST_LENGTH = 10;

	/**
	 * 外部折线的水平长度
	 */
	private final static int EXTERNAL_LINE_SECOND_LENGTH = 60;

	/**
	 * 外部小圆点的半径
	 */
	private final static int EXTERNAL_LINE_CIRCLE_RADIUS = 4;

	/**
	 *四分之一屏幕宽度
	 */
	private static int QUARTER_SCREEN_WIDTH;

	/**
	 * 画笔
	 */
	private Paint mPaint;

	/**
	 * 上下文引用
	 */
	private Context mContext;

	/**
	 *  每段弧形的度数
	 */
	public float mFirstSweepAngle;
	public float mSecondSweepAngle;
	public float mThirdSweepAngle;

	/**
	 * 对应颜色
	 */
	private int mColorFirst;
	private int mColorSecond;
	private int mColorThird;

	/**
	 * 文字，注意不包括单位“题”
	 */
	private String mText = "360";

	/**
	 *  文字大小
	 */
	private int mTextSize;

	public RingView(Context context) {

		this(context, null);
	}

	public RingView(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;

		mPaint = new Paint();

		// 消除锯齿
		mPaint.setAntiAlias(true);
	}

	/**
	 * 设置初始数据
	 * 
	 */
	public void setData(float mFirstSweepAngle, int mColorFirst,
			float mSecondSweepAngle, int mColorSecond, float mThirdSweepAngle,
			int mColorThird, String mText, int mTextSize) {
		this.mFirstSweepAngle = mFirstSweepAngle - INTERVAL_WIDTH < 0 ? mFirstSweepAngle
				: mFirstSweepAngle - INTERVAL_WIDTH;
		this.mSecondSweepAngle = mSecondSweepAngle - INTERVAL_WIDTH < 0 ? mSecondSweepAngle
				: mSecondSweepAngle - INTERVAL_WIDTH;
		this.mThirdSweepAngle = mThirdSweepAngle - INTERVAL_WIDTH < 0 ? mThirdSweepAngle
				: mThirdSweepAngle - INTERVAL_WIDTH;
		this.mText = mText;
		this.mTextSize = mTextSize;

		this.mColorFirst = mColorFirst;
		this.mColorSecond = mColorSecond;
		this.mColorThird = mColorThird;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		QUARTER_SCREEN_WIDTH = getWidth() / 4;

		//外部长方形
		RectF externalOval = new RectF(QUARTER_SCREEN_WIDTH,
				QUARTER_SCREEN_WIDTH, QUARTER_SCREEN_WIDTH * 3,
				QUARTER_SCREEN_WIDTH * 3);

		//内部长方形
		RectF innerOval = new RectF(QUARTER_SCREEN_WIDTH + CIRCLE_WIDTH,
				QUARTER_SCREEN_WIDTH + CIRCLE_WIDTH, QUARTER_SCREEN_WIDTH * 3
						- CIRCLE_WIDTH, QUARTER_SCREEN_WIDTH * 3 - CIRCLE_WIDTH);

		drawArc(0, mFirstSweepAngle, canvas, externalOval, innerOval, mPaint,
				mColorFirst, Color.WHITE);

		drawArc(mFirstSweepAngle + INTERVAL_WIDTH, mSecondSweepAngle, canvas,
				externalOval, innerOval, mPaint, mColorSecond, Color.WHITE);

		drawArc(mFirstSweepAngle + INTERVAL_WIDTH + mSecondSweepAngle
				+ INTERVAL_WIDTH, mThirdSweepAngle, canvas, externalOval,
				innerOval, mPaint, mColorThird, Color.WHITE);
		mPaint.setColor(Color.WHITE);
		mPaint.setAlpha(180);
		mPaint.setStyle(Paint.Style.FILL);
		canvas.drawCircle(QUARTER_SCREEN_WIDTH * 2, QUARTER_SCREEN_WIDTH * 2,
				QUARTER_SCREEN_WIDTH - CIRCLE_WIDTH + INTERVAL_WIDTH * 2,
				mPaint);

		if (!TextUtils.isEmpty(mText)) {
			mPaint.setColor(Color.BLACK);
			drawCenterText(canvas, mPaint, mText, mTextSize);
		}

		// 外部第一条线
		mPaint.setColor(mColorFirst);
		drawExternalLine(canvas, mFirstSweepAngle / 2, mPaint,"扶贫户："+
				(int) ((mFirstSweepAngle + INTERVAL_WIDTH) * 100 / 360) + "%");

		//外部第二条线
		mPaint.setColor(mColorSecond);
		drawExternalLine(canvas, mFirstSweepAngle + mSecondSweepAngle / 2
				+ INTERVAL_WIDTH, mPaint,
				"脱贫户："+
				(int) ((mSecondSweepAngle + INTERVAL_WIDTH) * 100 / 360) + "%");

		// 外部第三条线
		mPaint.setColor(mColorThird);
		drawExternalLine(canvas, mFirstSweepAngle + mSecondSweepAngle
				+ mThirdSweepAngle / 2 + INTERVAL_WIDTH * 2, mPaint,
				"其他："+(int) ((mThirdSweepAngle + INTERVAL_WIDTH) * 100 / 360) + "%");

		super.onDraw(canvas);
	}

	private void drawExternalLine(Canvas canvas, float angle, Paint paint,
			String percent) {

		float startX = 0;
		float startY = 0;
		float middleStopX = 0;
		float middleStopY = 0;
		float endStopX = 0;
		float endStopY = 0;

		// 标记外部文字的书写方向
		boolean isExternalTextToLeft = false;

		if (angle > 0) {

			startX = (float) (2 * QUARTER_SCREEN_WIDTH + QUARTER_SCREEN_WIDTH
					* Math.cos(angle / 180 * Math.PI));

			startY = (float) (2 * QUARTER_SCREEN_WIDTH + QUARTER_SCREEN_WIDTH
					* Math.sin(angle / 180 * Math.PI));

			if (angle < 90) {
				middleStopX = startX + EXTERNAL_LINE_FIRST_LENGTH;
				middleStopY = startY + EXTERNAL_LINE_FIRST_LENGTH;

				endStopX = middleStopX + EXTERNAL_LINE_SECOND_LENGTH;
				endStopY = middleStopY;

			} else if (angle == 90) {
				middleStopY = startY + EXTERNAL_LINE_FIRST_LENGTH;

				endStopX = middleStopX;
				endStopY = middleStopY + EXTERNAL_LINE_SECOND_LENGTH;
			} else if (angle > 90 && angle < 180) {

				middleStopX = startX - EXTERNAL_LINE_FIRST_LENGTH;
				middleStopY = startY + EXTERNAL_LINE_FIRST_LENGTH;

				endStopX = middleStopX - EXTERNAL_LINE_SECOND_LENGTH;
				endStopY = middleStopY;

				isExternalTextToLeft = true;
			} else if (angle == 180) {
				middleStopX = startX - EXTERNAL_LINE_FIRST_LENGTH;

				endStopX = middleStopX - EXTERNAL_LINE_SECOND_LENGTH;
				endStopY = middleStopY;
				isExternalTextToLeft = true;
			} else if (angle > 180 && angle < 270) {
				middleStopX = startX - EXTERNAL_LINE_FIRST_LENGTH;
				middleStopY = startY - EXTERNAL_LINE_FIRST_LENGTH;

				endStopX = middleStopX - EXTERNAL_LINE_SECOND_LENGTH;
				endStopY = middleStopY;
				isExternalTextToLeft = true;

			} else if (angle == 270) {
				middleStopY = startY - EXTERNAL_LINE_FIRST_LENGTH;

				endStopX = middleStopX;
				endStopY = middleStopY - EXTERNAL_LINE_SECOND_LENGTH;

			} else if (angle > 270 && angle < 360) {
				middleStopX = startX + EXTERNAL_LINE_FIRST_LENGTH;
				middleStopY = startY - EXTERNAL_LINE_FIRST_LENGTH;

				endStopX = middleStopX + EXTERNAL_LINE_SECOND_LENGTH;
				endStopY = middleStopY;

			} else if (angle == 360) {
				middleStopX = startX + EXTERNAL_LINE_FIRST_LENGTH;

				endStopX = middleStopX + EXTERNAL_LINE_SECOND_LENGTH;
				endStopY = middleStopY;
			}

			canvas.drawLine(startX, startY, middleStopX, middleStopY, paint);

			canvas.drawLine(middleStopX, middleStopY, endStopX, endStopY, paint);

			canvas.drawCircle(endStopX + EXTERNAL_LINE_CIRCLE_RADIUS, endStopY,
					EXTERNAL_LINE_CIRCLE_RADIUS, paint);

			drawExternalText(canvas, paint, endStopX
					+ EXTERNAL_LINE_CIRCLE_RADIUS * 4, endStopY, percent, 18,
					isExternalTextToLeft);
		}

	}

	private void drawExternalText(Canvas canvas, Paint paint, float x, float y,
			String text, int textSize, boolean isExternalTextToLeft) {

		// 字体设置
		paint.setTextSize(textSize);

		int textWidth = 0;

		if (isExternalTextToLeft) {
			textWidth = (int) paint.measureText(text)
					+ EXTERNAL_LINE_CIRCLE_RADIUS * 6;
		}

		// 字体居中
		canvas.drawText(text, x - textWidth, y, paint);

	}

	private void drawCenterText(Canvas canvas, Paint paint, String text,
			int textSize) {

		// 字体设置
		paint.setTextSize(textSize);

		//得到字符串的长度
		int topTextWidth = (int) paint.measureText(text);

		// 字体居中
		canvas.drawText(text, QUARTER_SCREEN_WIDTH * 2 - topTextWidth / 2,
				QUARTER_SCREEN_WIDTH * 2, paint);

		// 单位“题”
		String unit ="";
		int bottomTextWidth = (int) paint.measureText(unit);
		paint.setColor(Color.GRAY);
		canvas.drawText(unit, QUARTER_SCREEN_WIDTH * 2 - bottomTextWidth / 2,
				QUARTER_SCREEN_WIDTH * 2 + textSize, paint);
	}

	private void drawArc(float startAngle, float sweepAngle, Canvas canvas,
			RectF externalOval, RectF innerOval, Paint paint,
			int externalColor, int innerColor) {

		paint.setColor(externalColor);
		paint.setStyle(Paint.Style.FILL);

		canvas.drawArc(externalOval, startAngle, sweepAngle, true, mPaint);

		paint.setColor(innerColor);
		paint.setStyle(Paint.Style.FILL);

		canvas.drawArc(innerOval, startAngle, sweepAngle, true, mPaint);
	}

}
