import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleData {

    private static final int ROWS = 4;
    private static final int COLS = 4;

    // 保存当前拼图数据
    private String[][] puzzleData;

    // 保存拼图完成时的正确顺序
    private static final String[][] SOLUTION_DATA = {
            {"00", "10", "20", "30"},
            {"01", "11", "21", "31"},
            {"02", "12", "22", "32"},
            {"03", "13", "23", null}
    };

    public PuzzleData() {
        initData();
    }

    // 初始化拼图数据
    private void initData() {

        List<String> imageList = new ArrayList<>();

        // 第一位代表列，第二位代表行
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                // 右下角作为空白格
                if (row == 3 && col == 3) {
                    imageList.add(null);
                } else {
                    imageList.add(col + "" + row);
                }
            }
        }

        // 随机打乱图片编号
        Collections.shuffle(imageList);

        puzzleData = new String[ROWS][COLS];

        // 将一维数据转换成二维数据
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                puzzleData[row][col] =
                        imageList.get(row * COLS + col);
            }
        }
    }

    // 获取当前拼图数据
    public String[][] getPuzzleData() {
        return puzzleData;
    }

    // 获取正确的拼图顺序
    public String[][] getSolutionData() {
        return SOLUTION_DATA;
    }

    // 将拼图设置为完成状态
    public void solvePuzzle() {

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                puzzleData[row][col] =
                        SOLUTION_DATA[row][col];
            }
        }
    }
}