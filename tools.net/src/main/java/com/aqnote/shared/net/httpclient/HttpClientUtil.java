/*
 * Copyright (C) 2013-2016 aqnote.com<madding.lip@gmail.com>. 
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation;
 */
package com.aqnote.shared.net.httpclient;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aqnote.shared.charset.Charset;

/**
 * 类HttpClientUtil.java的实现描述：TODO 类实现描述
 * 
 * @author madding.lip Nov 21, 2013 4:08:03 PM
 */
public class HttpClientUtil {

    protected static final Logger logger     = LoggerFactory.getLogger("http_client_util");

    private static HttpClient     httpClient = null;
    static {
        initHttpClient(false);
    }

    public static void initHttpClient(boolean isMy) {
        if (isMy) {
            httpClient = HTTPClientFactory.getMyInstance(HTTPClientFactory.DEFAULT_SO_TIMEOUT,
                                                         HTTPClientFactory.DEFAULT_CON_TIMEOUT);
        } else {
            httpClient = HTTPClientFactory.getInstance(HTTPClientFactory.DEFAULT_SO_TIMEOUT,
                                                       HTTPClientFactory.DEFAULT_CON_TIMEOUT);
        }
    }

    public static String postAsUTF8(String url, Map<String, String> params) {
        String result = null;
        HttpPost httPost = null;

        try {
            httPost = new HttpPost(url);
            List<NameValuePair> nvps = new ArrayList<NameValuePair>(params.size());
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                nvps.add(new BasicNameValuePair(key, params.get(key)));
            }

            httPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            HttpResponse response = httpClient.execute(httPost);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity, Consts.UTF_8);
                return result;
            } else {
                logger.error("true:" + url + ":null");
            }
            logger.debug(String.format("request succeeded:%s, result:%s", url, result));
        } catch (Exception e) {
            logger.error(String.format("Error on post:%s", url), e);
        } finally {
            if (httPost != null) {
                httPost.abort();
            }
        }

        return result;
    }

    public static String get(URI uri, Charset charset) {
        HttpGet httpGet = new HttpGet(uri);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, charset.getCharset());
                logger.debug("true:" + uri + ":" + result);
                return result;
            } else {
                logger.error("true:" + uri + ":null");
            }
        } catch (ClientProtocolException e) {
            logger.error("false:" + uri + ":exception", e);
        } catch (IOException e) {
            logger.error("false:" + uri + ":exception", e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }
        return null;
    }

    public static String get(String uri, Charset charset) {
        HttpGet httpGet = new HttpGet(uri);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, charset.getCharset());
                logger.debug("true:" + uri + ":" + result);
                return result;
            } else {
                logger.error("true:" + uri + ":null");
            }
        } catch (ClientProtocolException e) {
            logger.error("false:" + uri + ":exception", e);
        } catch (IOException e) {
            logger.error("false:" + uri + ":exception", e);
        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
        }
        return null;
    }
}
