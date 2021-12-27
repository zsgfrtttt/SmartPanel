package com.csz.panel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.csz.core.help.KeyboardOpListener;
import com.csz.core.util.Util;
import com.csz.core.view.PanelLinearLayout;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private PanelLinearLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.et);
        parent = findViewById(R.id.layout_content);
        parent.setKeyboardListener(new KeyboardOpListener() {
            @Override
            public void requestCloseKeyboard() {
                Util.hideKeyboard(editText);
            }
        });

    }

    public void onClickPanel(View view) {
        if (parent.isOpen()) {
            Util.showKeyboard(editText);
        } else {
            parent.openPanel();
        }
    }

    @Override
    public void onBackPressed() {
        if (parent.isOpen()) {
            parent.closePanel();
            return;
        }
        super.onBackPressed();
    }
}