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

import java.util.Locale;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation.ConversationType;

public class ConversationActivity extends FragmentActivity {

    private static final String TAG = ConversationActivity.class.getSimpleName();
    
    private TextView mTitle;
    private RelativeLayout mBack;
    
    private String mTargetId, mRongTitle;
    
    private ConversationType mConversationType;
    
    private UserBean loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        loginUser = SharedUtil.readSharedUserInfo(this);
        Intent intent = getIntent();
        setActionBar();
        getIntentData(intent);
    }
    
    private void setActionBar() {
        mTitle = (TextView) findViewById(R.id.actionbar_title);
        mBack = (RelativeLayout) findViewById(R.id.actionbar_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    private void getIntentData(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");
        mRongTitle = intent.getData().getQueryParameter("title");
        mConversationType = ConversationType.valueOf(intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
        enterFragment(mConversationType, mTargetId, mRongTitle);
        setActionBarTitle(mRongTitle);
    }

    private void setActionBarTitle(String title) {
        mTitle.setText(title);
    }

    private void enterFragment(ConversationType conversationType, String targetId, String title) {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversion);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(conversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId)
                .appendQueryParameter("title", title).build();
        fragment.setUri(uri);
    }
    
    private void isReconnect(Intent intent) {
        String token = loginUser.token;
        if (null != intent && intent.getData() != null && intent.getData().getScheme().equals("rong")) {
            if (intent.getData().getQueryParameter("push") != null
                    && intent.getData().getQueryParameter("push").equals("true")) {
                reconnect(token);
            } else {
                if (RongIM.getInstance() == null || RongIM.getInstance().getRongIMClient() != null) {
                    reconnect(token);
                } else {
                    enterFragment(mConversationType, mTargetId, mRongTitle);
                }
            }
        }
    }

    private void reconnect(String token) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e(TAG, "--onTokenIncorrect--");
            }
            @Override
            public void onSuccess(String s) {
                enterFragment(mConversationType, mTargetId, mRongTitle);
            }
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG, "--onError--" + errorCode);
            }
        });
    }

}
