package cn.dxr.quake.GUI;

import cn.dxr.quake.Utils.DistanceUtil;
import cn.dxr.quake.Utils.HttpUtil;
import cn.dxr.quake.Utils.SoundUtil;
import cn.dxr.quake.app.AppTray;
import cn.dxr.quake.app.VersionChecker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindow {

    private static String EventID = null;

    private static final JFrame jFrame = new JFrame();

    JPanel jPanel;
    JLabel label,label1,label2,label3,label4,label5,label6,label7,label8,label9,label10,label11;
    SoundUtil soundUtil=new SoundUtil();
    String postUrl="";
    String eewData="";

    public MainWindow() {
        // 定义程序窗口及控件属性
        try{
            JSONObject jURL=JSON.parseObject(FileUtils.readFileToString(new File("Files/settings.json")));
            postUrl=jURL.getString("PushURL");
        }catch (IOException e){
            e.printStackTrace();
        }
        jFrame.setTitle("地震预警 v"+ VersionChecker.currentVersion);
        Image image = Toolkit.getDefaultToolkit().getImage("Files\\img\\icon.png");
        jFrame.setIconImage(image);
        jFrame.setSize(330, 330);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setLayout(new GridLayout());
        jFrame.setResizable(false);
        Font font = new Font("微软雅黑", Font.BOLD, 20);
        Font timeFont = new Font("微软雅黑", Font.BOLD, 23);
        jPanel = new JPanel();
        jPanel.setFont(font);
        jPanel.setBounds(0, 0, 330, 260);
        label = new JLabel();
        label.setFont(font);
        label1 = new JLabel();
        label1.setFont(font);
        label2 = new JLabel();
        label2.setFont(font);
        label3 = new JLabel();
        label3.setFont(font);
        label4 = new JLabel();
        label4.setFont(font);
        label5 = new JLabel();
        label5.setFont(font);
        label6 = new JLabel();
        label6.setFont(font);
        label8 = new JLabel();
        label8.setFont(font);
        label7 = new JLabel();
        label7.setFont(font);
        label9 = new JLabel();
        label9.setFont(font);
        label10 = new JLabel();
        label10.setFont(font);
        label11 = new JLabel();
        label11.setFont(timeFont);
        jPanel.setBackground(new Color(37, 42, 37, 255));
        label.setForeground(Color.white);
        label2.setForeground(Color.white);
        label3.setForeground(Color.white);
        label5.setForeground(Color.white);
        label6.setForeground(Color.white);
        label7.setForeground(Color.white);
        label8.setForeground(Color.white);
        label9.setForeground(Color.white);
        label10.setForeground(Color.white);
        label11.setForeground(Color.white);
        // 将控件添加至窗口
        jPanel.add(label);
        jPanel.add(label2);
        jPanel.add(label3);
        jPanel.add(label5);
        jPanel.add(label6);
        jPanel.add(label8);
        jPanel.add(label10);
        jPanel.add(label9);
        jPanel.add(label11);
        jPanel.add(label7);
        jFrame.add(jPanel);

        // 右键菜单
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItem = new JMenuItem("关于");
        jMenuItem.setFont(new Font("微软雅黑",Font.BOLD,12));
        jMenuItem.addActionListener(e -> new AboutPage());
        JMenuItem jMenuItem1 = new JMenuItem("设置");
        jMenuItem1.setFont(new Font("微软雅黑",Font.BOLD,12));
        jMenuItem1.addActionListener(e -> new SettingsDialog());
        JMenuItem jMenuItem2 = new JMenuItem("退出程序");
        jMenuItem2.setFont(new Font("微软雅黑",Font.BOLD,12));
        jMenuItem2.addActionListener(e -> System.exit(1));
        jPopupMenu.add(jMenuItem);
        jPopupMenu.add(jMenuItem1);
        jPopupMenu.add(jMenuItem2);

        // 右键菜单事件监听器
        jFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    jPopupMenu.show(jFrame,e.getX(),e.getY());
                }
            }
        });

        // 窗口关闭事件监听器
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AppTray.showMessage("提示","地震预警正在后台运行,当收到地震预警且本地烈度达到预警阀值时主窗口将会弹出");
            }
        });

        TimerTask timerQueryEEW=new TimerTask() {
            @Override
            public void run() {
                eewData=HttpUtil.sendGet("https://mobile.chinaeew.cn", "/v1/earlywarnings?updates=&start_at=");
            }
        };
        new Timer().schedule(timerQueryEEW,0,3000L);

        // 创建一个定时任务用于获取地震信息
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    TaskFunction1(eewData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // 每3秒向api接口发送一次请求
        new Timer().schedule(timerTask, 0L, 3000L);

        // 再创建一个定时任务用于倒计时
        TimerTask timerTaskCounter=new TimerTask() {
            @Override
            public void run() {
                try{
                    TaskFunction2(eewData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        new Timer().schedule(timerTaskCounter,0,3000);

        // 创建另一个定时任务用于获取当前时间
        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                String url = HttpUtil.sendGet("https://api.pinduoduo.com", "/api/server/_stm");
                try {
                    JSONObject jsonObject = JSON.parseObject(url);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Long time = new Long(jsonObject.getString("server_time"));
                    String date = simpleDateFormat.format(time);
                    label7.setText(date);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // 每秒向api接口发送一次请求
        new Timer().schedule(timerTask1, 0L, 1000L);
    }

    private void TaskFunction1(String data)throws Exception{
        File path = new File("Files\\settings.json");
        File path1 = new File("Files\\start.json");
        File path2 = new File("Files\\int.json");
        JSONObject jsonObj = JSON.parseObject(data);
        JSONArray jsonArray = jsonObj.getJSONArray("data");
        JSONObject json = jsonArray.getJSONObject(0);
        double epicenterLat = json.getDouble("latitude");
        double epicenterLng = json.getDouble("longitude");
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Long time = new Long(json.getString("startAt"));
        double maxInt = calcMaxInt(json.getDouble("magnitude"),json.getDouble("depth"));
        double arriveTime = Double.parseDouble(decimalFormat.format(getArriveTime(data)));
        String date = format.format(time);
        String file = FileUtils.readFileToString(path);
        JSONObject jsonObject = JSON.parseObject(file);
        double userLat = jsonObject.getDouble("Lat");
        double userlng = jsonObject.getDouble("Lng");
        String distance = decimalFormat.format(DistanceUtil.getDistance(userlng, userLat, epicenterLng, epicenterLat));
        double local = 0.92 + 1.63 * json.getDouble("magnitude") - 3.49 * Math.log10(Double.parseDouble(distance));
        String localInt = decimalFormat.format(local);
        if (local < 0) {
            localInt = "0.0";
        }
        label10.setText("本地烈度: " + localInt + "度" + " " + getFeel(data));
        label9.setText("震中距: 约" + distance + "KM");
        String file1 = FileUtils.readFileToString(path1);
        JSONObject jsonObject1 = JSON.parseObject(file1);
        label.setText("  地震预警  ");
        label2.setText("     " + json.getString("epicenter") + "         M" + decimalFormat.format(json.getDouble("magnitude")));
        label3.setText("    震源深度: " + decimalFormat.format(json.getDouble("depth")) + "KM   ");
        label6.setText("发震时刻: " + date);
        label8.setText("最大烈度: " + decimalFormat.format(maxInt) + "度");
        String file2 = FileUtils.readFileToString(path2);
        JSONObject jsonObject2 = JSON.parseObject(file2);
        double userInt = jsonObject2.getDouble("Int");
        if (!Objects.equals(json.getString("eventId"), jsonObject1.getString("ID"))) {
            if (!Objects.equals(json.getString("eventId"), EventID)) {
                if (local < 0) {
                    jPanel.setBackground(Color.gray);
                }
                if (local >= 0 && local < 1) {
                    jPanel.setBackground(Color.gray);
                }
                if (local >= 1 && local < 2) {
                    jPanel.setBackground(new Color(121, 118, 5, 255));
                }
                if (local >= 2 && local < 3) {
                    jPanel.setBackground(new Color(169, 142, 27, 255));
                }
                if (local >= 3 && local < 4) {
                    jPanel.setBackground(new Color(185, 85, 13, 255));
                }
                if (local >=4 && local < 5) {
                    jPanel.setBackground(new Color(168, 37, 8, 255));
                }
                if (local >= 5) {
                    jPanel.setBackground(new Color(147, 7, 7, 255));
                }
                if (local > userInt) {
                    soundUtil.playSound("sounds\\First.wav");
                    jFrame.setAlwaysOnTop(true);
                    jFrame.setVisible(true);
                }
                if(postUrl.length()>0) {
                    //转发ICL速报信息
                    HttpUtil.sendPost(postUrl,json.toString());
                }
                AppTray.showMessage("现正发生有感地震",decimalFormat.format(json.getDouble("magnitude")) + "级地震," + arriveTime + "秒后抵达\n" + "您所在的地区将" + getFeel(data) + ",请合理避险!");
                EventID = json.getString("eventId");
            } else {
                jFrame.setAlwaysOnTop(false);
            }
        }
    }

    private void TaskFunction2(String data)throws Exception{
        File path = new File("Files\\settings.json");
        File path1 = new File("Files\\start.json");
        JSONObject jsonObj = JSON.parseObject(data);
        JSONArray jsonArray = jsonObj.getJSONArray("data");
        JSONObject json = jsonArray.getJSONObject(0);
        String file = FileUtils.readFileToString(path);
        JSONObject jsonObject = JSON.parseObject(file);
        String file1 = FileUtils.readFileToString(path1);
        JSONObject jsonObject1 = JSON.parseObject(file1);
        double epicenterLat = json.getDouble("latitude");
        double epicenterLng = json.getDouble("longitude");
        double userLat = jsonObject.getDouble("Lat");
        double userlng = jsonObject.getDouble("Lng");
        int time = (int) DistanceUtil.getTime(userlng, userLat, epicenterLng, epicenterLat);
        label11.setText("地震横波已抵达");
        if (!Objects.equals(json.getString("eventId"), jsonObject1.getString("ID"))) {
            if (!Objects.equals(json.getString("eventId"), EventID)) {
                // 倒计时
                for (int i = time; i > -1; i--) {
                    label11.setText("地震横波将在" + i + "秒后抵达");
                    if (i == 0) {
                        soundUtil.playSound("sounds\\Arrive.wav");
                        label11.setText("地震横波已抵达");
                    }
                    Thread.sleep(1000L);
                }
                EventID = json.getString("eventId");
            } else {
                jPanel.setBackground(new Color(37, 42, 37, 255));
            }
        }
    }

    // 获取横波到达时间
    public static double getArriveTime(String data) {
        File path = new File("Files\\settings.json");
        try {
            String url = data;
            JSONObject jsonObj = JSON.parseObject(url);
            JSONArray jsonArray = jsonObj.getJSONArray("data");
            JSONObject json = jsonArray.getJSONObject(0);
            String file = FileUtils.readFileToString(path);
            JSONObject jsonObject = JSON.parseObject(file);
            double epicenterLat = json.getDouble("latitude");
            double epicenterLng = json.getDouble("longitude");
            double userLat = jsonObject.getDouble("Lat");
            double userlng = jsonObject.getDouble("Lng");
            return DistanceUtil.getTime(userlng, userLat, epicenterLng, epicenterLat);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 获取震感
    public static String getFeel(String data) {
        File path = new File("Files\\settings.json");
        try {
            String url = data;
            JSONObject jsonObj = JSON.parseObject(url);
            JSONArray jsonArray = jsonObj.getJSONArray("data");
            JSONObject json = jsonArray.getJSONObject(0);
            String file = FileUtils.readFileToString(path);
            JSONObject jsonObject = JSON.parseObject(file);
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            double epicenterLat = json.getDouble("latitude");
            double epicenterLng = json.getDouble("longitude");
            double userLat = jsonObject.getDouble("Lat");
            double userlng = jsonObject.getDouble("Lng");
            String distance = decimalFormat.format(DistanceUtil.getDistance(userlng, userLat, epicenterLng, epicenterLat));
            double local = 0.92 + 1.63 * json.getDouble("magnitude") - 3.49 * Math.log10(Double.parseDouble(distance));
            String feel = "";
            if (local < 1) {
                feel = "无震感";
            }
            if (local >= 1 && local < 2) {
                feel = "可能有震感";
            }
            if (local >= 2 && local < 3) {
                feel = "高楼层有感";
            }
            if (local >= 3 && local < 4) {
                feel = "震感较强";
            }
            if (local >= 4 && local < 5) {
                feel = "震感强烈";
            }
            if (local >= 5) {
                feel = "震感极强";
            }
            return feel;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 计算烈度
    public static double calcMaxInt(double magnitude, double depth) {
        double a = 1.65 * magnitude;
        double b = depth < 10 ? 1.21 * Math.log10(10) : 1.21 * Math.log10(depth);
        return (double) Math.round(a / b);
    }

    public static void show() {
        jFrame.setVisible(true);
    }
}
