package com.bigx.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ConversationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);
        getSupportActionBar().setTitle("聊天列表");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
