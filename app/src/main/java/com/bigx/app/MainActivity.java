package com.bigx.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bigx.adapter.UserAdaptor;
import com.bigx.beans.UserBean;
import com.bigx.tools.BasicTool;
import com.bigx.tools.UserTool;
import com.bigx.utils.SharedUtil;

import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class MainActivity extends AppCompatActivity {
    
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
        
        mUserListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long userId = userAdaptor.getItemId(position);
                RongIM.getInstance().startPrivateChat(MainActivity.this, Long.toString(userId), "");
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (RongIM.getInstance() != null) {
//            switch (item.getItemId()) {
//                case R.id.menu_conversation:
//                    RongIM.getInstance().startPrivateChat(MainActivity.this, "1", "hello");
//                    break;
//                case R.id.menu_conversation_list:
//                    RongIM.getInstance().startConversationList(MainActivity.this);
//                    break;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
