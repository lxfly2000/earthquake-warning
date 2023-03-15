package cn.dxr.quake.GUI;

import javax.swing.*;
import java.awt.*;

public class AboutPage {
    public AboutPage() {
        // 定义关于界面窗口及控件属性
        JFrame jFrame = new JFrame("关于");
        Image image = Toolkit.getDefaultToolkit().getImage("Files\\img\\icon.png");
        jFrame.setIconImage(image);
        jFrame.setSize(460,300);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setLayout(null);
        jFrame.setResizable(false);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(null);
        jPanel.setBounds(0,0,460,300);
        JLabel jLabel = new JLabel();
        JLabel jLabel1 = new JLabel();
        JLabel jLabel2 = new JLabel();
        JLabel jLabel3 = new JLabel();
        JLabel jLabel4 = new JLabel();
        JLabel jLabel5 = new JLabel();
        JLabel jLabel6 = new JLabel();
        JLabel jLabel7 = new JLabel();
        JLabel jLabel8 = new JLabel();
        JLabel jLabel9 = new JLabel();
        jLabel.setBounds(150,0,250,50);
        jLabel.setText("关于本软件");
        jLabel.setForeground(Color.white);
        jLabel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        jLabel1.setBounds(20,40,460,50);
        jLabel1.setText("软件作者: dxr_xie2019(简称Dxr) QQ: 2194362576");
        jLabel1.setForeground(Color.white);
        jLabel1.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel2.setBounds(20,60,460,50);
        jLabel2.setText("本软件禁止用于商业用途! 如有疑问或有合作意图可联系作者QQ");
        jLabel2.setForeground(Color.white);
        jLabel2.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel3.setBounds(20,80,460,50);
        jLabel3.setText("以下机构、团体或软件为本软件提供了帮助,在此统一鸣谢:");
        jLabel3.setForeground(Color.white);
        jLabel3.setFont(new Font("微软雅黑", Font.BOLD, 15));
        jLabel4.setBounds(20,100,460,50);
        jLabel4.setText("成都高新减灾研究所(ICL)  提供地震预警信息");
        jLabel4.setForeground(Color.white);
        jLabel4.setFont(new Font("微软雅黑", Font.BOLD, 13));
        jLabel5.setBounds(20,120,460,50);
        jLabel5.setText("chinaeew.cn  提供Api接口");
        jLabel5.setForeground(Color.white);
        jLabel5.setFont(new Font("微软雅黑", Font.BOLD, 13));
        jLabel6.setBounds(20,140,460,50);
        jLabel6.setText("JQuake  提供软件图标");
        jLabel6.setForeground(Color.white);
        jLabel6.setFont(new Font("微软雅黑", Font.BOLD, 13));
        jLabel7.setBounds(20,160,460,50);
        jLabel7.setText("Kiwimonitor  提供地震横波到达时的音效");
        jLabel7.setForeground(Color.white);
        jLabel7.setFont(new Font("微软雅黑", Font.BOLD, 13));
        jLabel8.setBounds(20,180,460,50);
        jLabel8.setText("SREV  提供地震预警发布时的音效");
        jLabel8.setForeground(Color.white);
        jLabel8.setFont(new Font("微软雅黑", Font.BOLD, 13));
        jLabel9.setBounds(20,210,460,50);
        jLabel9.setText("另外还要感谢ttcctctc、吉尔or小吉等用户对本软件的支持!");
        jLabel9.setForeground(Color.white);
        jLabel9.setFont(new Font("微软雅黑", Font.BOLD, 15));
        //将控件添加至窗口
        jFrame.add(jPanel);
        jPanel.add(jLabel);
        jPanel.add(jLabel1);
        jPanel.add(jLabel2);
        jPanel.add(jLabel3);
        jPanel.add(jLabel4);
        jPanel.add(jLabel5);
        jPanel.add(jLabel6);
        jPanel.add(jLabel7);
        jPanel.add(jLabel8);
        jPanel.add(jLabel9);
        // 设置背景
        jPanel.setBackground(new Color(37, 42, 37, 255));
    }
}
