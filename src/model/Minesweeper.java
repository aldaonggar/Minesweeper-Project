package model;

import model.AbstractMineSweeper;
import model.AbstractTile;
import model.Difficulty;

import java.util.Random;

public class Minesweeper extends AbstractMineSweeper {
    //protected int row, col, explosionCount;
    protected AbstractTile[][] world;
    protected boolean firstTile=true;

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
        //this.row = world.length;
        //this.col = world[0].length;
    }

    @Override
    public void open(int x, int y) {
        if(x >=0 && y>=0 && x<world.length && y<world[0].length){
            if(firstTile && !world[x][y].isFlagged()){
                deactivateFirstTileRule();
                if(getTile(x,y).isExplosive()){
                    world[x][y]= generateExplosiveTile();
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
    }
    @Override
    public void flag(int x, int y) {
        if (!getTile(x, y).isOpened()) {
            getTile(x, y).flag();
            viewNotifier.notifyFlagged(x, y);
        }
    }

    @Override
    public void unflag(int x, int y) {
            getTile(x,y).unflag();
            viewNotifier.notifyUnflagged(x,y);
    }

    @Override
    public void deactivateFirstTileRule() {
        this.firstTile=false;
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
        int minesaround=0;
        for(int i=0; i< world.length;i++){
            for(int w=0; w<world[0].length;w++){
                if(i==x && w==y-1 && world[i][w].isExplosive()){
                    minesaround++;
                }
                if(i==x && w==y+1 && world[i][w].isExplosive()){
                    minesaround++;
                }
                if(i==x-1 && w==y-1 && world[i][w].isExplosive()){
                    minesaround++;
                }
                if(i==x-1 && w==y && world[i][w].isExplosive()){
                    minesaround++;
                }
                if(i==x-1 && w==y+1 && world[i][w].isExplosive()){
                    minesaround++;
                }
                if(i==x+1 && w==y-1 && world[i][w].isExplosive()){
                    minesaround++;
                }
                if(i==x+1 && w==y && world[i][w].isExplosive()){
                    minesaround++;
                }
                if(i==x+1 && w==y+1 && world[i][w].isExplosive()){
                    minesaround++;
                }
            }
        }

        return minesaround;
    }
}
