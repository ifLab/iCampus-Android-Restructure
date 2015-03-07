package org.iflab.icampus.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by hcjcch on 2015/2/13.
 * 网络访问累类，使用第三方库AsyncHttpClient
 */

public class AsyncHttpIc {

    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * get方式
     * @param url 访问的地址
     * @param params 参数
     * @param responseHandler 回调
     */
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * post方式
     * @param url 访问的地址
     * @param params 参数
     * @param responseHandler 回调
     */
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    /**
     * 获取网络资源的绝对路径，因为所有的资源路径，起始部分相同
     * @param relativeUrl 相对路径
     * @return
     */
    private static String getAbsoluteUrl(String relativeUrl) {
        return relativeUrl;
//        return BASE_URL + relativeUrl;
    }

}