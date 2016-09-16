package com.bigx.app;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigx.adapter.user.UserAdaptor;
import com.bigx.beans.UserBean;
import com.bigx.tools.BasicTool;
import com.bigx.tools.UserTool;
import com.bigx.utils.SharedUtil;

import java.util.HashMap;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class MainActivity extends AppCompatActivity implements RongIM.UserInfoProvider {
    
    private String token;
    
    private UserBean loginUser;
    
    private List<UserBean> users;
    
    private UserAdaptor userAdaptor;
    
    private ListView mUserListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserListView = (ListView) findViewById(R.id.user_list);
        loginUser = SharedUtil.readSharedUserInfo(this);
        token = loginUser.token;
        users = UserTool.getUsers(loginUser.account);
        userAdaptor = new UserAdaptor(this, users);
        mUserListView.setAdapter(userAdaptor);
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e("MainActivity", "--onTokenError--");
                BasicTool.showToast(MainActivity.this, "获取用户状态失败");
            }
            @Override
            public void onSuccess(String s) {
                Log.e("MainActivity", "--onSuccess--" + s);
            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e("MainActivity", "--onError--" + errorCode);
            }
        });
        RongIM.setUserInfoProvider(this, true);
        mUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserBean userBean = (UserBean) userAdaptor.getItem(position);
                RongIM.getInstance().startPrivateChat(MainActivity.this, Long.toString(userBean.userId), userBean.name);
            }
        });
    }

    @Override
    public UserInfo getUserInfo(String s) {
        UserBean userBean = UserTool.getUserById(s);
        UserInfo userInfo = new UserInfo(Long.toString(userBean.userId), userBean.name, Uri.parse(userBean.portraitUri));
        return userInfo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (RongIM.getInstance() != null) {
            switch (item.getItemId()) {
                case R.id.menu_conversation_list:
                    RongIM.getInstance().startConversationList(MainActivity.this, new HashMap<String, Boolean>());
                    break;
                case R.id.menu_sub_conversation_list:
                    RongIM.getInstance().startSubConversationList(MainActivity.this, Conversation.ConversationType.GROUP);
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
