package com.tilen.investment.common;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.util.StringUtils;

import static org.apache.http.impl.client.HttpClients.createDefault;

/** @Author chaoyu.wang@causascloud.cn @Date 2019/12/13 14:10 @Desc */
@Slf4j
public class HttpClient {
  private static final int SOCKET_DEFAULT_TIMEOUT = 3000;
  private static final int CONNECT_DEFAULT_TIMEOUT = 3000;
  public static String proxyUrl;
  public static boolean isIdc = false;

  public static String doPut(String url, Map<String, String> params, Map<String, Object> headers)
      throws IOException {

    CloseableHttpClient httpClient = createDefault();

    CloseableHttpResponse response = null;
    String result;
    try {
      HttpPut httpPut = new HttpPut(url);
      RequestConfig requestConfig = null;
      if (StringUtils.isEmpty(proxyUrl)) {
        requestConfig =
            RequestConfig.custom()
                .setSocketTimeout(SOCKET_DEFAULT_TIMEOUT)
                .setConnectTimeout(CONNECT_DEFAULT_TIMEOUT)
                .build(); // 设置请求和传输超时时间
      } else {
        HttpHost proxy = new HttpHost(proxyUrl, 80, "http");
        requestConfig =
            RequestConfig.custom()
                .setProxy(proxy)
                .setSocketTimeout(SOCKET_DEFAULT_TIMEOUT)
                .setConnectTimeout(CONNECT_DEFAULT_TIMEOUT)
                .build(); // 设置请求和传输超时时间
      }

      httpPut.setConfig(requestConfig);
      if (headers == null) {
        httpPut.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        httpPut.setHeader(
            "User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");

      } else {
        for (Entry<String, Object> entry : headers.entrySet()) {
          httpPut.setHeader(entry.getKey(), entry.getValue().toString());
        }
      }
      if (!CollectionUtils.isEmpty(params)) {
        List<BasicNameValuePair> basicNameValuePairs = new ArrayList<>();
        for (Entry<String, String> entity : params.entrySet()) {
          basicNameValuePairs.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
        }

        UrlEncodedFormEntity urlEncodedFormEntity =
            new UrlEncodedFormEntity(basicNameValuePairs, Consts.UTF_8);
        httpPut.setEntity(urlEncodedFormEntity);
      }
      response = httpClient.execute(httpPut);
      StatusLine statusLine = response.getStatusLine();
      log.info(
          String.format(
              "request url: %s, params: %s, response status: %s",
              url, JSON.toJSONString(params), statusLine.getStatusCode()));

      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, Consts.UTF_8);
      log.info(String.format("response data: %s", result));
      return result;

    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        log.error("close http client failed", e);
      }
    }
  }

  public static String handlePost(
      String url, String params, Map<String, Object> headers, String proxy) throws IOException {

    CloseableHttpClient httpClient = createDefault();

    CloseableHttpResponse response = null;
    String result;
    try {
      HttpPost httpPost = new HttpPost(url);
      RequestConfig requestConfig = null;
      if (StringUtils.isEmpty(proxy)) {
        requestConfig =
            RequestConfig.custom()
                .setSocketTimeout(SOCKET_DEFAULT_TIMEOUT)
                .setConnectTimeout(CONNECT_DEFAULT_TIMEOUT)
                .build(); // 设置请求和传输超时时间
      } else {
        HttpHost p = new HttpHost(proxy, 80, "http");
        requestConfig =
            RequestConfig.custom()
                .setProxy(p)
                .setSocketTimeout(SOCKET_DEFAULT_TIMEOUT)
                .setConnectTimeout(CONNECT_DEFAULT_TIMEOUT)
                .build(); // 设置请求和传输超时时间
      }

      httpPost.setConfig(requestConfig);
      if (headers == null) {
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
      } else {
        for (Entry<String, Object> entry : headers.entrySet()) {
          httpPost.setHeader(entry.getKey(), entry.getValue().toString());
        }
      }

      StringEntity se = new StringEntity(params, Consts.UTF_8);

      httpPost.setEntity(se);
      response = httpClient.execute(httpPost);
      StatusLine statusLine = response.getStatusLine();
      log.info(
          String.format(
              "request url: %s, params: %s, response status: %s",
              url, JSON.toJSONString(params), statusLine.getStatusCode()));

      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, Consts.UTF_8);
      log.info(String.format("response data: %s", result));
      return result;

    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        log.error("close http client failed", e);
      }
    }
  }

  public static String handleUpload(
      String url, byte[] bytes, Map<String, Object> headers, String proxy) throws IOException {

    CloseableHttpClient httpClient = createDefault();

    CloseableHttpResponse response = null;
    String result;
    try {
      HttpPut httpPut = new HttpPut(url);
      RequestConfig requestConfig = null;
      if (StringUtils.isEmpty(proxy)) {
        requestConfig =
            RequestConfig.custom()
                .setSocketTimeout(SOCKET_DEFAULT_TIMEOUT * 10)
                .setConnectTimeout(CONNECT_DEFAULT_TIMEOUT * 10)
                .build(); // 设置请求和传输超时时间
      } else {
        HttpHost p = new HttpHost(proxy, 80, "http");
        requestConfig =
            RequestConfig.custom()
                .setProxy(p)
                .setSocketTimeout(SOCKET_DEFAULT_TIMEOUT * 10)
                .setConnectTimeout(CONNECT_DEFAULT_TIMEOUT * 10)
                .build(); // 设置请求和传输超时时间
      }

      httpPut.setConfig(requestConfig);
      if (headers == null) {
        httpPut.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
      } else {
        for (Entry<String, Object> entry : headers.entrySet()) {
          httpPut.setHeader(entry.getKey(), entry.getValue().toString());
        }
      }

      ByteArrayEntity byteArrayEntity = new ByteArrayEntity(bytes);
      httpPut.setEntity(byteArrayEntity);
      response = httpClient.execute(httpPut);
      StatusLine statusLine = response.getStatusLine();
      log.info(
          String.format("request url: %s, response status: %s", url, statusLine.getStatusCode()));
      log.info("handle upload url={},res={}", url, JsonMapper.toJson(response));
      if (statusLine.getStatusCode() == 200 && statusLine.getReasonPhrase().equals("OK")) {
        return "SUCCESS";
      } else {
        log.error("handle upload failed!---url={}", url);
        return statusLine.getReasonPhrase();
      }
    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        log.error("close http client failed", e);
      }
    }
  }

  public static byte[] download(String url, Map<String, Object> headers) {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet(url);
    if (headers == null) {
    } else {
      for (Entry<String, Object> entry : headers.entrySet()) {
        httpGet.setHeader(entry.getKey(), entry.getValue().toString());
      }
    }

    CloseableHttpResponse response = null;
    if (!isIdc) {
      try {
        response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        byte[] bytes = EntityUtils.toByteArray(responseEntity);
        return bytes;
      } catch (IOException e) {
        log.error("请求url-{}失败", url, e);
      }

    } else {
      try {
        if (!StringUtils.isEmpty(proxyUrl)) {
          HttpHost proxy = new HttpHost(proxyUrl, 80, "http");
          RequestConfig requestConfig =
              RequestConfig.custom()
                  .setProxy(proxy)
                  .setSocketTimeout(SOCKET_DEFAULT_TIMEOUT * 10)
                  .setConnectTimeout(CONNECT_DEFAULT_TIMEOUT * 10)
                  .build(); // 设置请求和传输超时时间
          httpGet.setConfig(requestConfig);
        }
        response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        byte[] result = EntityUtils.toByteArray(responseEntity);
        return result;
      } catch (ClientProtocolException e) {
        e.printStackTrace();
      } catch (ParseException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          // 释放资源
          if (httpClient != null) {
            httpClient.close();
          }
          if (response != null) {
            response.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  public static <T> T doGet(String url, Class<T> clazz) {
    CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet(url);
    CloseableHttpResponse response = null;
    if (!isIdc) {
      try {
        response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        String result = EntityUtils.toString(responseEntity);
        return JsonMapper.getObj(result, clazz);
      } catch (IOException e) {
        log.error("请求url-{}失败", url);
      }

    } else {
      try {
        if (!StringUtils.isEmpty(proxyUrl)) {
          HttpHost proxy = new HttpHost(proxyUrl, 80, "http");
          RequestConfig requestConfig =
              RequestConfig.custom()
                  .setProxy(proxy)
                  .setSocketTimeout(SOCKET_DEFAULT_TIMEOUT)
                  .setConnectTimeout(CONNECT_DEFAULT_TIMEOUT)
                  .build(); // 设置请求和传输超时时间
          httpGet.setConfig(requestConfig);
        }

        response = httpClient.execute(httpGet);
        HttpEntity responseEntity = response.getEntity();
        String result = EntityUtils.toString(responseEntity);
        return JsonMapper.getObj(result, clazz);
      } catch (ClientProtocolException e) {
        log.error("请求url-{}失败", url);
      } catch (ParseException e) {
        log.error("请求url-{}失败", url);
      } catch (IOException e) {
        log.error("请求url-{}失败", url);
      } finally {
        try {
          // 释放资源
          if (httpClient != null) {
            httpClient.close();
          }
          if (response != null) {
            response.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  public static void main(String[] args) {

    byte[] bytes =
        HttpClient.download(
            "http://recordcloud.170.com/vnum/recordings/download/1000004_67942427569375355531581907914773_15804514026_17090150995_18903614714_2020021710.mp3",
            null);
    File file = new File("/users/tilenmac/desktop/m.mp3");

    BufferedOutputStream bos = null;
    FileOutputStream fos = null;
    try {

      fos = new FileOutputStream(file);
      bos = new BufferedOutputStream(fos);
      bos.write(bytes);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (bos != null) {
        try {
          bos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    System.out.println(bytes);
  }

  public String doPost(
      String url, Map<String, String> params, Map<String, Object> headers, String proxyUrl)
      throws IOException {
    if (StringUtils.isEmpty(url) || params == null || params.isEmpty()) {
      return null;
    }

    CloseableHttpClient httpClient = createDefault();

    CloseableHttpResponse response = null;
    String result;
    try {
      HttpPost httpPost = new HttpPost(url);
      RequestConfig requestConfig = null;
      log.info("===direct");
      requestConfig =
          RequestConfig.custom()
              .setSocketTimeout(SOCKET_DEFAULT_TIMEOUT)
              .setConnectTimeout(CONNECT_DEFAULT_TIMEOUT)
              .build(); // 设置请求和传输超时时间

      httpPost.setConfig(requestConfig);
      if (headers == null) {
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
      } else {
        for (Entry<String, Object> entry : headers.entrySet()) {
          httpPost.setHeader(entry.getKey(), entry.getValue().toString());
        }
      }

      List<BasicNameValuePair> basicNameValuePairs = new ArrayList<>();
      for (Entry<String, String> entity : params.entrySet()) {
        basicNameValuePairs.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
      }

      UrlEncodedFormEntity urlEncodedFormEntity =
          new UrlEncodedFormEntity(basicNameValuePairs, Consts.UTF_8);
      httpPost.setEntity(urlEncodedFormEntity);

      log.info("===httpPost = " + httpPost.toString());
      response = httpClient.execute(httpPost);
      StatusLine statusLine = response.getStatusLine();
      log.info(
          String.format(
              "===request url: %s, params: %s, response status: %s",
              url, JSON.toJSONString(params), statusLine.getStatusCode()));

      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, Consts.UTF_8);
      log.info(String.format("response data: %s", result));
      return result;

    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        log.error("close http client failed", e);
      }
    }
  }

  public String doPostWhthoutProxy(
      String url, Map<String, String> params, Map<String, Object> headers) throws IOException {
    if (StringUtils.isEmpty(url) || params == null || params.isEmpty()) {
      return null;
    }

    CloseableHttpClient httpClient = createDefault();

    CloseableHttpResponse response = null;
    String result;
    try {
      HttpPost httpPost = new HttpPost(url);
      RequestConfig requestConfig = null;
      log.info("===direct");
      requestConfig =
          RequestConfig.custom()
              .setSocketTimeout(SOCKET_DEFAULT_TIMEOUT)
              .setConnectTimeout(CONNECT_DEFAULT_TIMEOUT)
              .build(); // 设置请求和传输超时时间

      httpPost.setConfig(requestConfig);
      if (headers == null) {
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
      } else {
        for (Entry<String, Object> entry : headers.entrySet()) {
          httpPost.setHeader(entry.getKey(), entry.getValue().toString());
        }
      }

      List<BasicNameValuePair> basicNameValuePairs = new ArrayList<>();
      for (Entry<String, String> entity : params.entrySet()) {
        basicNameValuePairs.add(new BasicNameValuePair(entity.getKey(), entity.getValue()));
      }

      UrlEncodedFormEntity urlEncodedFormEntity =
          new UrlEncodedFormEntity(basicNameValuePairs, Consts.UTF_8);
      httpPost.setEntity(urlEncodedFormEntity);

      log.info("===httpPost = " + httpPost.toString());
      response = httpClient.execute(httpPost);
      StatusLine statusLine = response.getStatusLine();
      log.info(
          String.format(
              "===request url: %s, params: %s, response status: %s",
              url, JSON.toJSONString(params), statusLine.getStatusCode()));

      HttpEntity entity = response.getEntity();
      result = EntityUtils.toString(entity, Consts.UTF_8);
      log.info(String.format("response data: %s", result));
      return result;

    } catch (IOException e) {
      throw e;
    } finally {
      try {
        if (response != null) {
          response.close();
        }
        if (httpClient != null) {
          httpClient.close();
        }
      } catch (IOException e) {
        log.error("close http client failed", e);
      }
    }
  }
}
