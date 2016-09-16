package com.bigx.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SubConversationListActivity extends FragmentActivity {

    private TextView mTitleView;
    private RelativeLayout mBackView;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_conversation_list);
        setActionBarTitle();
    }

    private void setActionBarTitle() {
        mTitleView = (TextView) findViewById(R.id.actionbar_title);
        mBackView = (RelativeLayout) findViewById(R.id.actionbar_back);
        mBackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        type = intent.getData().getQueryParameter("type");
        String title;
        switch (type) {
            case "group":
                title = "聚合群组";
                break;
            case "private":
                title = "聚合单聊";
                break;
            case "discussion":
                title = "聚合讨论组";
                break;
            case "system":
                title = "聚合系统会话";
                break;
            default:
                title = "聚合";
                break;
        }
        mTitleView.setText(title);
    }
}
