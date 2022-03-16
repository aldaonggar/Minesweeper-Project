package model;

import model.AbstractMineSweeper;
import model.AbstractTile;
import model.Difficulty;

import java.util.Random;

public class Minesweeper extends AbstractMineSweeper {
    protected int row, col, explosionCount;
    protected AbstractTile world[][];

    @Override
    public int getWidth() {
        return col;
    }

    @Override
    public int getHeight() {
        return row;
    }

    @Override
    public void startNewGame(Difficulty level) {
        if (level == Difficulty.EASY) {
            startNewGame(8, 8, 10);
        }
        if (level == Difficulty.MEDIUM) {
            startNewGame(16, 16, 40);
        }
        if (level == Difficulty.HARD) {
            startNewGame(16, 30, 99);
        }
    }

    @Override
    public void startNewGame(int row, int col, int explosionCount) {
        this.row = row;
        this.col = col;
        world = new AbstractTile[row][col];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                world[i][j] = generateEmptyTile();

        int minesPlaced = 0;
        Random random = new Random();
        while(minesPlaced < explosionCount) {
            int x = random.nextInt(col);
            int y = random.nextInt(row);
            if(!world[y][x].isExplosive()) {
                world[y][x] = generateExplosiveTile();
                minesPlaced++;
            }
        }
    }

    @Override
    public void toggleFlag(int x, int y) {
        if (getTile(x, y).isFlagged())
            getTile(x, y).unflag();
        else
            getTile(x, y).flag();
    }

    @Override
    public AbstractTile getTile(int x, int y) {
        if(x >= 0 && x < getWidth() && y >= 0 && y < getHeight())
            return world[y][x];
        return null;
    }

    @Override
    public void setWorld(AbstractTile[][] world) {
        this.world = world;
        this.row = world.length;
        this.col = world[0].length;
    }

    @Override
    public void open(int x, int y) {
       if(getTile(x, y) != null)
           getTile(x, y).open();
    }
    @Override
    public void flag(int x, int y) {
        getTile(x, y).flag();
    }

    @Override
    public void unflag(int x, int y) {
        getTile(x, y).unflag();
    }

    @Override
    public void deactivateFirstTileRule() {
    }

    @Override
    public AbstractTile generateEmptyTile() {
        AbstractTile emptyTile = new EmptyTile();
        return emptyTile;
    }

    @Override
    public AbstractTile generateExplosiveTile() {
        AbstractTile explosiveTile = new ExplosiveTile();
        return explosiveTile;
    }
}
