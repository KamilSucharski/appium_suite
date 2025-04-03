package common.test.regression

import common.test.sample.SampleSuite
import org.junit.experimental.categories.Categories
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@SuiteClasses(SampleSuite::class)
@RunWith(Categories::class)
class CompleteRegressionSuite