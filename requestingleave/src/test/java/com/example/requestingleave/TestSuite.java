package com.example.requestingleave;

import com.example.requestingleave.leaveRequest.LeaveDayTests;
import com.example.requestingleave.leaveRequest.LeaveRequestTests;
import com.example.requestingleave.staff.LeaveEntitlementTests;
import com.example.requestingleave.staff.LeavePeriodTests;
import com.example.requestingleave.staff.StaffTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses( {LeaveDayTests.class,
                LeaveRequestTests.class,
                LeaveEntitlementTests.class,
                LeavePeriodTests.class,
                StaffTests.class})
public class TestSuite {
}
