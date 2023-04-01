package cn.dxr.quake.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AboutDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public AboutDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("关于");
        setIconImage(Toolkit.getDefaultToolkit().getImage("Files/img/icon.png"));
        pack();

        buttonOK.addActionListener(e->onOK());

        // 点击 X 时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK() {
        // 在此处添加您的代码
        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }
}
