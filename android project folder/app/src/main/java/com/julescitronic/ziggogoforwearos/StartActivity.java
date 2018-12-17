package com.julescitronic.ziggogoforwearos;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

public class StartActivity extends WearableActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mTextView = (TextView) findViewById(R.id.text);

        Intent skip = new Intent(this, channelOverview.class);
        startActivity(skip);

        // Enables Always-on
        setAmbientEnabled();
    }
}
