package common.test.sample

import org.junit.experimental.categories.Categories
import org.junit.runner.RunWith
import org.junit.runners.Suite.SuiteClasses

@SuiteClasses(
    SampleListTest::class,
    SampleDetailsTest::class
)
@RunWith(Categories::class)
class SampleSuite