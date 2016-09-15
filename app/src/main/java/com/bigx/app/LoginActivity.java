package com.bigx.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigx.beans.UserBean;
import com.bigx.tools.BasicTool;
import com.bigx.tools.UserTool;
import com.bigx.utils.SharedUtil;

public class LoginActivity extends AppCompatActivity {
    
    private TextView mAccountView, mPasswordView;
    
    private Button mLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        mAccountView = (TextView) findViewById(R.id.login_account);
        mPasswordView = (TextView) findViewById(R.id.login_password);
        mLoginBtn = (Button) findViewById(R.id.login_btn_in);
        init();
    }
    
    private void init() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = mAccountView.getText().toString();
                String password = mPasswordView.getText().toString();
                UserBean userBean = UserTool.getUser(account, password);
                if (null == userBean) {
                    BasicTool.showToast(LoginActivity.this, "账号或者密码错误");
                    return;
                }
                SharedUtil.writeSharedUserInfo(LoginActivity.this, userBean);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
