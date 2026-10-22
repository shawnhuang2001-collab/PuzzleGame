
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class GameFrame extends JFrame {

    private PuzzleData puzzleData;

    // 保存当前图片文件夹路径
    private String imagePath =
            "images/animals/animal1/";

    public GameFrame() {

        initData();
        initFrame();
        initMenu();
        initGame();
    }

    // 初始化拼图数据
    private void initData() {

        puzzleData = new PuzzleData();
    }

    // 初始化游戏窗口
    private void initFrame() {

        setTitle("Puzzle Game");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // 初始化菜单栏
    private void initMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu functionMenu = new JMenu("功能");
        JMenuItem contactItem = new JMenuItem("联系我们");

        contactItem.addActionListener(e -> {
            showQRCode();
        });

        JMenuItem changeImageItem = new JMenuItem("更换图片");

        changeImageItem.addActionListener(e -> {
            showImageTypeDialog();
        });

        JMenuItem restartGameItem = new JMenuItem("重新游戏");

        restartGameItem.addActionListener(e -> {
            restartGame();
        });

        JMenuItem reloginItem = new JMenuItem("重新登录");

        reloginItem.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });

        JMenuItem exitGameItem = new JMenuItem("关闭游戏");

        exitGameItem.addActionListener(e -> {
            System.exit(0);
        });

        functionMenu.add(changeImageItem);
        functionMenu.add(restartGameItem);
        functionMenu.add(reloginItem);
        functionMenu.add(exitGameItem);

        menuBar.add(functionMenu);
        menuBar.add(contactItem);

        setJMenuBar(menuBar);
    }

    // 初始化游戏界面
    private void initGame() {

        PuzzlePanel puzzlePanel =
                new PuzzlePanel(
                        puzzleData,
                        imagePath
                );

        GamePanel gamePanel = new GamePanel();

        // 使用网格布局控制游戏组件位置
        gamePanel.setLayout(new GridBagLayout());

        MoveController moveController =
                new MoveController(
                        puzzleData,
                        puzzlePanel,
                        gamePanel
                );

        GridBagConstraints puzzleConstraints =
                new GridBagConstraints();

        puzzleConstraints.gridx = 0;
        puzzleConstraints.gridy = 0;
        puzzleConstraints.weightx = 1;
        puzzleConstraints.weighty = 1;
        puzzleConstraints.anchor = GridBagConstraints.CENTER;

        gamePanel.add(
                puzzlePanel,
                puzzleConstraints
        );

        GridBagConstraints stepConstraints =
                new GridBagConstraints();

        stepConstraints.gridx = 0;
        stepConstraints.gridy = 0;
        stepConstraints.weightx = 1;
        stepConstraints.weighty = 1;
        stepConstraints.anchor = GridBagConstraints.NORTHEAST;
        stepConstraints.insets = new Insets(20, 0, 0, 20);

        gamePanel.add(
                gamePanel.getStepLabel(),
                stepConstraints
        );

        // 添加更换图片按钮
        GridBagConstraints changeImageConstraints =
                new GridBagConstraints();

        changeImageConstraints.gridx = 0;
        changeImageConstraints.gridy = 0;
        changeImageConstraints.weightx = 1;
        changeImageConstraints.weighty = 1;
        changeImageConstraints.anchor =
                GridBagConstraints.NORTHEAST;
        changeImageConstraints.insets =
                new Insets(60, 0, 0, 20);

        gamePanel.add(
                gamePanel.getChangeImageButton(),
                changeImageConstraints
        );

        // 点击按钮随机更换图片
        gamePanel.getChangeImageButton()
                .addActionListener(e -> {
                    changeRandomImage();
                });

        gamePanel.addKeyListener(moveController);
        gamePanel.setFocusable(true);

        add(gamePanel);

        gamePanel.requestFocusInWindow();
    }

    // 重新开始游戏
    private void restartGame() {

        puzzleData = new PuzzleData();

        getContentPane().removeAll();

        initGame();

        revalidate();
        repaint();
    }

    // 随机更换当前图片类型中的图片
    private void changeRandomImage() {

        File currentFolder = new File(imagePath);

        File typeFolder = currentFolder.getParentFile();

        if (typeFolder == null) {
            return;
        }

        File[] imageFolders =
                typeFolder.listFiles(File::isDirectory);

        if (imageFolders == null || imageFolders.length <= 1) {
            return;
        }

        // 排除当前图片文件夹
        File[] availableFolders =
                new File[imageFolders.length - 1];

        int index = 0;

        for (File imageFolder : imageFolders) {

            if (!imageFolder.equals(currentFolder)) {
                availableFolders[index] = imageFolder;
                index++;
            }
        }

        // 随机选择新的图片文件夹
        int randomIndex =
                (int) (Math.random() * availableFolders.length);

        imagePath =
                availableFolders[randomIndex].getPath()
                        + File.separator;

        puzzleData = new PuzzleData();

        getContentPane().removeAll();

        initGame();

        revalidate();
        repaint();
    }

    // 显示二维码
    private void showQRCode() {

        JDialog dialog = new JDialog(
                this,
                "联系我们",
                true
        );

        ImageIcon originalIcon =
                new ImageIcon("images/二维码.jpg");

        Image image = originalIcon.getImage();

        Image scaledImage = image.getScaledInstance(
                350,
                350,
                Image.SCALE_SMOOTH
        );

        JLabel qrLabel =
                new JLabel(new ImageIcon(scaledImage));

        dialog.add(qrLabel);

        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        dialog.setVisible(true);
    }

    // 显示图片类型选择窗口
    private void showImageTypeDialog() {

        String[] options = {
                "动物",
                "交通工具",
                "建筑",
                "植物"
        };

        int choice = JOptionPane.showOptionDialog(
                this,
                "请选择图片类型",
                "更换图片",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            changeImageType("images/animals/");
        }

        if (choice == 1) {
            changeImageType("images/transportations/");
        }

        if (choice == 2) {
            changeImageType("images/buildings/");
        }

        if (choice == 3) {
            changeImageType("images/plants/");
        }
    }

    // 切换图片类型并随机选择图片
    private void changeImageType(String typePath) {

        File folder = new File(typePath);

        File[] imageFolders =
                folder.listFiles(File::isDirectory);

        if (imageFolders == null || imageFolders.length == 0) {
            return;
        }

        int randomIndex =
                (int) (Math.random() * imageFolders.length);

        imagePath =
                imageFolders[randomIndex].getPath()
                        + File.separator;

        puzzleData = new PuzzleData();

        getContentPane().removeAll();

        initGame();

        revalidate();
        repaint();
    }
}

