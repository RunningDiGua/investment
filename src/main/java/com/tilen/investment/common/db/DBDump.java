package com.tilen.investment.common.db;

import java.io.BufferedReader;
// import java.io.File;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBDump {
  public static void main(String[] args) {
    //
    System.out.println("---start----");
    dump();
    System.out.println("---done----");
  }

  public static boolean dump() {

    String hostIP = "127.0.0.1";
    String userName = "root";
    String password = "Tilen0123++123";
    String savePath = "~/Users/wangchangdong/Desktop/";
    String fileName =
        "SetofBook" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".sql";
    String databaseName = "athena";
    File saveFile = new File(savePath);
    // 如果目录不存在
    if (!saveFile.exists()) {
      // 创建文件夹
      saveFile.mkdirs();
    }
    if (!savePath.endsWith(File.separator)) {
      savePath = savePath + File.separator;
    }

    PrintWriter printWriter = null;
    BufferedReader bufferedReader = null;
    try {
      printWriter =
          new PrintWriter(
              new OutputStreamWriter(new FileOutputStream(savePath + fileName), "utf8"));
      Process process =
          Runtime.getRuntime()
              .exec(
                  " mysqldump -h"
                      + hostIP
                      + " -u"
                      + userName
                      + " -p"
                      + password
                      + " --set-charset=UTF8 "
                      + databaseName);
      InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "utf8");
      bufferedReader = new BufferedReader(inputStreamReader);
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        printWriter.println(line);
      }
      printWriter.flush();
      // 0 表示线程正常终止。
      if (process.waitFor() == 0) {
        return true;
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
        if (printWriter != null) {
          printWriter.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return false;
  }
}
