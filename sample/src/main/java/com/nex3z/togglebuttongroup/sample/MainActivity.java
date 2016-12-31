package com.nex3z.togglebuttongroup.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMultiSelect = (Button) findViewById(R.id.btn_multi_select_sample);
        btnMultiSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MultiSelectSampleActivity.class);
                startActivity(intent);
            }
        });

        Button btnSingleSelect = (Button) findViewById(R.id.btn_single_select_sample);
        btnSingleSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SingleSelectSampleActivity.class);
                startActivity(intent);
            }
        });

        Button btnTag = (Button) findViewById(R.id.btn_tag_sample);
        btnTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TagSampleActivity.class);
                startActivity(intent);
            }
        });
    }

}
