package com.tencent.helloui3;

import android.content.Context;
import android.graphics.Rect;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import com.tencent.helloui3.util.Utils;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.content.ContentValues.TAG;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 * adb shell
 * pm grant com.tencent.helloui3 'android.permission.WRITE_EXTERNAL_STORAGE'
   pm grant com.tencent.helloui3 'android.permission.READ_EXTERNAL_STORAGE'
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ApplicationTest {//} extends ActivityUnitTestCase<Application> {
    private UiDevice mUIDevice = null;
    private Context mContext = null;

    private Logger log = Utils.getLogger(ApplicationTest.class);

    @Before
    public void setUp() throws RemoteException {
        Log.d(TAG, "setUp begin...");
        //初始化
        mUIDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mContext = InstrumentationRegistry.getContext();
        assertThat(mUIDevice, notNullValue());
        assertThat(mContext, notNullValue());

        if (!mUIDevice.isScreenOn()) {
            mUIDevice.wakeUp();
        }
        Log.d(TAG, "setUp finished");
    }


    @Test
    public void cleanKikaKeyboardCach() {
        new ClearCache().action(mUIDevice);
    }
}