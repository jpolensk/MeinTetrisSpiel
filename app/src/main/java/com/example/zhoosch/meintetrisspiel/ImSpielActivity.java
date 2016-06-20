package com.example.zhoosch.meintetrisspiel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

// erster Test für die Versionierung
public class ImSpielActivity extends AppCompatActivity implements Runnable {

  private static final String DOOF_GELAUFEN = "000123";
  private CustomDrawableView mCanvas;
  private Thread mLocalGameThread = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_im_spiel);

    mCanvas = (CustomDrawableView) findViewById(R.id.cvsImSpiel);

    if (mLocalGameThread == null) {
      mLocalGameThread = new Thread(this);
      mLocalGameThread.start();
    }

  }

  public void clearCanvas(View v) {
    mCanvas.clearCanvas();
  }

  @Override
  public void run() {
    // TODO: UND HIER STARTEN WIR! MIT EINEM TIMER!
    // von hier: http://stackoverflow.com/questions/5274619/investigation-of-optimal-sleep-time-calculation-in-game-loop
    final Semaphore mutexRefresh = new Semaphore(0);
    final Semaphore mutexRefreshing = new Semaphore(1);
    int refresh = 0;


    Timer timeRefresh = new Timer();
    timeRefresh.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        if (mutexRefreshing.tryAcquire()) {
          mutexRefreshing.release();
          mutexRefresh.release();
        }
      }
    }, 0, 1000 / 50); // 50 fps
    /// hier blieben wir dann
    Date startDate = new Date();
    while (true) {
      if (Thread.currentThread().isInterrupted()) {
        Log.d("ACHTUNG", "Thread wird beendet!");
        return;
      }
      try {
        mutexRefresh.acquire();
        mutexRefreshing.acquire();
      } catch (InterruptedException ie) {
        Log.e(DOOF_GELAUFEN, "run: gameloop", ie);
      }

      // refresh
      refresh += 1;


      if ((refresh = (refresh % 50)) == 0) {
        Date endDate = new Date();
        // folgende Ausgabe landet auf der logcat Konsole:
        System.out.println(String.valueOf(50.0 * 1000 / (endDate.getTime() - startDate.getTime())) + " fps.");
        startDate = new Date();
      }
      mutexRefreshing.release();
    }
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (mLocalGameThread.isAlive() && !mLocalGameThread.isInterrupted()) {
      mLocalGameThread.interrupt();
      Log.d("lifecycle", "onStop Thread interrupted");
    }
    Log.d("LIFECYCLE", "onStop invoked");
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (mLocalGameThread.isAlive() && !mLocalGameThread.isInterrupted()) {
      mLocalGameThread.interrupt();
      Log.d("lifecycle", "onPause Thread interrupted");
    }
    Log.d("LIFECYCLE", "onPause invoked");
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (!mLocalGameThread.isAlive()) {
      mLocalGameThread.run();
      Log.d("lifecycle", "onStart Thread gestartet");
    }
    Log.d("LIFECYCLE", "onStart invoked");
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (!mLocalGameThread.isAlive()) {
      mLocalGameThread.run();
      Log.d("lifecycle", "onResume Thread gestartet");
    }
    Log.d("LIFECYCLE", "onResume invoked");
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    if (!mLocalGameThread.isAlive()) {
      mLocalGameThread.run();
      Log.d("lifecycle", "onRestart Thread gestartet");
    }
    Log.d("LIFECYCLE", "onRestart invoked");
  }
}

