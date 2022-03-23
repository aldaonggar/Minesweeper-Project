package model;


import java.util.Random;
import java.util.Timer;

public class Minesweeper extends AbstractMineSweeper {
    //protected int row, col, explosionCount;
    protected AbstractTile[][] world;
    protected boolean firstTile=true;
    protected int flagCount = 0;

    @Override
    public int getWidth() {
        return world[0].length;
    }

    @Override
    public int getHeight() {
        return world.length;
    }

    @Override
    public void startNewGame(Difficulty level) {
        if (level.equals(Difficulty.EASY)) {
            startNewGame(8, 8, 10);
        }
        if (level.equals(Difficulty.MEDIUM)) {
            startNewGame(16, 16, 40);
        }
        if (level.equals(Difficulty.HARD)) {
            startNewGame(16, 30, 99);
        }
    }

    @Override
    public void startNewGame(int row, int col, int explosionCount) {
        //this.row = row;
        //this.col = col;
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
        viewNotifier.notifyNewGame(row, col);
    }

    @Override
    public int toggleFlag(int x, int y) {
        if (getTile(x, y).isFlagged()) {
            getTile(x, y).unflag();
            flagCount -= 1;
            viewNotifier.notifyUnflagged(x, y);
        } else {
            getTile(x, y).flag();
            flagCount += 1;
            viewNotifier.notifyFlagged(x, y);
        }
        return flagCount;
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
        //this.row = world.length;
        //this.col = world[0].length;
    }

    @Override
    public void open(int x, int y) {
        if(x >= 0 && y >= 0 && x < world.length && y < world[0].length){
            if(firstTile && !world[x][y].isFlagged()){
                deactivateFirstTileRule();
                if(getTile(x,y).isExplosive()){
                    world[x][y]= generateEmptyTile();
                }
                world[x][y].open();
                this.viewNotifier.notifyOpened(x,y,MinesAround(x,y));

            }
            else if(getTile(x,y).isExplosive() && !getTile(x,y).isFlagged()){
                getTile(x,y).open();
                this.viewNotifier.notifyExploded(x,y);
                this.viewNotifier.notifyGameLost();
            }
            else if(!getTile(x,y).isExplosive() && !getTile(x,y).isFlagged()){
                getTile(x,y).open();
                this.viewNotifier.notifyOpened(x,y,MinesAround(x,y));
            }
        }

        int isOpen = 0;
        for(int i=0; i< world.length;i++){
            for(int j=0; j<world[i].length;j++){
               if (world[i][j].isOpened())
                   isOpen++;
            }
        }
        if(isOpen == (world.length * world[0].length))
            viewNotifier.notifyGameWon();
    }
    @Override
    public void flag(int x, int y) {
        if (!getTile(x, y).isOpened())
            getTile(x, y).flag();
    }

    @Override
    public void unflag(int x, int y) {
        getTile(x,y).unflag();
    }

    @Override
    public void deactivateFirstTileRule() {
        this.firstTile = false;
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

    public int MinesAround(int x, int y) {
        int minesAround=0;
        for(int i=0; i< world.length;i++){
            for(int j=0; j<world[i].length;j++){
                if(i == y && j == x+1 && world[i][j].isExplosive())
                    minesAround++;

                if(i == y+1 && j == x && world[i][j].isExplosive())
                    minesAround++;

                if(i == y+1 && j == x+1 && world[i][j].isExplosive())
                    minesAround++;

                if(i == y && j == x-1 && world[i][j].isExplosive())
                    minesAround++;

                if(i == y-1 && j == x && world[i][j].isExplosive())
                    minesAround++;

                if(i == y-1 && j == x-1 && world[i][j].isExplosive())
                    minesAround++;

                if(i == y+1 && j == x-1 && world[i][j].isExplosive())
                    minesAround++;

                if(i == y-1 && j == x+1 && world[i][j].isExplosive())
                    minesAround++;
            }
        }
        return minesAround;
    }
}
