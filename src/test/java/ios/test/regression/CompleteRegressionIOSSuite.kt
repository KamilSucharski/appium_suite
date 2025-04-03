package ios.test.regression

import common.test.regression.CompleteRegressionSuite
import ios.test.helper.SetPlatformIOSHelper
import org.junit.experimental.categories.Categories
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@SuiteClasses(
    SetPlatformIOSHelper::class,
    CompleteRegressionSuite::class
)
@RunWith(Categories::class)
class CompleteRegressionIOSSuite