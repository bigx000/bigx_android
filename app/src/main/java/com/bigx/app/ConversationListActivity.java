package com.bigx.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigx.beans.UserBean;
import com.bigx.utils.SharedUtil;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class ConversationListActivity extends FragmentActivity {

    private static final String TAG = ConversationListActivity.class.getSimpleName();

    private TextView mTitleView;
    private RelativeLayout mBackView;
    private UserBean loginUser;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        loginUser = SharedUtil.readSharedUserInfo(this);
        setActionBarTitle();
    }

    private void setActionBarTitle() {
        mTitleView = (TextView) findViewById(R.id.actionbar_title);
        mBackView = (RelativeLayout) findViewById(R.id.actionbar_back);
        mTitleView.setText("聊天列表");
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    private void isReconnect() {
        Intent intent = getIntent();
        String token = loginUser.token;
        if (intent != null && intent.getData() != null && intent.getData().getScheme().equals("rong")) {
            if (intent.getData().getQueryParameter("push") != null
                    && intent.getData().getQueryParameter("push").equals("true")) {
                reconnect(token);
            } else {
                //程序切到后台，收到消息后点击进入,会执行这里
                if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() == null) {
                    reconnect(token);
                } else {
                    enterFragment();
                }
            }
        }
    }

    private void enterFragment() {
        ConversationListFragment fragment = (ConversationListFragment) getSupportFragmentManager().findFragmentById(R.id.conversationlist);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
        fragment.setUri(uri);
    }

    private void reconnect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e(TAG, "--onTokenIncorrect--");
            }
            @Override
            public void onSuccess(String s) {
                enterFragment();
            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG, "--onError--" + errorCode);
            }
        });
    }
}
