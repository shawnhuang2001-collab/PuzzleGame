
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MoveController implements KeyListener {

    private PuzzleData puzzleData;
    private PuzzlePanel puzzlePanel;
    private GamePanel gamePanel;

    // 保存空白格的位置
    private int x;
    private int y;

    // 保存当前步数
    private int steps;

    // 判断游戏是否完成
    private boolean gameWon;

    // 创建键盘控制器
    public MoveController(
            PuzzleData puzzleData,
            PuzzlePanel puzzlePanel,
            GamePanel gamePanel
    ) {

        this.puzzleData = puzzleData;
        this.puzzlePanel = puzzlePanel;
        this.gamePanel = gamePanel;

        findBlankPosition();
    }

    // 查找空白格的位置
    private void findBlankPosition() {

        String[][] data = puzzleData.getPuzzleData();

        for (int row = 0; row < data.length; row++) {
            for (int col = 0; col < data[row].length; col++) {

                if (data[row][col] == null) {
                    x = col;
                    y = row;
                    return;
                }
            }
        }
    }

    // 处理键盘按键
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // A 键：按住时显示完整原图
        if (code == KeyEvent.VK_A) {

            if (!gameWon) {
                puzzlePanel.showPuzzleImage();
            }

            return;
        }

        // W 键：完成拼图
        if (code == KeyEvent.VK_W) {

            puzzleData.solvePuzzle();

            x = 3;
            y = 3;

            puzzlePanel.refresh();

            checkWin();

            return;
        }

        // 游戏完成后禁止移动
        if (gameWon) {
            return;
        }

        boolean moved = false;

        // 方向键控制空白格移动
        if (code == KeyEvent.VK_UP && y < 3) {
            swap(x, y, x, y + 1);
            y++;
            moved = true;
        }

        if (code == KeyEvent.VK_DOWN && y > 0) {
            swap(x, y, x, y - 1);
            y--;
            moved = true;
        }

        if (code == KeyEvent.VK_LEFT && x < 3) {
            swap(x, y, x + 1, y);
            x++;
            moved = true;
        }

        if (code == KeyEvent.VK_RIGHT && x > 0) {
            swap(x, y, x - 1, y);
            x--;
            moved = true;
        }

        // 只有成功移动才增加步数
        if (moved) {

            steps++;

            gamePanel.updateSteps(steps);

            puzzlePanel.refresh();

            checkWin();
        }
    }

    // 交换两个拼图位置
    private void swap(
            int x1,
            int y1,
            int x2,
            int y2
    ) {

        String[][] data = puzzleData.getPuzzleData();

        String temp = data[y1][x1];
        data[y1][x1] = data[y2][x2];
        data[y2][x2] = temp;
    }

    // 检查拼图是否完成
    private void checkWin() {

        String[][] currentData =
                puzzleData.getPuzzleData();

        String[][] solutionData =
                puzzleData.getSolutionData();

        for (int row = 0; row < currentData.length; row++) {
            for (int col = 0; col < currentData[row].length; col++) {

                if (currentData[row][col] == null
                        && solutionData[row][col] == null) {
                    continue;
                }

                if (currentData[row][col] == null
                        || !currentData[row][col].equals(
                        solutionData[row][col])) {

                    return;
                }
            }
        }

        gameWon = true;

        System.out.println("游戏胜利！");
    }

    // 松开 A 键：恢复拼图
    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_A && !gameWon) {
            puzzlePanel.hidePuzzleImage();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

