package cn.dxr.quake.GUI;

import cn.dxr.quake.Utils.SoundUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SettingsDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboWarningIntensity;
    private JButton buttonTestEEWSFX;
    private JButton buttonTestSWAVESFX;
    private JTextField editLatitude;
    private JTextField editLongitude;
    private JTextField editPostURL;

    public SettingsDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle("设置");
        setIconImage(Toolkit.getDefaultToolkit().getImage("Files/img/icon.png"));
        setLocationRelativeTo(null);
        pack();

        buttonOK.addActionListener(e->onOK());
        buttonCancel.addActionListener(e->onCancel());
        LoadSettings();
        setVisible(true);//设为模态后，会造成阻塞
    }

    private void LoadSettings(){
        File fSettings=new File("Files/settings.json");
        File fInt=new File("Files/int.json");
        try{
            String strSettings= FileUtils.readFileToString(fSettings);
            JSONObject jsonSettings= JSON.parseObject(strSettings);
            editLatitude.setText(jsonSettings.getString("Lat"));
            editLongitude.setText(jsonSettings.getString("Lng"));
            editPostURL.setText(jsonSettings.getString("PushURL"));
            String strInt=FileUtils.readFileToString(fInt);
            JSONObject jsonInt=JSON.parseObject(strInt);
            switch (jsonInt.getString("Int")){
                case "-1000.0":comboWarningIntensity.setSelectedIndex(0);break;
                case "2.0":comboWarningIntensity.setSelectedIndex(1);break;
                case "3.0":comboWarningIntensity.setSelectedIndex(2);break;
                case "4.0":comboWarningIntensity.setSelectedIndex(3);break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        SoundUtil soundUtil=new SoundUtil();
        buttonTestEEWSFX.addActionListener(e->soundUtil.playSound("sounds/First.wav"));
        buttonTestSWAVESFX.addActionListener(e->soundUtil.playSound("sounds/Arrive.wav"));
    }

    private void onOK() {
        try{
            JSONObject jsonSettings=new JSONObject(),jsonInt=new JSONObject();
            jsonSettings.put("Lat",editLatitude.getText());
            jsonSettings.put("Lng",editLongitude.getText());
            jsonSettings.put("PushURL",editPostURL.getText());
            final String[]intensityStrings={"-1000.0","2.0","3.0","4.0"};
            jsonInt.put("Int",intensityStrings[comboWarningIntensity.getSelectedIndex()]);
            BufferedWriter bufferedWriterSettings=new BufferedWriter(new FileWriter("Files/settings.json"));
            bufferedWriterSettings.write(jsonSettings.toString());
            bufferedWriterSettings.close();
            BufferedWriter bufferedWriterInt=new BufferedWriter(new FileWriter("Files/int.json"));
            bufferedWriterInt.write(jsonInt.toString());
            bufferedWriterInt.close();
            JOptionPane.showMessageDialog(this,"设置保存成功！");
        }catch (IOException e){
            JOptionPane.showMessageDialog(this,"设置保存失败！\n"+e,"错误",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        dispose();
    }

    private void onCancel(){
        dispose();
    }
}
