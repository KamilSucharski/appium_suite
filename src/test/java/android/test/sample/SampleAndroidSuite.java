package android.test.sample;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;

import static org.junit.runners.Suite.SuiteClasses;

@SuiteClasses({
    SampleListAndroidTest.class,
    SampleDetailsAndroidTest.class
})
@RunWith(Categories.class)
public final class SampleAndroidSuite {
}