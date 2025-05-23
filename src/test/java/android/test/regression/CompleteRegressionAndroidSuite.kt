package android.test.regression

import android.test.helper.SetPlatformAndroidHelper
import common.test.regression.CompleteRegressionSuite
import org.junit.experimental.categories.Categories
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@SuiteClasses(
    SetPlatformAndroidHelper::class,
    CompleteRegressionSuite::class
)
@RunWith(Categories::class)
class CompleteRegressionAndroidSuite