package com.example.zhengjin.funsettingsuitest.testuitasks;

import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionEnter;
import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionMenu;
import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionMoveDown;
import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionMoveLeft;
import com.example.zhengjin.funsettingsuitest.testuiactions.DeviceActionMoveRight;
import com.example.zhengjin.funsettingsuitest.testuiactions.UiActionsManager;
import com.example.zhengjin.funsettingsuitest.testutils.ShellUtils;
import com.example.zhengjin.funsettingsuitest.testutils.TestHelper;
import com.example.zhengjin.funsettingsuitest.utils.StringUtils;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

import static com.example.zhengjin.funsettingsuitest.testutils.TestConstants.SHORT_WAIT;
import static com.example.zhengjin.funsettingsuitest.testutils.TestConstants.WAIT;

/**
 * Created by zhengjin on 2016/6/14.
 * <p>
 * Include the UI selectors and tasks for file manager apk.
 */
public final class TaskFileManager {

    private static TaskFileManager instance = null;

    private UiDevice device;
    private UiActionsManager action;

    private TaskFileManager() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        action = UiActionsManager.getInstance();
    }

    public static synchronized TaskFileManager getInstance() {
        if (instance == null) {
            instance = new TaskFileManager();
        }
        return instance;
    }

    public void destroyInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    public BySelector getFileManagerHomeTabSelector() {
        return By.res("tv.fun.filemanager:id/activity_fun_fm_tab");
    }

    public BySelector getVideoCategorySelector() {
        return By.res("tv.fun.filemanager:id/category_video");
    }

    public BySelector getCategoryTitleSelector() {
        return By.res("tv.fun.filemanager:id/entity_name");
    }

    public BySelector getCategoryEntiesCountSelector() {
        return By.res("tv.fun.filemanager:id/entity_count");
    }

    public BySelector getMainTitleSelector() {
        return By.res("tv.fun.filemanager:id/activity_sub_title_main");
    }

    public BySelector getSubTitleSelector() {
        return By.res("tv.fun.filemanager:id/activity_sub_title_sub");
    }

    public BySelector getMenuTipsSelector() {
        return By.res("tv.fun.filemanager:id/activity_sub_menu_tips");
    }

    public BySelector getMenuContainerSelector() {
        return By.res("android:id/tv_fun_menu");
    }

    public BySelector getMenuRemoveBtnContainerSelector() {
        return By.res("tv.fun.filemanager:id/menu_item_del_id");
    }

    public BySelector getMenuHideBtnContainerSelector() {
        return By.res("tv.fun.filemanager:id/menu_item_hide_id");
    }

    public BySelector getMenuShowAllBtnContainerSelector() {
        return By.res("tv.fun.filemanager:id/menu_item_show_id");
    }

    public BySelector getMenuBtnTextSelector() {
        return By.res("android:id/tv_fun_menu_text");
    }

    public BySelector getYesBtnOfConfirmDialogSelector() {
        return By.res("tv.fun.filemanager:id/confirm_dialog_btn_confirm");
    }

    public BySelector getCancelBtnOfConfirmDialogSelector() {
        return By.res("tv.fun.filemanager:id/confirm_dialog_btn_cancel");
    }

    public BySelector getTipsOfEmptyDirFromLocalFilesCardSelector() {
        return By.res("tv.fun.filemanager:id/sub_blank_tips");
    }

    public void openLocalFilesCard() {
        final int positionX = 1350;
        final int positionY = 450;
        this.clickOnSpecifiedCardOfFileManagerHome(positionX, positionY);
    }

    public void openCategoryVideoCard() {
        final int positionX = 450;
        final int positionY = 450;
        this.clickOnSpecifiedCardOfFileManagerHome(positionX, positionY);
        this.enterAndWaitForSpecifiedCardOpened();
    }

    public void openCategoryAppCard() {
        final int positionX = 650;
        final int positionY = 450;
        this.clickOnSpecifiedCardOfFileManagerHome(positionX, positionY);
        this.enterAndWaitForSpecifiedCardOpened();
    }

    public void openCategoryMusicCard() {
        final int positionX = 450;
        final int positionY = 750;
        this.clickOnSpecifiedCardOfFileManagerHome(positionX, positionY);
        this.enterAndWaitForSpecifiedCardOpened();
    }

    public void openCategoryPictureCard() {
        final int positionX = 750;
        final int positionY = 750;
        this.clickOnSpecifiedCardOfFileManagerHome(positionX, positionY);
        this.enterAndWaitForSpecifiedCardOpened();
    }

    private void clickOnSpecifiedCardOfFileManagerHome(int positionX, int positionY) {
        Assert.assertTrue("openLocalFilesCard, error click at position.",
                device.click(positionX, positionY));
        ShellUtils.systemWaitByMillis(WAIT);
    }

    private void enterAndWaitForSpecifiedCardOpened() {
        action.doDeviceActionAndWait(new DeviceActionEnter());
        TestHelper.waitForUiObjectEnabled(device.findObject(this.getSubTitleSelector()));
    }

    public void navigateToSpecifiedPath(String path) {
        List<String> dirs = parsePath(path);
        for (String dir : dirs) {
            if (dir.toLowerCase().equals("mnt") || dir.toLowerCase().equals("sdcard")) {
                continue;
            }
            this.clickOnSpecifiedItemFromCurrentDir(dir);
        }
    }

    public void navigateAndOpenSpecifiedFile(String fileAbsPath) {
        this.navigateToSpecifiedPath(fileAbsPath);
    }

    // Item for directory and file
    private void clickOnSpecifiedItemFromCurrentDir(String dirName, boolean flag_bottom) {
        final int ScrollSteps = 5;

        UiScrollable fileList = new UiScrollable(new UiSelector()
                .resourceId("tv.fun.filemanager:id/activity_sub_grid"));
        fileList.setAsVerticalList();
        try {
            fileList.scrollTextIntoView(dirName);
            ShellUtils.systemWaitByMillis(SHORT_WAIT);
            if (flag_bottom) {
                fileList.scrollForward(ScrollSteps);
                ShellUtils.systemWaitByMillis(SHORT_WAIT);
            }
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
            Assert.assertTrue(String.format("clickOnSpecifiedItemFromCurrentDir, "
                    + "error scroll to item %s.", dirName), false);
        }

        UiObject2 dir = device.findObject(By.text(dirName));
        action.doClickActionAndWait(dir);
    }

    private void clickOnSpecifiedItemFromCurrentDir(String dirName) {
        this.clickOnSpecifiedItemFromCurrentDir(dirName, false);
    }

    // path like: android/data/tv.fun.filemanager
    // or: /android/data/tv.fun.filemanager/
    private List<String> parsePath(String path) {
        int levels = 20;
        List<String> dirs = new ArrayList<>(levels);

        String[] tempDirs = path.split("/");
        for (String dir : tempDirs) {
            if (!StringUtils.isEmpty(dir)) {
                dirs.add(dir);
            }
        }
        if (dirs.size() == 0) {
            Assert.assertTrue("parsePath, error: the dirs size is 0.", false);
        }

        return dirs;
    }

    public void moveUntilSpecifiedItemSelected(String itemText) {
        UiObject2 item = device.findObject(By.text(itemText));
        // set focus on 1st item
        action.doRepeatDeviceActionAndWait(new DeviceActionMoveLeft(), 2);
        for (int i = 0, maxMove = 15; i < maxMove; i++) {
            if (item.isSelected()) {
                return;
            }
            action.doDeviceActionAndWait(new DeviceActionMoveRight());
        }

        Assert.assertTrue(String.format("Failed to move on the item %s", itemText), false);
    }

    public void showMenuAndClickBtn(String btnText) {
        boolean isFocused = false;

        showMenuAndRequestFocus();
        UiObject2 btn = device.findObject(By.text(btnText));
        Assert.assertNotNull(String.format("showMenuAndClickBtn, Button %s is not found."
                , btnText), btn);
        UiObject2 btnContainer = btn.getParent();

        for (int i = 0, maxMove = 5; i < maxMove; i++) {
            if (btnContainer.isFocused()) {
                isFocused = true;
                break;
            }
            action.doDeviceActionAndWait(new DeviceActionMoveRight());
        }
        Assert.assertTrue(String.format("showMenuAndClickBtn, Button %s is not focused."
                , btnText), isFocused);

        action.doDeviceActionAndWait(new DeviceActionEnter());
    }

    public void showMenuAndRequestFocus() {
        action.doDeviceActionAndWait(new DeviceActionMenu());
        action.doDeviceActionAndWait(new DeviceActionMoveDown());  // request focus
    }

}
