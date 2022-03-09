import model.AbstractTile;

public class Tile extends AbstractTile {
    private Boolean isFlagged;
    private Boolean isExplosive;
    private Boolean isOpened;

    @Override
    public boolean open() {
        isOpened = true;
        return isOpened;
    }

    @Override
    public void flag() {
        isFlagged = true;
    }

    @Override
    public void unflag() {
        isFlagged = false;
    }

    @Override
    public boolean isFlagged() {
        return isFlagged;
    }

    @Override
    public boolean isExplosive() {
        return isExplosive;
    }

    @Override
    public boolean isOpened() {
        return isOpened;
    }
}
