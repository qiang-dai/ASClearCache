package com.tencent.helloui3;

import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import com.tencent.helloui3.util.Utils;
import org.apache.log4j.Logger;
import java.util.List;

/**
 * Created by xinmei365 on 17/3/29.
 */

public class ClearCache {
    UiDevice mUIDevice  = null;
    private Logger log = Utils.getLogger(ClearCache.class);

    public void action(UiDevice mUIDevice) {
        this.mUIDevice = mUIDevice;

        mUIDevice.pressHome();

        UiObject2 setting = mUIDevice.wait(Until.findObject(By.text("设置")), 5000);
        setting.click();

        for(int i = 0; i < 10; i++) {
            UiObject2 app = mUIDevice.wait(Until.findObject(By.text("应用管理")), 5000);
            if (app != null) {
                app.click();
                break;
            }
            List<UiObject2> scrollList = mUIDevice.findObjects(By.clazz("android.widget.ScrollView"));
            UiObject2 scroll = scrollList.get(0);
            scroll.scroll(Direction.DOWN, 0.8f, 3000);
        }

        Utils.sleep(3000, "for listView");
        List<UiObject2> listList = mUIDevice.findObjects(By.clazz("android.widget.ListView"));
        if (listList.size() > 0) {
            UiObject2 list = listList.get(0);
            list.scroll(Direction.DOWN, 13f, 10000);

            clearCache("iKeyboard");
            clearCache("Kika Emoji Keyboard Pro");
            clearCache("Kika Keyboard");
        } else {
            log.error("no listView");
        }
    }
    private void clearCache(String name) {
        List<UiObject2> textList = mUIDevice.findObjects(By.clazz("android.widget.TextView"));
        for(UiObject2 object2 : textList) {
            if (object2 == null) {
                continue;
            }
            String word = object2.getText();
            if (word == null) {
                continue;
            }
            if (word.equals(name)) {
                object2.getParent().click();
                Utils.sleep(2000, "enter");
                //
                UiObject2 mem = mUIDevice.wait(Until.findObject(By.text("存储")), 5000);
                mem.click();
                //
                UiObject2 clear = mUIDevice.wait(Until.findObject(By.text("删除数据")), 5000);
                clear.click();
                Utils.sleep(2000, "wait");

                UiObject2 ok = mUIDevice.wait(Until.findObject(By.text("删除")), 5000);
                if (ok != null) {
                    ok.click();
                }

                UiObject2 cache = mUIDevice.wait(Until.findObject(By.text("清空缓存")), 5000);
                cache.click();

                mUIDevice.pressBack();
                mUIDevice.pressBack();
            }
        }
    }
}
