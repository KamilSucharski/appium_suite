package common.util

import org.junit.Assume

object AssertUtils {

    fun ignoreTest(reason: String) {
        Assume.assumeTrue(reason, false)
    }

}