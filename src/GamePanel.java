import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private Image backgroundImage;

    // 显示当前步数
    private JLabel stepLabel;
    // 更换图片按钮
    private JButton changeImageButton;

    public GamePanel() {

        // 加载背景图片
        backgroundImage = new ImageIcon(
                "images/background.png"
        ).getImage();

        // 创建步数标签
        stepLabel = new JLabel("步数:00");
        stepLabel.setFont(
                new Font("微软雅黑", Font.BOLD, 20)
        );


        //自定义计数器背景色
        stepLabel.setOpaque(true);
        stepLabel.setBackground(new Color(255, 245, 190));

        // 创建更换图片按钮
        changeImageButton = new JButton("更换图片");
    }


    // 获取步数标签
    public JLabel getStepLabel() {
        return stepLabel;
    }
    // 获取更换图片按钮
    public JButton getChangeImageButton() {
        return changeImageButton;
    }
    // 更新步数显示
    public void updateSteps(int steps) {

        stepLabel.setText(
                String.format("步数:%02d", steps)
        );
    }

    // 绘制游戏背景
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        g.drawImage(
                backgroundImage,
                0,
                0,
                getWidth(),
                getHeight(),
                this
        );
    }
}