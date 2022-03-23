package notifier;

import java.time.Duration;
import java.util.Timer;

public interface IGameStateNotifier {
    void notifyNewGame(int row, int col);
    void notifyGameLost();
    void notifyGameWon();
    void notifyFlagCountChanged(int newFlagCount);
    void notifyTimeElapsedChanged(int time);
    void notifyOpened(int x, int y, int explosiveNeighbourCount);
    void notifyFlagged(int x, int y);
    void notifyUnflagged(int x, int y);
    void notifyExploded(int x, int y);
}
