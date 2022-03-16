package model;

import model.AbstractTile;

public class Tile extends AbstractTile {
    protected Boolean isFlagged = false;
    protected Boolean isExplosive;
    protected Boolean isOpened;

    @Override
    public boolean open() {
        this.isOpened = true;
        return this.isOpened;
    }

    @Override
    public void flag() {
        this.isFlagged = true;
    }

    @Override
    public void unflag() {
        this.isFlagged = false;
    }

    @Override
    public boolean isFlagged() {
        return this.isFlagged;
    }

    @Override
    public boolean isExplosive() {
        return false;
    }

    @Override
    public boolean isOpened() {
        return this.isOpened;
    }

}
