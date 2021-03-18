package android.test.regression

import android.test.sample.SampleAndroidSuite
import org.junit.experimental.categories.Categories
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@SuiteClasses(SampleAndroidSuite::class)
@RunWith(Categories::class)
class CompleteRegressionAndroidSuite 