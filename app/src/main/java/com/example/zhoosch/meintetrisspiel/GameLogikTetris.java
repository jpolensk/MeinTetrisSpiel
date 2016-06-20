package com.example.zhoosch.meintetrisspiel;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by zhoosch on 06.06.2016.
 */
public class GameLogikTetris {
  private static final int GAME_FIELD_WIDTH = 20;
  private static final int GAME_FIELD_HEIGHT = 60;
  private CustomDrawableView mCdv;
  private TetrisBlock mTBInAction;
  private TetrisBlock mTBNextOne;
  private int[][] mGameField;

  GameLogikTetris(CustomDrawableView cdv) {
    mCdv = cdv;
    mGameField = new int[GAME_FIELD_WIDTH][GAME_FIELD_HEIGHT];
  }

  private TetrisBlock newTetrisBlock() {
    boolean[] bKillLines = checkLines4Kill();
    runPhysikAfterClean(bKillLines);
    return (new TetrisBlock());
  }

  private void runPhysikForTetrisBlock() {
    // TODO den Block nach unten bewegen bzw. den nächsten Spielabschnitt vorbereiten oder freigeben
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

  class TetrisBlock {
    /**
     * 1.)  2.)  3.)  4.)  5.)  6.)  7.)
     * ##   #    #    #   #     #   #
     * ##   #    ##  ##   #     #   ##
     *      #     #  #    ##   ##   #
     *      #
     */
    private final boolean KIND_OF_BLOCK[][][] = {
            {{true, true}, {true, true}},
            {{true}, {true}, {true}, {true}},
            {{true, false}, {true, true}, {false, true}},
            {{false, true}, {true, true}, {true, false}},
            {{true, false}, {true, false}, {true, true}},
            {{false, true}, {false, true}, {true, true}},
            {{true, false}, {true, true}, {true, false}}};

    private boolean[][] mElement;
    private int mBlockID;
    private Color mColor;

    // Random Block
    TetrisBlock() {
      Random rd = new Random();
      mBlockID = rd.nextInt(7);
      mElement = KIND_OF_BLOCK[mBlockID].clone();

      // TODO: eine Farbe generieren momentan alles Schwarz default
    }

    public int getHeight() {
      return (mElement.length);
    }

    public int getWidth() {
      return (mElement[0].length);
    }

    public Color getColor() {
      return (mColor);
    }

    public void rotateLeft() {
      // TODO: prüfen ob links auch links rum dreht
      boolean[][] tempElement = new boolean[mElement[0].length][mElement.length];
      for (int x = 0; x < mElement.length; x++) {
        for (int y = 0; y < mElement[0].length; y++) {
          tempElement[(tempElement.length - 1) - y][x] = mElement[x][y];
        }
      }
      mElement = tempElement;
    }

    public void rotateRight() {
      // TODO: prüfen ob rechts auch rechts rum dreht
      boolean[][] tempElement = new boolean[mElement[0].length][mElement.length];
      for (int x = 0; x < mElement.length; x++) {
        for (int y = 0; y < mElement[0].length; y++) {
          tempElement[y][(mElement[0].length - 1) - x] = mElement[x][y];
        }
      }
      mElement = tempElement;
    }

    public boolean[][] getBlock() {
      return (mElement);
    }

    public boolean[][] get4x4Block() {
      boolean[][] retBlock = {
              {false, false, false, false},
              {false, false, false, false},
              {false, false, false, false},
              {false, false, false, false}};

      int oX = (mElement[0].length < 3) ? 1 : 0;
      int oY = (mElement.length < 3) ? 1 : 0;
      for (int x = 0; x < mElement[0].length; x++) {
        for (int y = 0; y < mElement.length; y++) {
          retBlock[x + oX][y + oY] = mElement[x][y];
        }
      }
      return (retBlock);
    }
  }
}


