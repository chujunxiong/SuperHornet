package com.chujunxiong.superhornet;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

/**
 *获取请求对象类（受限于Volley库，这个类比较啰嗦）
 * Created by looper on 2016/4/18.
 */
public class GetRequestObject {

    private GetRequestObject() {}

    /**
     * 单例
     * @return
     */
    public static GetRequestObject getDefault() {
        return RequestObjectHolder.instance;
    }

    private static class RequestObjectHolder {
        private static final GetRequestObject instance = new GetRequestObject();
    }


    /**
     * 获取请求对象
     * @param map
     * @return
     */
    public Request getRequestObject(HashMap<String,Object> map) {
        int method = 0;
        int requesttype = 0;
        String URL = null;
        ResponseListener mListener = null;
        Request request = null;

        Set<String> key = map.keySet();
        for (String name:key) {
            if(name.equals(RequestParameter.Method)) {
                method = (int)map.get(name);
            }else if(name.equals(RequestParameter.URL)) {
                URL = (String)map.get(name);
            }else if(name.equals(RequestParameter.LISTENER)) {
                mListener = (ResponseListener) map.get(name);
            }else if(name.equals(RequestParameter.REQUEST_TYPE)) {
                requesttype = (int)map.get(name);
            }
        }

        switch (requesttype) {
            case 0: request = getJsonArrayRequest(method,URL,mListener);
                break;
            case 1: request = getJsonObjectRequest(method,URL,mListener);
                break;
            case 2: request = getStringRequest(method,URL,mListener);
                break;
        }
        return  request;
    }


    /**
     * 返回JsonArray请求对象
     * @param Method 方法（get or post）
     * @param URL 请求地址
     * @param listener  结果响应监听器
     * @return
     */
    private JsonArrayRequest getJsonArrayRequest(int Method, String URL, final ResponseListener listener) {
        return new JsonArrayRequest(
                Method,
                URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listener.sucess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.error(error);
                    }
                }
        );
    }


    /**
     * 返回JsonObject请求对象
     * @param Method  请求方法(post or get)
     * @param URL 请求地址
     * @param listener  结果响应监听器
     * @return
     */
    private JsonObjectRequest getJsonObjectRequest(int Method, String URL, final ResponseListener listener) {
        return  new JsonObjectRequest(
                Method,
                URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.sucess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.error(error);
                    }
                }
        );
    }


    /**
     * 返回String请求对象
     * @param Method  请求方法（get or post）
     * @param URL 请求地址
     * @param listener 结果响应监听器
     * @return
     */
    private StringRequest getStringRequest(int Method, String URL, final ResponseListener listener) {
        return  new StringRequest(
                Method,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        listener.sucess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.error(error);
                    }
                }
        );
    }



}
