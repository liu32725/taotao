package com.taotao.web.service;

import com.taotao.common.httpclient.HttpResult;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liuam
 * @version 2018/4/18 0018 上午 11:07 创建文件
 */
@Service
public class ApiService implements BeanFactoryAware {

    @Autowired
    private RequestConfig requestConfig;

    private BeanFactory beanFactory;

    /**
     * 指定GET请求，返回:null,请求失败，String数据，请求成功
     *
     * @param url
     * @return
     * @throws IOException
     */
    public String doGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;

        try {
            response = getHttpClient().execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 带有参数的GET请求，返回:null,请求失败，String数据，请求成功
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public String doGet(String url, Map<String, String> params) throws URISyntaxException {
        URIBuilder builder = null;
        try {
            builder = new URIBuilder(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.setParameter(entry.getKey(), entry.getValue());
        }
        return this.doGet(builder.build().toString());
    }


    /**
     * 带有参数的POST请求
     *
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, String> params) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (params != null) {
            // 设置 post 参数
            List<NameValuePair> parameters = new ArrayList<>(0);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            // 构造一个 form 表单
            UrlEncodedFormEntity formEntity = null;
            try {
                formEntity = new UrlEncodedFormEntity(parameters);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpPost.setEntity(formEntity);
        }
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = getHttpClient().execute(httpPost);
            return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(response.getEntity(), "UTF-8"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    /**
     * 没有参数的POST请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public HttpResult doPost(String url) {
        return doPost(url, null);
    }

    private CloseableHttpClient getHttpClient() {
        // 通过 bean 工厂获取 bean ，保证 HttpClient 对象是多例的
        return this.beanFactory.getBean(CloseableHttpClient.class);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
