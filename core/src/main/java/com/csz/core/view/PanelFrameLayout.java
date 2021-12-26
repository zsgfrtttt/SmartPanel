package com.csz.core.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.csz.core.help.KeyboardOpListener;
import com.csz.core.help.PanelGroup;
import com.csz.core.help.PanelGroupImpl;

import androidx.annotation.Nullable;


/**
 * @author caishuzhan
 */
public class PanelFrameLayout extends FrameLayout implements PanelGroup {
    private PanelGroup panelGroup;

    public PanelFrameLayout(Context context) {
        this(context, null);
    }

    public PanelFrameLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        panelGroup = new PanelGroupImpl();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            panelGroup.setup(this);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = panelGroup.calculateHeightMeasureSpec(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public int calculateHeightMeasureSpec(int heightMeasureSpec) {
        return panelGroup.calculateHeightMeasureSpec(heightMeasureSpec);
    }

    @Override
    public void setup(View hostView) {
    }

    @Override
    public void openPanel() {
        panelGroup.openPanel();
    }

    @Override
    public void closePanel() {
        panelGroup.closePanel();
    }

    @Override
    public boolean isOpen() {
        return panelGroup.isOpen();
    }

    @Override
    public void adjustPanelHeight(int heightMeasureSpec) {
        panelGroup.adjustPanelHeight(heightMeasureSpec);
    }

    @Override
    public void requestCloseKeyboard() {
        panelGroup.requestCloseKeyboard();
    }

    @Override
    public void setKeyboardListener(KeyboardOpListener listener) {
        panelGroup.setKeyboardListener(listener);
    }
}
