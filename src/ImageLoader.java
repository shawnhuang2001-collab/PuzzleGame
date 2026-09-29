import javax.swing.ImageIcon;
import java.io.File;

public class ImageLoader {

    private static final int ROWS = 4;
    private static final int COLS = 4;

    // 根据拼图数据和图片路径加载对应图片
    public static ImageIcon[][] loadImages(
            PuzzleData puzzleData,
            String imagePath
    ) {

        ImageIcon[][] images =
                new ImageIcon[ROWS][COLS];

        String[][] data =
                puzzleData.getPuzzleData();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                String imageNumber =
                        data[row][col];

                // 空白格不加载图片
                if (imageNumber == null) {
                    continue;
                }

                String fileName =
                        imageNumber + ".jpg";

                File file =
                        new File(imagePath + fileName);

                if (file.exists()) {
                    images[row][col] =
                            new ImageIcon(file.getPath());
                } else {
                    System.out.println(
                            "图片不存在：" + file.getPath()
                    );
                }
            }
        }

        return images;
    }
}