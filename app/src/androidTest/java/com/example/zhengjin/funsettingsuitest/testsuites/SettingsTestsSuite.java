package com.example.zhengjin.funsettingsuitest.testsuites;

import com.example.zhengjin.funsettingsuitest.testcases.TestGeneralSettings;
import com.example.zhengjin.funsettingsuitest.testcategory.CategoryVersion20;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by zhengjin on 2016/6/17.
 *
 * Run test cases for testing common settings module.
 */
@RunWith(Categories.class)
@Categories.IncludeCategory(CategoryVersion20.class)
@Suite.SuiteClasses(TestGeneralSettings.class)
public final class SettingsTestsSuite {
}
