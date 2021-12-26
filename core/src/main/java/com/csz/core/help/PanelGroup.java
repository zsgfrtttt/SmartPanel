package com.csz.core.help;

/**
 * @author caishuzhan
 */
public interface PanelGroup extends ParentPanel,ChildPanel{

    void setKeyboardListener(KeyboardOpListener listener);
}
