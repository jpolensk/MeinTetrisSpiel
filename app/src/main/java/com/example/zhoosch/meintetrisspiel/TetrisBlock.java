package com.example.zhoosch.meintetrisspiel;

import java.util.Random;

/**
 * Created by zhoosch on 27.06.2016.
 */
public class TetrisBlock {
  /**
   * 1.)  2.)  3.)  4.)  5.)  6.)  7.)
   * ##   #    #    #   #     #   #
   * ##   #    ##  ##   #     #   ##
   * #     #  #    ##   ##   #
   * #
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
  private int mColor;

  // Random Block
  public TetrisBlock() {
    this(0);
  }

  public TetrisBlock(int iColor) {
    Random rd = new Random();
    this.setColor(rd.nextInt(iColor) + 1);
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

  public int getColor() {
    return (mColor);
  }

  public void setColor(int iColor) {
    mColor = iColor;
    return;
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

