package com.csz.core.help;

import android.view.View;

/**
 * @author caishuzhan
 */
public interface Panel {

    int calculateHeightMeasureSpec(int heightMeasureSpec);

    void setup(View hostView);

    void openPanel();

    void closePanel();

    boolean isOpen();

}
