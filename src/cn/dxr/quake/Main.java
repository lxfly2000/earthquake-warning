package cn.dxr.quake;

import cn.dxr.quake.GUI.MainWindow;
import cn.dxr.quake.app.AppTray;
import cn.dxr.quake.app.StartDataWriter;
import cn.dxr.quake.app.VersionChecker;

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
    }
}