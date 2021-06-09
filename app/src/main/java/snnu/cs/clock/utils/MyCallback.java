package snnu.cs.clock.utils;

import android.os.Looper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyCallback implements Callback
{
    private Callback callback;

    public MyCallback(Callback callback)
    {
        this.callback = callback;
    }

    @Override
    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
    {
        Looper.prepare();
        callback.onResponse(call, response);
        Looper.loop();
    }

    @Override
    public void onFailure(@NotNull Call call, @NotNull IOException e)
    {
        e.printStackTrace();
        Looper.prepare();
        callback.onFailure(call, e);
        Looper.loop();
    }
}
