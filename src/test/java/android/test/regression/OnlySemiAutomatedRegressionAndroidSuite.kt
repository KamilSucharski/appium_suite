package android.test.regression

import common.junit.Helper
import common.junit.SemiAutomated
import org.junit.experimental.categories.Categories
import org.junit.experimental.categories.Categories.IncludeCategory
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@SuiteClasses(CompleteRegressionAndroidSuite::class)
@IncludeCategory(SemiAutomated::class, Helper::class)
@RunWith(Categories::class)
class OnlySemiAutomatedRegressionAndroidSuite 