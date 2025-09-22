package com.example.common;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses( {AssertionConcernTests.class,
                DaysTests.class,
                DepartmentTests.class,
                EmailAddressTests.class,
                FullNameTests.class,
                IdentityTests.class,
                LeavePeriodTests.class})
public class TestSuite {
}
