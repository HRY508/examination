package com.examination.utils;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;

public class LogReaderUtil {

    public static String download(HttpServletResponse response, String url) {
        //得到要下载的文件
        File file = new File(url);
        //如果文件不存在
        if (!file.exists()) {
            //如果文件不存在，进行处理
            int i = 1 / 0;//系统会报错，除数不能为0.
        }
        InputStream inputStream = null;
        OutputStream outputStream = null;
        //创建缓冲区
        byte[] b = new byte[1024];
        int len = 0;
        try {
            //读取要下载的文件，保存到文件输入流
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            response.setContentType("application/force-download");
            String filename = file.getName();
            //设置响应头，控制浏览器下载该文件
            response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
            response.setContentLength((int) file.length());
            //循环将输入流中的内容读取到缓冲区当中
            while ((len = inputStream.read(b)) != -1) {
                //输出缓冲区的内容到浏览器，实现文件下载
                outputStream.write(b, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static ArrayList<String> readFileNames() throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<>();
        //读取的文件夹
        File folder = new File("logs/");
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                FileReader fileReader = new FileReader("logs/" + file.getName());
                list.add("logs/"+file.getName());
            }
        }
        return list;
    }
}
