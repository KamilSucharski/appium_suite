package ios.test.regression

import common.util.junit.SemiAutomated
import org.junit.experimental.categories.Categories
import org.junit.experimental.categories.Categories.ExcludeCategory
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@SuiteClasses(CompleteRegressionIOSSuite::class)
@ExcludeCategory(SemiAutomated::class)
@RunWith(Categories::class)
class OnlyFullyAutomatedRegressionIOSSuite