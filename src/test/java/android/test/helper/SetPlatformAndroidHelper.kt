package android.test.helper

import common.util.junit.Helper
import common.util.Platform
import common.util.platform
import org.junit.Test
import org.junit.experimental.categories.Category

class SetPlatformAndroidHelper {

    @Test
    @Category(Helper::class)
    fun setPlatformAndroid() {
        platform = Platform.Android
    }

}