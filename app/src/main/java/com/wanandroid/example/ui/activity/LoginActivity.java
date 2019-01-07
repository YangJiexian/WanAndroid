package com.wanandroid.example.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.wanandroid.example.R;
import com.wanandroid.example.base.BaseActivity;
import com.wanandroid.example.core.BaseUrl;
import com.wanandroid.example.core.bean.ArticleListData;
import com.wanandroid.example.core.bean.LoginData;
import com.wanandroid.example.core.http.WanAndroidApis;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 登录模块
 * @author yangjx
 * @date 2019/1/7
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_account_edit)
    EditText account;
    @BindView(R.id.login_password_edit)
    EditText password;
    @BindView(R.id.login_button)
    Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        final ClearableCookieJar cookieJar =
                new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(this));
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(account.getText()) || TextUtils.isEmpty(password.getText())){
                    Toast.makeText(LoginActivity.this,"密码/用户名不能为空",Toast.LENGTH_SHORT).show();
                }else {

                    OkHttpClient client = new OkHttpClient.Builder()
                            .cookieJar(cookieJar)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .baseUrl(BaseUrl.service)
                            .build();

                    WanAndroidApis loginApi = retrofit.create(WanAndroidApis.class);

                    Call<LoginData> loginCall = loginApi.getLoginData(account.getText().toString().trim(),
                            password.getText().toString().trim());
                    loginCall.enqueue(new Callback<LoginData>() {
                        @Override
                        public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                            if (response.body().getErrorCode() == -1){
                                Toast.makeText(LoginActivity.this,response.body().getErrorMsg(),Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginData> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}
