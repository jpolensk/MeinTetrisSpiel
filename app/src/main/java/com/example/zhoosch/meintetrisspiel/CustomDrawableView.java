package com.example.zhoosch.meintetrisspiel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zhoosch on 23.05.2016.
 *
 * Diese Klasse übernimmt alles was mit dem Zeichnen zu tun hat.
 */
public class CustomDrawableView extends View {
  private static final float TOLLERANCE = 5;
  // Membervariablen / Attribute
  public int mWidth;
  public int mHeight;
  private Bitmap mBitmap;
  private Canvas mCanvas;
  private Path mPath; // Testzweck
  private Context mContext;
  private Paint mPaint;
  private float mx, my; // Moves

  public CustomDrawableView(Context context, AttributeSet attributset) {
    super(context, attributset);
    mContext = context;

    // starten eines Neuen Pfades // für die Zeichnerei TEST!!!
    mPath = new Path();

    // Initialisierung der Stifte
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setColor(Color.BLACK);
    mPaint.setStyle(Paint.Style.STROKE);
    mPaint.setStrokeJoin(Paint.Join.ROUND);
    mPaint.setStrokeWidth(4.0f);

    // TODO: es werden defintiv noch mehr Farben benötigt
  }

  @Override
  protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
    super.onSizeChanged(width, height, oldWidth, oldHeight);
    mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    mCanvas = new Canvas(mBitmap);
    // TODO:
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);

    canvas.drawPath(mPath, mPaint);
    canvas.drawRect(16, 15, 200, 600, mPaint);
  }

  private void startTouch(float x, float y) {
    mPath.moveTo(x, y);
    mx = x;
    my = y;
  }

  private void moveTouch(float x, float y) {
    float dx = Math.abs(x - mx);
    float dy = Math.abs(y - my);
    if (dx >= TOLLERANCE || dy >= TOLLERANCE) {
      mPath.quadTo(mx, my, (x + mx) / 2, (y + my) / 2);
      mx = x;
      my = y;
    }
  }

  public void clearCanvas() {
    mPath.reset();
    invalidate();
  }

  private void upTouch() {
    mPath.lineTo(mx, my);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX();
    float y = event.getY();

    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        startTouch(x, y);
        break;
      case MotionEvent.ACTION_MOVE:
        moveTouch(x, y);
        break;
      case MotionEvent.ACTION_UP:
        upTouch();
        break;
    }
    invalidate();
    return true;
  }
}
