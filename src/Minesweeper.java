import model.AbstractMineSweeper;
import model.AbstractTile;
import model.Difficulty;

import java.util.Random;

public class Minesweeper extends AbstractMineSweeper {
    private int row, col, explosionCount;
    private AbstractTile world[][];

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
        this.explosionCount = explosionCount;
        world = new AbstractTile[row][col];
        setWorld(world);
    }

    @Override
    public void toggleFlag(int x, int y) {

    }

    @Override
    public AbstractTile getTile(int x, int y) {
        return world[y][x];
    }

    @Override
    public void setWorld(AbstractTile[][] world) {

        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++)
                world[i][j] = generateEmptyTile();

        int minesPlaced = 0;
        Random random = new Random();
        while(minesPlaced < explosionCount) {
            int x = random.nextInt(col); // a number between 0 and col - 1
            int y = random.nextInt(row);
            // make sure we don't place a mine on top of another
            if(!world[y][x].isExplosive()) {
                world[y][x] = generateExplosiveTile();
                minesPlaced ++;
            }
        }
    }

    @Override
    public void open(int x, int y) {

    }

    @Override
    public void flag(int x, int y) {

    }

    @Override
    public void unflag(int x, int y) {

    }

    @Override
    public void deactivateFirstTileRule() {

    }

    @Override
    public AbstractTile generateEmptyTile() {
        AbstractTile tile = new AbstractTile() {
            @Override
            public boolean open() {
                return false;
            }

            @Override
            public void flag() {

            }

            @Override
            public void unflag() {

            }

            @Override
            public boolean isFlagged() {
                return false;
            }

            @Override
            public boolean isOpened() {
                return false;
            }

            @Override
            public boolean isExplosive() {
                return false;
            }
        };
        return tile;
    }

    @Override
    public AbstractTile generateExplosiveTile() {
        AbstractTile tile = new AbstractTile() {
            @Override
            public boolean open() {
                return false;
            }

            @Override
            public void flag() {

            }

            @Override
            public void unflag() {

            }

            @Override
            public boolean isFlagged() {
                return false;
            }

            @Override
            public boolean isOpened() {
                return false;
            }

            @Override
            public boolean isExplosive() {
                return true;
            }
        };
        return null;
    }
}
