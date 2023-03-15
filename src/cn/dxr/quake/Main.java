package cn.dxr.quake;

import cn.dxr.quake.GUI.MainWindow;
import cn.dxr.quake.app.AppTray;
import cn.dxr.quake.app.StartDataWriter;
import cn.dxr.quake.app.VersionChecker;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // 写入初始数据
        StartDataWriter startDataWriter = new StartDataWriter();
        startDataWriter.write();

        // 实例化程序主窗口
        new MainWindow();

        // 实例化程序系统托盘图标
        new AppTray();

        // 检查版本
        VersionChecker versionChecker = new VersionChecker();
        try {
            versionChecker.checkVersion();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 弹窗提示
        JOptionPane.showMessageDialog(null,"关闭此窗口后，程序将在后台运行，收到地震预警时将弹出。若关闭窗口后需要重新显示此窗口,请点击程序托盘图标。");
    }
}