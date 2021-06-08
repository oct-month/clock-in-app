package snnu.cs.clock.utils;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class LocalStorage
{
    private static final String FILE_NAME = "data";
    private Activity content;

    public LocalStorage(Activity content)
    {
        this.content = content;
    }

    public void set(String key, String value)
    {
        SharedPreferences.Editor editor = content.getSharedPreferences(FILE_NAME, MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String get(String key)
    {
        SharedPreferences pref = content.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        return pref.getString(key, "");
    }
}
