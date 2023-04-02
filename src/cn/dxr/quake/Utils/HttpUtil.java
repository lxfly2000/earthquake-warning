package cn.dxr.quake.Utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpUtil {
    private static HttpClient httpClient=null;
    private static HttpClient getHttpClient(){
        if(httpClient==null){
            httpClient=HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .followRedirects(HttpClient.Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();
        }
        return httpClient;
    }

    public static String sendGet(String url, String param) {
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create(url+param))
                .timeout(Duration.ofSeconds(60))
                .header("Accept","*/*")
                //.header("Connection","Keep-Alive")
                .header("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)")
                .GET().build();
        try {
            HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (Exception e){
            System.err.println("[ERROR]发送GET请求出现异常！");
            e.printStackTrace();
        }
        return "";
    }

    public static String sendPost(String url, String param) {
        HttpRequest request=HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(60))
                .header("Accept","*/*")
                //.header("Connection","Keep-Alive")
                .header("User-Agent","Mozilla/5.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)")
                .POST(HttpRequest.BodyPublishers.ofString(param)).build();
        try {
            HttpResponse<String> response = getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        }catch (Exception e){
            System.err.println("[ERROR]发送POST请求出现异常！");
            e.printStackTrace();
        }
        return "";
    }
}