package com.csz.core.help;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * @author caishuzhan
 */
public class ChildPanelImpl implements PanelGroup {

    private View target;
    private int panelHeight;
    private int displayHeight;

    @Override
    public void adjustPanelHeight(int heightMeasureSpec) {
        if (heightMeasureSpec > 0 && heightMeasureSpec < displayHeight && heightMeasureSpec != panelHeight) {
            panelHeight = heightMeasureSpec;
        }
    }

    @Override
    public int calculateHeightMeasureSpec(int heightMeasureSpec) {
        if (panelHeight > 0) {
            int newSpecMode = View.MeasureSpec.EXACTLY;
            int newSpecSize = panelHeight;
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(newSpecSize, newSpecMode);
        }
        return heightMeasureSpec;
    }

    @Override
    public void setup(View hostView) {
        this.target = hostView;
        final WindowManager windowManager = (WindowManager) hostView.getContext().getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        displayHeight = size.y;
    }

    @Override
    public void openPanel() {
        target.setVisibility(View.VISIBLE);
    }

    @Override
    public void closePanel() {
        target.setVisibility(View.GONE);
    }

    @Override
    public boolean isOpen() {
        return target.getVisibility() != View.GONE;
    }

    @Override
    public void requestCloseKeyboard() {
        //ignore
    }

    @Override
    public void setKeyboardListener(KeyboardOpListener listener) {
        //ignore
    }
}
