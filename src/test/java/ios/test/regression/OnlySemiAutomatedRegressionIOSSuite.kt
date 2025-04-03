package ios.test.regression

import common.util.junit.Helper
import common.util.junit.SemiAutomated
import org.junit.experimental.categories.Categories
import org.junit.experimental.categories.Categories.IncludeCategory
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@SuiteClasses(CompleteRegressionIOSSuite::class)
@IncludeCategory(SemiAutomated::class, Helper::class)
@RunWith(Categories::class)
class OnlySemiAutomatedRegressionIOSSuite