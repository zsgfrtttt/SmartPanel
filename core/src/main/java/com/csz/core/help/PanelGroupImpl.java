package com.csz.core.help;

import android.view.View;

import com.csz.core.R;

/**
 * @author caishuzhan
 */
public class PanelGroupImpl implements PanelGroup {

    private PanelGroup proxy;

    private KeyboardOpListener tmpKeyboardOpListener;

    @Override
    public void adjustPanelHeight(int heightMeasureSpec) {
        if (proxy != null) {
            proxy.adjustPanelHeight(heightMeasureSpec);
        }
    }

    @Override
    public void requestCloseKeyboard() {
        if (proxy != null) {
            proxy.requestCloseKeyboard();
        }
    }

    @Override
    public void setKeyboardListener(KeyboardOpListener listener) {
        this.tmpKeyboardOpListener = listener;
    }

    @Override
    public int calculateHeightMeasureSpec(int heightMeasureSpec) {
        return proxy == null ? heightMeasureSpec : proxy.calculateHeightMeasureSpec(heightMeasureSpec);
    }

    @Override
    public void setup(View hostView) {
        if (hostView.getId() == R.id.childPanel) {
            proxy = new ChildPanelImpl();
        } else {
            View childPanel = hostView.findViewById(R.id.childPanel);
            if (!(childPanel instanceof ChildPanel)) {
                throw new RuntimeException("Panel child can't null. You must set child id:childPanel implements ChildPanel");
            }
            proxy = new ParentPanelImpl((ChildPanel)childPanel);
        }
        proxy.setKeyboardListener(tmpKeyboardOpListener);
        proxy.setup(hostView);
    }

    @Override
    public void openPanel() {
        if (proxy != null) {
            proxy.openPanel();
        }
    }

    @Override
    public void closePanel() {
        if (proxy != null) {
            proxy.closePanel();
        }
    }

    @Override
    public boolean isOpen() {
        return proxy != null && proxy.isOpen();
    }
}
