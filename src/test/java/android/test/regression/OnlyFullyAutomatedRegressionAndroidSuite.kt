package android.test.regression

import common.junit.SemiAutomated
import org.junit.experimental.categories.Categories
import org.junit.experimental.categories.Categories.ExcludeCategory
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@SuiteClasses(CompleteRegressionAndroidSuite::class)
@ExcludeCategory(SemiAutomated::class)
@RunWith(Categories::class)
class OnlyFullyAutomatedRegressionAndroidSuite 