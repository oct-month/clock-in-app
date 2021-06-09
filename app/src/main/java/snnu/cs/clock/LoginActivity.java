package snnu.cs.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import snnu.cs.clock.entity.MyResponse;
import snnu.cs.clock.entity.Student;
import snnu.cs.clock.utils.HttpUtils;
import snnu.cs.clock.utils.LocalStorage;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final String TAG = LoginActivity.class.getSimpleName();

    private HttpUtils httpUtils;
    private LocalStorage storage;

    private EditText editSchoolCode;
    private Button btnLogin;
    private TextView textRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editSchoolCode = findViewById(R.id.edit_school_code);
        btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
        textRegister = findViewById(R.id.text_register);
        textRegister.setOnClickListener(this);
        httpUtils = new HttpUtils();
        storage = new LocalStorage(this);
    }

    private void loginWithCode(String code)
    {
        Student student = new Student(code, null, null);
        httpUtils.post("/api/account/login", student, new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                Toast.makeText(LoginActivity.this, Config.HTTP_FAIL, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String res = response.body().string();
                MyResponse myResponse = new Gson().fromJson(res, MyResponse.class);
                Toast.makeText(LoginActivity.this, myResponse.getMsg(), Toast.LENGTH_SHORT).show();
                // 登录成功，跳转到首页
                if (myResponse.getStatus().equals(Config.SUCCESS))
                {
                    storage.set(Config.SCHOOL_KEY, code);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_login:
                String code = editSchoolCode.getText().toString().trim();
                loginWithCode(code);
                break;
            case R.id.text_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
