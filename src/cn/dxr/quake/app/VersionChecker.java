package cn.dxr.quake.app;

import cn.dxr.quake.Utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class VersionChecker {
    public static final String currentVersion="1.6.2";

    // 获取软件当前版本
    public String getVersion() throws IOException {
        return currentVersion;
    }

    private long VersionStringToInt(String s){
        String[]parts=s.split("\\.");
        long v=0;
        long[]multi={0x7F000000,0xFF0000,0xFF00,0xFF};
        for(int i=0;i<Math.min(4,parts.length);i++){
            v=v+Integer.parseInt(parts[i])*multi[i];
        }
        return v;
    }

    // 获取软件最新版本,并检查版本
    public void checkVersion() throws IOException {
        String url = HttpUtil.sendGet("https://dxrxie2019.github.io","/SCEEW/version.json");
        JSONObject jsonObject = JSON.parseObject(url);
        String latest = jsonObject.getString("latest");
        if(VersionStringToInt(getVersion())<VersionStringToInt(latest)) {
            JOptionPane.showMessageDialog(null, "您当前的软件版本已过期，请至https://gitee.com/dxr2222/earthquake-warning/releases下载最新版!");
        }
    }
}