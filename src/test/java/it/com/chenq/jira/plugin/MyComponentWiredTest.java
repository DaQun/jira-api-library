package it.com.chenq.jira.plugin;

import com.atlassian.jira.testkit.client.Backdoor;
import com.atlassian.jira.testkit.client.util.TestKitLocalEnvironmentData;
import com.atlassian.jira.testkit.client.util.TimeBombLicence;
import lombok.RequiredArgsConstructor;
import net.sf.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.atlassian.plugins.osgi.test.AtlassianPluginsTestRunner;
import com.atlassian.sal.api.ApplicationProperties;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

@RequiredArgsConstructor
@RunWith(AtlassianPluginsTestRunner.class)
public class MyComponentWiredTest
{
    private final ApplicationProperties applicationProperties;

    @Test
    public void testMyName()
    {
        assertEquals("names do not match!", "myComponent:" + applicationProperties.getDisplayName());
    }

    public void testIntegration() {
        // Put your test logic here
    }
    @Inject
    private SessionFactory sessionFactory;

//    @Inject
//    private Navigation navigation;
//
//    @Test
//    @RestoreBlankInstance
//    @LoginAs(user = ADMIN_USERNAME)
//    public void systemShouldDoX() {
//        final Session otherUser = sessionFactory.begin();
//        otherUser.withSession(() -> {
//            navigation.login("fred");
//            // do things in the other session
//        });
//    }
}