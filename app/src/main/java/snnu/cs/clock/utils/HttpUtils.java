package snnu.cs.clock.utils;

import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtils
{
    private static final String BASE_URL = "http://clock.oct-month.top";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");;

    private OkHttpClient client;
    private Gson gson;

    public HttpUtils()
    {
        client = new OkHttpClient();
        gson = new Gson();
    }

    public void post(String path, String token, Object json, Callback callback)
    {
        String data = gson.toJson(json);
        RequestBody body = RequestBody.create(data, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(body)
                .addHeader("Authorization", token)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void post(String path, Object json, Callback callback)
    {
        String data = gson.toJson(json);
        RequestBody body = RequestBody.create(data, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void get(String path, Callback callback)
    {
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
