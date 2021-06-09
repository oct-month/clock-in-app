package snnu.cs.clock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener
{
    private HttpUtils httpUtils;
    private EditText editSchoolCode;
    private EditText editName;
    private EditText editClassName;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editSchoolCode = findViewById(R.id.edit_school_code);
        editName = findViewById(R.id.edit_user_name);
        editClassName = findViewById(R.id.edit_class_name);
        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);
        httpUtils = new HttpUtils();
    }

    private void registerIn(Student student)
    {
        httpUtils.post("/api/account/register", student, new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                Toast.makeText(RegisterActivity.this, Config.HTTP_FAIL, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException
            {
                String res = response.body().string();
                MyResponse myResponse = new Gson().fromJson(res, MyResponse.class);
                Toast.makeText(RegisterActivity.this, myResponse.getMsg(), Toast.LENGTH_SHORT).show();
                // 注册成功，跳转到登录页
                if (myResponse.getStatus().equals(Config.SUCCESS))
                {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
            case R.id.btn_register:
                String schoolCode = editSchoolCode.getText().toString().trim();
                String name = editName.getText().toString().trim();
                String className = editClassName.getText().toString().trim();
                registerIn(new Student(schoolCode, name, className));
                break;
        }
    }
}
