package com.example.zhoosch.meintetrisspiel;

import android.util.Log;

import java.util.Random;

/**
 * Created by zhoosch on 06.06.2016.
 *
 * -Das Spielfeld wird von MAX nach MIN bespielt das entspricht von Oben nach Unten.
 */
public class GameLogikTetris {
  private static final int GAME_FIELD_WIDTH = 20;
  private static final int GAME_FIELD_HEIGHT = 60;
  private static final int GAME_FIELD_COLORS = 4;
  private CustomDrawableView mCdv;
  private TetrisBlock mTBInAction;
  private TetrisBlock mTBNextOne;
  private int mLevel;
  private int[][] mGameField;
  private int[][] mGameFieldDraw;
  private int mBlockPosX;
  private int mBlockPosY;
  private int mBlockHeight;
  private int mBlockWidth;



  GameLogikTetris(CustomDrawableView cdv) {
    mCdv = cdv;
    mGameField = new int[GAME_FIELD_HEIGHT][GAME_FIELD_WIDTH];
    mGameFieldDraw = mGameField.clone();
  }

  private boolean startANewGame(int iLevel, int iFragmentedLines) {
    Log.d("Status:", "Ein neues Spiel wurde gestartet.");
    this.clearGameField();
    if (iFragmentedLines > 0) {
      Log.d("Status:", "Befülle Spielfeld mit " + iFragmentedLines + " Müllzeilen!");
      if (iFragmentedLines > (GAME_FIELD_HEIGHT / 2)) {
        Log.d("EINGABEFEHLER:", "Es ist nicht möglich, mehr als 50% des Spielfeldes mit Müllzeilen zu befüllen!");
        iFragmentedLines = GAME_FIELD_HEIGHT / 2;
      }
      Random rd = new Random();
      for (int i = 0; i < iFragmentedLines; i++) {
        for (int j = 0; j < GAME_FIELD_WIDTH; j++) {
          mGameField[i][j] = rd.nextInt(GAME_FIELD_COLORS) + 1;
        }
        mGameField[i][rd.nextInt(GAME_FIELD_WIDTH)] = 0;
      }
    }
    Log.d("Status:", "Setze Spiellevel auf: " + iLevel);
    mLevel = iLevel;

    Log.d("Status:", "Erstelle einen neuen Bock für NEXT.");
    mTBNextOne = newTetrisBlock();
    insertABlock();


    return true;
  }

  private boolean startANewGame() {
    return this.startANewGame(0, 0);
  }

  private boolean insertABlock() {
    Log.d("Status:", "Next Block wird aktueller Block!");
    mTBInAction = mTBNextOne;
    Log.d("Status:", "Erstelle einen neuen Bock für NEXT.");
    mTBNextOne = newTetrisBlock();
    Log.d("Status:", "Kopiere den aktuellen Block ins Spielfeld.");

    mBlockPosX = GAME_FIELD_WIDTH / 2 - 1;   // hat die Breite x deswegen -1
    mBlockPosY = GAME_FIELD_HEIGHT - 1;    // startet mit 0 deswegen -1
    mBlockHeight = mTBInAction.getHeight();
    mBlockWidth = mTBInAction.getWidth();

    for (int i = mBlockPosX; i < mBlockPosX + mBlockWidth; i++) {
      for (int j = mBlockPosY; j < mBlockPosY + mBlockHeight; j++) {
        // TODO: HIER BIN ICH!!!
      }
    }

    return true;
  }

  private TetrisBlock newTetrisBlock() {
    boolean[] bKillLines = checkLines4Kill();
    runPhysikAfterClean(bKillLines);
    return (new TetrisBlock());
  }

  private void runPhysikForTetrisBlock() {
    // TODO den Block nach unten bewegen bzw. den nächsten Spielabschnitt vorbereiten oder freigeben
    // begrenzen auf das modifizierte Feld und dessen größe 4x4 aktuell
  }

  private void runPhysikAfterClean(boolean[] bLines) {
    for (int y = 0, oY = 0; y < (GAME_FIELD_HEIGHT - (oY - y)); y++, oY++) {
      if (bLines[y]) {
        oY++;
      }
      for (int x = 0; x < GAME_FIELD_WIDTH; x++) {
        mGameField[y][x] = mGameField[oY][x];
        mGameField[oY][x] = 0;
      }
    }
  }

  private void blinkingLines(boolean[] bLines) {
    // TODO: blinken der zu löschenden Zeilen animieren
    // hier wird remote zur höheren Instanz kommunziert
    // kleine pause wegen animationen usw. rückkopplung in gamethread?
  }

  private boolean[] checkLines4Kill() {
    boolean[] bVecClean = new boolean[GAME_FIELD_HEIGHT];
    boolean bLastIsCleaned = false;
    boolean bClearLine;

    clkNewLine:
    for (int y = 0; y < GAME_FIELD_HEIGHT; y++) {
      bClearLine = true;
      for (int x = 0; x < GAME_FIELD_WIDTH; x++) {
        if (mGameField[y][x] == 0) {
          bClearLine = false;
          y = y % GAME_FIELD_WIDTH + GAME_FIELD_WIDTH;
          x = 0;
          continue clkNewLine;
        }
      }
      if (bClearLine) {
        bVecClean[y] = true;
      }
    }
    return (bVecClean);
  }

  private void clearLines() {

  }

  /**
   * Setzt das Spielfeld zurück und löscht alle Blöcke in diesem.
   *
   * @return true - wenn der Löschvorgang durchgeführt wurde sonst false.
   */
  private boolean clearGameField() {
    java.util.Arrays.fill(mGameField, false);
    if (java.util.Arrays.asList(mGameField).contains(true)) {
      Log.d("BUG:", "Das Spielfeld kann nicht gelöscht werden!");
      return false;
    }
    Log.d("Status:", "Das Spielfeld wurde gelöscht.");
    return true;
  }
}


