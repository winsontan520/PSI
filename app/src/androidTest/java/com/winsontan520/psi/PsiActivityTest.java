package com.winsontan520.psi;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.winsontan520.psi.data.source.remote.DataGovRemoteDataSource;
import com.winsontan520.psi.ui.psi.PsiActivity;
import com.winsontan520.psi.util.FileUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Winson Tan on 17/4/18.
 */

@RunWith(AndroidJUnit4.class)
public class PsiActivityTest extends InstrumentationTestCase {

    @Rule
    public ActivityTestRule<PsiActivity> mActivityRule =
            new ActivityTestRule<>(PsiActivity.class, true, false);
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());

        DataGovRemoteDataSource.BASE_URL = server.url("/").toString();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testRetryButtonShowsWhenError() throws Exception {
        String fileName = "psi_404_not_found.json";

        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(FileUtil.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withText(R.string.alert_dialog_message)).check(matches(isDisplayed()));
    }

    @Test
    public void testProgressBarDismissedOnSuccess() throws Exception {
        String fileName = "psi_200_ok_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(FileUtil.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(withId(R.id.progress_bar)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }
}
