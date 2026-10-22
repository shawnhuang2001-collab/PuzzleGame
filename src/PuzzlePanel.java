import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class PuzzlePanel extends JPanel {

    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static final int TILE_SIZE = 135;

    // 保存当前拼图数据
    private PuzzleData puzzleData;

    // 保存当前图片文件夹路径
    private String imagePath;

    // 管理拼图和完整原图的切换
    private CardLayout cardLayout;

    // 保存拼图区域
    private JPanel puzzleContainer;

    // 保存完整原图
    private JLabel puzzleImageLabel;

    // 控制当前是否显示完整原图
    private boolean showingImage;

    // 创建拼图界面
    public PuzzlePanel(
            PuzzleData puzzleData,
            String imagePath
    ) {

        this.puzzleData = puzzleData;
        this.imagePath = imagePath;

        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setOpaque(false);

        puzzleContainer = new JPanel();
        puzzleContainer.setLayout(
                new GridLayout(ROWS, COLS)
        );
        puzzleContainer.setOpaque(false);

        // 加载完整原图
        ImageIcon originalIcon =
                new ImageIcon(imagePath + "all.jpg");

        Image originalImage =
                originalIcon.getImage();

        Image scaledImage =
                originalImage.getScaledInstance(
                        540,
                        540,
                        Image.SCALE_SMOOTH
                );

        puzzleImageLabel =
                new JLabel(new ImageIcon(scaledImage));

        createPuzzle();

        add(puzzleContainer, "puzzle");
        add(puzzleImageLabel, "image");

        cardLayout.show(this, "puzzle");
    }

    // 创建拼图块
    private void createPuzzle() {

        ImageIcon[][] images =
                ImageLoader.loadImages(
                        puzzleData,
                        imagePath
                );

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {

                JLabel tile = new JLabel();
                tile.setOpaque(false);

                if (images[row][col] != null) {

                    Image image =
                            images[row][col].getImage();

                    Image scaledImage =
                            image.getScaledInstance(
                                    TILE_SIZE,
                                    TILE_SIZE,
                                    Image.SCALE_SMOOTH
                            );

                    tile.setIcon(
                            new ImageIcon(scaledImage)
                    );
                }

                tile.setBorder(
                        new BevelBorder(
                                BevelBorder.LOWERED
                        )
                );

                puzzleContainer.add(tile);
            }
        }
    }

    // 刷新拼图画面
    public void refresh() {

        puzzleContainer.removeAll();

        createPuzzle();

        puzzleContainer.revalidate();
        puzzleContainer.repaint();
    }

    // 显示完整原图
    public void showPuzzleImage() {

        if (!showingImage) {

            showingImage = true;

            cardLayout.show(this, "image");

            revalidate();
            repaint();
        }
    }

    // 恢复显示拼图
    public void hidePuzzleImage() {

        if (showingImage) {

            showingImage = false;

            cardLayout.show(this, "puzzle");

            revalidate();
            repaint();
        }
    }

    // 设置拼图区域的默认大小
    @Override
    public Dimension getPreferredSize() {

        return new Dimension(
                COLS * TILE_SIZE,
                ROWS * TILE_SIZE
        );
    }
}