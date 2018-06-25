package com.whoiszxl.xl.delegates;

/**
 * @author whoiszxl
 *
 */
public abstract class XlDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends XlDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
