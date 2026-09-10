
import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame() {

        initFrame();
        initMenu();

    }

    // 初始化窗口
    private void initFrame() {

        setTitle("Puzzle Game");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    // 初始化菜单栏
    private void initMenu() {

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();

        // 创建一级菜单
        JMenu functionMenu = new JMenu("功能");
        JMenu contactMenu = new JMenu("联系我们");

        // 创建“功能”菜单中的选项
        JMenuItem changeImageItem = new JMenuItem("更换图片");
        JMenuItem restartGameItem = new JMenuItem("重新游戏");
        JMenuItem reloginItem = new JMenuItem("重新登录");
        JMenuItem exitGameItem = new JMenuItem("关闭游戏");

        // 将功能选项添加到“功能”菜单
        functionMenu.add(changeImageItem);
        functionMenu.add(restartGameItem);
        functionMenu.add(reloginItem);
        functionMenu.add(exitGameItem);

        // 将一级菜单添加到菜单栏
        menuBar.add(functionMenu);
        menuBar.add(contactMenu);

        // 将菜单栏添加到窗口
        setJMenuBar(menuBar);
    }
}

