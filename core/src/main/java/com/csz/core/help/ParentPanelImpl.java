package com.csz.core.help;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.csz.core.util.Util;

/**
 * @author caishuzhan
 */
public class ParentPanelImpl implements PanelGroup {
    private static float KEYBOARD_MIN_HEIGHT_RATIO = 0.15f;

    private View originView;
    private View rootView;
    private final ChildPanel childPanel;
    private KeyboardOpListener keyboardOpListener;

    private final Rect lastFrame = new Rect();
    private boolean isShowPanel;

    private boolean isRealOpenPanel;
    private boolean isRealOpenKeyboard;

    public ParentPanelImpl(ChildPanel panel) {
        childPanel = panel;
    }

    @Override
    public int calculateHeightMeasureSpec(int heightMeasureSpec) {
        updateFrameSize();
        return heightMeasureSpec;
    }

    @Override
    public void setup(View hostView) {
        originView = hostView;
        rootView = hostView.getRootView();
        if (rootView == null) {
            Log.e("csz", "setup error with root null!");
            return;
        }
    }

    @Override
    public void openPanel() {
        if (isOpenSoftKeyboard()) {
            isShowPanel = true;
            requestCloseKeyboard();
        } else {
            childPanel.openPanel();
            notifyStateChanged();
        }
    }


    @Override
    public void closePanel() {
        childPanel.closePanel();
    }

    @Override
    public boolean isOpen() {
        return childPanel.isOpen();
    }

    private void updateFrameSize() {
        if (rootView != null) {
            final Rect frame = new Rect();
            rootView.getWindowVisibleDisplayFrame(frame);
            int bottomChangeSize = 0;
            if (lastFrame.bottom > 0) {
                bottomChangeSize = frame.bottom - lastFrame.bottom;
            }
            lastFrame.set(frame);
            checkSoftKeyboardAction(bottomChangeSize);
        }
    }

    /**
     * 启动Activity的时候可能会measure多次，所以bottomChangeSize可能键盘不弹出但是 > 0
     *
     * @param bottomChangeSize >0:证明软键盘收起 (初始启动也是>0)  <0:软键盘弹出
     */
    private void checkSoftKeyboardAction(int bottomChangeSize) {
        if (bottomChangeSize > 0 && !isOpenSoftKeyboard()) {
            childPanel.adjustPanelHeight(bottomChangeSize);
            if (isShowPanel) {
                isShowPanel = false;
                openPanel();
            } else {
                notifyStateChanged();
            }
        } else if (bottomChangeSize < 0) {
            closePanel();
        }
    }

    private void notifyStateChanged() {
        boolean isOpenPanel = childPanel.isOpen();
        if (isRealOpenPanel != isOpenPanel) {
            isRealOpenPanel = isOpenPanel;
        }

        boolean isOpenSoftKeyboard = isOpenSoftKeyboard();
        if (isRealOpenKeyboard != isOpenSoftKeyboard) {
            isRealOpenKeyboard = isOpenSoftKeyboard;
        }
    }

    private boolean isOpenSoftKeyboard() {
        Activity activity = Util.findActivity(originView.getContext());
        if (activity != null) {
            View activityRoot = Util.getContentRoot(activity);
            Rect r = new Rect();
            activityRoot.getWindowVisibleDisplayFrame(r);

            int[] location = new int[2];
            Util.getContentRoot(activity).getLocationOnScreen(location);
            int screenHeight = activityRoot.getRootView().getHeight();
            int heightDiff = screenHeight - lastFrame.height() - location[1];
            return heightDiff > screenHeight * KEYBOARD_MIN_HEIGHT_RATIO;
        }
        return false;
    }

    @Override
    public void requestCloseKeyboard() {
        if (keyboardOpListener != null) {
            keyboardOpListener.requestCloseKeyboard();
        }
    }

    @Override
    public void setKeyboardListener(KeyboardOpListener listener) {
        keyboardOpListener = listener;
    }

    @Override
    public void adjustPanelHeight(int heightMeasureSpec) {
        //ignore
    }
}
