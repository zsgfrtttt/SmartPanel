package com.csz.core.help;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;

/**
 * @author caishuzhan
 */
public class ParentPanelImpl implements PanelGroup {

    private View rootView;
    private final ChildPanel childPanel;
    private KeyboardOpListener keyboardOpListener;

    private final Rect lastFrame = new Rect();
    private int lastWindowInsetBottom;
    private int displayHeightWithInsetBottom;
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
        rootView = hostView.getRootView();
        if (rootView == null) {
            Log.e("csz", "setup error with root null!");
            return;
        }
        final WindowManager windowManager = (WindowManager) hostView.getContext().getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        displayHeightWithInsetBottom = size.y + getWindowStableInsetBottom();
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
            lastWindowInsetBottom = getWindowStableInsetBottom();

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
     * 获取底部装饰的高度
     *
     * @return
     */
    private int getWindowStableInsetBottom() {
        if (rootView == null) {
            return 0;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            final WindowInsets rootWindowInsets = rootView.getRootWindowInsets();
            return rootWindowInsets.getStableInsetBottom();
        }
        return 0;
    }

    /**
     * 启动Activity的时候可能会measure多次，所以bottomChangeSize可能键盘不弹出但是 > 0
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
        return lastFrame.bottom != 0 && lastFrame.bottom + lastWindowInsetBottom != displayHeightWithInsetBottom && lastFrame.height() + lastWindowInsetBottom != displayHeightWithInsetBottom;
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
