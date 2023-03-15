package cn.dxr.quake.app;

import cn.dxr.quake.Utils.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StartDataWriter {

    // 写入初始数据
    public void write() {
        String url = HttpUtil.sendGet("https://mobile.chinaeew.cn", "/v1/earlywarnings?updates=&start_at=");
        JSONObject jsonObj = JSON.parseObject(url);
        JSONArray jsonArray = jsonObj.getJSONArray("data");
        JSONObject json = jsonArray.getJSONObject(0);
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("ID", json.getString("eventId"));
        BufferedWriter bufferedWriter;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter("Files\\start.json"));
            bufferedWriter.write(jsonObject2.toString());
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
