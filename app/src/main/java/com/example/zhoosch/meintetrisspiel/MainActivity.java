package com.example.zhoosch.meintetrisspiel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    initAll();
  }

  private void initAll() {
    // initialisieren der Listener
    Button cmdStartGame = (Button) findViewById(R.id.cmdStartGame);

    cmdStartGame.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        // TODO: Daten für das Spiel bereit stellen?
        Intent intent = new Intent(getBaseContext(), ImSpielActivity.class);
        startActivity(intent);
        // TODO: Daten vom letzten Spiel speichern?
      }
    });

    Button cmdShowScore = (Button) findViewById(R.id.cmdShowScore);
    cmdShowScore.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        // TODO: Hier cmdShowScore eine Datenbankanbindung scheint hier sinnvoll mit grafischer ausgabe
      }
    });

    Button cmdShowOptions = (Button) findViewById(R.id.cmdShowOptions);
    cmdShowOptions.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        // TODO: Hier cmdShowOptions Musik? Style? Templates? Schwierigkeitsgrad?
      }
    });

    Button cmdExit = (Button) findViewById(R.id.cmdExit);
    cmdExit.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        onExit();
      }
    });
  }

  public void onExit() {
    finish();
  }
}
