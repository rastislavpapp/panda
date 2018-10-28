package eu.nyerel.panda.testapp.service;

import eu.nyerel.panda.testapp.TestApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(TestApp.class)
public class TestServiceTest {

    @Autowired
    private TestService testService;

    @Test
    public void testSomething() {
        for (int i = 0; i < 10; i++) {
            testService.testInheritance();
            testService.waitFor(100);
            testService.recursiveInternalCall(10, false);
        }
    }


}