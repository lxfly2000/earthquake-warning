package cn.dxr.quake.app;

import cn.dxr.quake.GUI.MainWindow;
import cn.dxr.quake.GUI.SettingsDialog;
import cn.dxr.quake.Utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class AppTray {

    private static String id = null;

    private static final Image image = Toolkit.getDefaultToolkit().getImage("Files\\img\\tray.png");

    private static final TrayIcon trayIcon = new TrayIcon(image);
    private static String postUrl="";

    public AppTray() {
        try{
            JSONObject jURL=JSON.parseObject(FileUtils.readFileToString(new File("Files/settings.json")));
            postUrl=jURL.getString("PushURL");
        }catch (IOException e){
            e.printStackTrace();
        }
        // 托盘右键菜单
        JDialog jDialog = new JDialog();
        jDialog.setUndecorated(true);
        jDialog.setSize(1,1);
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jmenuItem = new JMenuItem("设置");
        jmenuItem.addActionListener(e -> new SettingsDialog());
        JMenuItem jmenuItem2 = new JMenuItem("显示主窗口");
        jmenuItem2.addActionListener(e -> MainWindow.show());
        JMenuItem jmenuItem1 = new JMenuItem("退出程序");
        jmenuItem1.addActionListener(e -> System.exit(1));
        jPopupMenu.add(jmenuItem);
        jPopupMenu.add(jmenuItem2);
        jPopupMenu.add(jmenuItem1);

        // 系统托盘
        if (SystemTray.isSupported()) {
            SystemTray systemTray = SystemTray.getSystemTray();
            trayIcon.setToolTip("地震预警 by dxr");
            trayIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3) {
                        jDialog.setLocation(e.getX(),e.getY());
                        jDialog.setVisible(true);
                        jPopupMenu.show(jDialog,0,0);
                    } else if (e.getButton() == MouseEvent.BUTTON1) {
                        MainWindow.show();
                    }
                }
            });
            try {
                systemTray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }

            // 创建一个定时任务用于获取台网信息
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        String httpGet = HttpUtil.sendGet("http://218.5.2.111:9088/", "/earthquakeWarn/bulletin/list.json?pageSize=1");
                        JSONObject jsonObject = JSON.parseObject(httpGet);
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        JSONObject json = jsonArray.getJSONObject(0);
                        DecimalFormat decimalFormat = new DecimalFormat("0.0");
                        if (!Objects.equals(json.getString("id"), id)) {
                            String type = json.getString("infoTypeName");
                            String time = json.getString("shockTime");
                            String region = json.getString("placeName");
                            double lat = json.getDouble("latitude");
                            double lng = json.getDouble("longitude");
                            String mag = json.getString("magnitude");
                            String depth = json.getString("depth");
                            double maxInt = MainWindow.calcMaxInt(json.getDouble("magnitude"),json.getDouble("depth"));
                            if(postUrl.length()>0){
                                HttpUtil.sendPost(postUrl,json.toString());
                            }
                            showMessage("中国地震台网" + type,time + "在" + region + "(北纬" + lat + "度,东经" + lng + "度)" + "发生" + mag + "级地震,震源深度" + depth +"公里,预估最大烈度" + decimalFormat.format(maxInt) + "度");
                            id = json.getString("id");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            new Timer().schedule(timerTask,0L,7000L);
        }
    }
    public static void showMessage(String title,String message) {
        trayIcon.displayMessage(title,message,TrayIcon.MessageType.INFO);
    }
}
