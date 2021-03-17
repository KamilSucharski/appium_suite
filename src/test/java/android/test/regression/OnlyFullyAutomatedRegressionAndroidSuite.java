package android.test.regression;

import common.junit.SemiAutomated;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;

import static org.junit.runners.Suite.SuiteClasses;

@SuiteClasses(CompleteRegressionAndroidSuite.class)
@Categories.ExcludeCategory(SemiAutomated.class)
@RunWith(Categories.class)
public final class OnlyFullyAutomatedRegressionAndroidSuite {
}