package com.example.zhoosch.meintetrisspiel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

// erster TEst für die Versionierung
public class ImSpielActivity extends AppCompatActivity {

  CustomDrawableView mCustomDrawableView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mCustomDrawableView = new CustomDrawableView(this);

    setContentView(R.layout.activity_im_spiel);
  }
}

