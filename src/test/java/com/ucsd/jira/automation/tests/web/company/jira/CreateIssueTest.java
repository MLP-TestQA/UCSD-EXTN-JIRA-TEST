package com.ucsd.jira.automation.tests.web.company.jira;

import com.pwc.core.framework.annotations.Issue;
import com.pwc.core.framework.listeners.Retry;
import com.ucsd.jira.automation.data.Constants;
import com.ucsd.jira.automation.data.Data;
import com.ucsd.jira.automation.frameworksupport.Groups;
import com.ucsd.jira.automation.frameworksupport.JiraTestCase;
import org.testng.annotations.Test;

import java.util.Date;

import static com.pwc.assertion.AssertService.assertEquals;
import static com.pwc.logging.service.LoggerService.*;

public class CreateIssueTest extends JiraTestCase {

    @Override
    public void beforeMethod() {
    }

    @Override
    public void afterMethod() {
    }

    // @Issue("STORY-1234")
    @Test(retryAnalyzer = Retry.class, groups = {Groups.ACCEPTANCE_TEST})
    public void testCreateIssue() {

        FEATURE("Create Jira Issue");
        SCENARIO("User logs in and creates Jira issue");

        GIVEN("I am a valid user");
        webElementVisible(Constants.NEW_TEST_HEADING);

        WHEN("I navigate to the Create Global Item");
        webAction(Constants.CREATE_ISSUE_BUTTON);
        // redirect(Constants.HOME_URL);

        THEN("Input the Summary and Description of the issue and select Create Issue");

        webAction(Constants.SUMMARY_INPUT, Constants.SUMMARY_TEXT);

        //use current date and time in the Description to ensure verification of exact issue created
        Date currentDateTime = new Date();
        final String CURRENT_DATETIME = (' ' + currentDateTime.toString());
        final String DESCRIPTION_PLUS_DATETIME = (Constants.DESCRIPTION_TEXT + CURRENT_DATETIME);
        webAction(Constants.DESCRIPTION_TEXT_FIELD, DESCRIPTION_PLUS_DATETIME);

        webAction(Constants.CREATE_BUTTON);
        redirect(Constants.HOME_URL);

        //verify that new issue was created by test using datetimestamp info from description
        webAction(Constants.SEARCH_NAVBAR_BUTTON);  // magnifying glass button in left nav bar
        webAction(Constants.BASIC_SEARCH_INPUT_TWO, DESCRIPTION_PLUS_DATETIME);  // text field for search text
        webElementVisible(Constants.SEARCH_MATCHING_ISSUES_URL);  // search result shows url

        /*
        //Use forward slash plus asterisk followed by asterisk plus back slash
        to select out large sections of code to not execute, for example for testing only part of code
        figure out how to do assert?
        Is it needed vs WebElementVisible verified OK, &/or test fails if webElementVisible fails.

        //Test values for datetime so I can test the verify portion without re-creating issues each time
        //This one should succeed (at least until I clean up the data by deleting created issues)
        final String DESCRIPTION_PLUS_DATETIME = "This is a default description. Wed Nov 13 13:50:41 PST 2018";
        //This one should fail (can change search text from a valid string or can edit actual issue to cause mismatch
        final String DESCRIPTION_PLUS_DATETIME = "This is a default description. Wed Nov 13 13:50:41 PST 2019";

        //extra line just for a debug stop after all else done but before after actions
        final String UNUSED = "An unused line to put a debug stop on.";

        NOTE: Logs for WebElementVisible indicate it will try for 45 seconds, but it only seems to go for 10
        before retrying the whole test case a second time.  Likewise that time it runs for about 10 and then
        fails the test, so it shows 2 tests run, one ignored, and one failed.  Expected?

        Future - should clean up by deleting created data - need administrator permissions to show delete option.
        */


    }

}
