package cn.dxr.quake.GUI;

import cn.dxr.quake.Utils.SoundUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class SettingsPage {
    public SettingsPage() {
        // 定义设置界面窗口及控件属性
        JFrame jFrame = new JFrame("设置");
        Image image = Toolkit.getDefaultToolkit().getImage("Files\\img\\icon.png");
        jFrame.setIconImage(image);
        jFrame.setSize(400,450);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setLayout(null);
        jFrame.setResizable(false);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(0,0,400,450);
        JLabel jLabel = new JLabel();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        jLabel.setBounds(0,0,250,50);
        jLabel.setText("-----用户所在地经纬度设置-----");
        jLabel.setForeground(Color.white);
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel1.setBounds(12,40,250,20);
        jLabel1.setText("纬度");
        jLabel1.setForeground(Color.white);
        jLabel1.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel2.setBounds(180,40,250,20);
        jLabel2.setText("经度");
        jLabel2.setForeground(Color.white);
        jLabel2.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel3.setBounds(0,70,250,50);
        jLabel3.setText("-----预警音效试听-----");
        jLabel3.setForeground(Color.white);
        jLabel3.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel4.setBounds(12,110,250,20);
        jLabel4.setText("地震预警发布时");
        jLabel4.setForeground(Color.white);
        jLabel4.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel5.setBounds(12,140,250,20);
        jLabel5.setText("地震横波抵达时");
        jLabel5.setForeground(Color.white);
        jLabel5.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel6.setBounds(0,170,250,50);
        jLabel6.setText("-----预警烈度阀值设置-----");
        jLabel6.setForeground(Color.white);
        jLabel6.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel7.setBounds(0,190,450,50);
        jLabel7.setText("注: 当本地预估烈度达到以下所设阀值及以上时才会预警");
        jLabel7.setForeground(Color.white);
        jLabel7.setFont(new Font("微软雅黑", Font.BOLD, 13));
        JComboBox jComboBox = new JComboBox();
        jComboBox.setBounds(12,230,90,20);
        jComboBox.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jComboBox.addItem("不设置");
        jComboBox.addItem("2度");
        jComboBox.addItem("3度");
        jComboBox.addItem("4度");
        JEditorPane jEditorPane = new JEditorPane();
        JEditorPane jEditorPane1 = new JEditorPane();
        JEditorPane jEditorPane2 = new JEditorPane();
        jComboBox.addItemListener(e -> {
            String Int = Objects.requireNonNull(jComboBox.getSelectedItem()).toString();
            jEditorPane2.setText(Int);
        });
        jEditorPane.setBounds(50,40,100,20);
        jEditorPane1.setBounds(220,40,100,20);
        JButton jButton = new JButton("保存设置");
        JButton jButton1 = new JButton("试听");
        JButton jButton2 = new JButton("试听");
        jButton.setBounds(120,330,150,30);
        jButton.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jButton.setFocusPainted(false);
        jButton1.setBounds(120,110,70,20);
        jButton1.setFont(new Font("微软雅黑", Font.BOLD, 12));
        jButton1.setFocusPainted(false);
        jButton2.setBounds(120,140,70,20);
        jButton2.setFont(new Font("微软雅黑", Font.BOLD, 12));
        jButton2.setFocusPainted(false);
        // 将控件添加至窗口
        jFrame.add(jPanel);
        jPanel.add(jLabel);
        jPanel.add(jLabel1);
        jPanel.add(jLabel2);
        jPanel.add(jLabel3);
        jPanel.add(jLabel4);
        jPanel.add(jLabel5);
        jPanel.add(jLabel6);
        jPanel.add(jLabel7);
        jPanel.add(jEditorPane);
        jPanel.add(jEditorPane1);
        jPanel.add(jButton);
        jPanel.add(jButton1);
        jPanel.add(jButton2);
        jPanel.add(jComboBox);
        // 设置背景
        jPanel.setBackground(new Color(37, 42, 37, 255));

        // 文件读取
        File path = new File("Files\\settings.json");
        File path1 = new File("Files\\int.json");
        try {
            String file = FileUtils.readFileToString(path);
            JSONObject jsonObject = JSON.parseObject(file);
            jEditorPane.setText(jsonObject.getString("Lat"));
            jEditorPane1.setText(jsonObject.getString("Lng"));
            String file1 = FileUtils.readFileToString(path1);
            JSONObject jsonObject1 = JSON.parseObject(file1);
            if (jsonObject1.getString("Int").equals("-1000.0")) {
                jComboBox.setSelectedIndex(0);
            }
            if (jsonObject1.getString("Int").equals("2.0")) {
                jComboBox.setSelectedIndex(1);
            }
            if (jsonObject1.getString("Int").equals("3.0")) {
                jComboBox.setSelectedIndex(2);
            }
            if (jsonObject1.getString("Int").equals("4.0")) {
                jComboBox.setSelectedIndex(3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 实例化声音播放类
        SoundUtil soundUtil = new SoundUtil();

        // 音效播放
        jButton1.addActionListener(e -> soundUtil.playSound("sounds\\First.wav"));
        jButton2.addActionListener(e -> soundUtil.playSound("sounds\\Arrive.wav"));

        // 保存设置
        jButton.addActionListener(e -> {
            try {
                JSONObject jsonObject1 = new JSONObject();
                JSONObject jsonObject2 = new JSONObject();
                jsonObject1.put("Lat",jEditorPane.getText());
                jsonObject1.put("Lng",jEditorPane1.getText());
                if (jEditorPane2.getText().equals("不设置")) {
                    jsonObject2.put("Int","-1000.0");
                }
                if (jEditorPane2.getText().equals("2度")) {
                    jsonObject2.put("Int","2.0");
                }
                if (jEditorPane2.getText().equals("3度")) {
                    jsonObject2.put("Int","3.0");
                }
                if (jEditorPane2.getText().equals("4度")) {
                    jsonObject2.put("Int","4.0");
                }
                BufferedWriter bufferedWriter;
                bufferedWriter = new BufferedWriter(new FileWriter("Files\\settings.json"));
                bufferedWriter.write(jsonObject1.toString());
                bufferedWriter.close();
                BufferedWriter bufferedWriter1;
                bufferedWriter1 = new BufferedWriter(new FileWriter("Files\\int.json"));
                bufferedWriter1.write(jsonObject2.toString());
                bufferedWriter1.close();
                JOptionPane.showMessageDialog(null, "设置保存成功!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "设置保存失败!" + ex, "错误", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }
}
