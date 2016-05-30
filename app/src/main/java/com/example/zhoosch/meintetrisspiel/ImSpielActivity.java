package com.example.zhoosch.meintetrisspiel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

// erster TEst für die Versionierung
public class ImSpielActivity extends AppCompatActivity {

  private CustomDrawableView mCanvas;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_im_spiel);

    mCanvas = (CustomDrawableView) findViewById(R.id.cvsImSpiel);
  }

  public void clearCanvas(View v) {
    mCanvas.clearCanvas();
  }
}

