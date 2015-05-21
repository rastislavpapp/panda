package eu.nyerel.panda.testapp.web.servlet.welcome;

import eu.nyerel.panda.testapp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
@Controller
@RequestMapping("/")
public class WelcomeController {

    @Autowired
    private TestService testService;

    @RequestMapping
    public String page1(Model model) {
        testService.waitFor(1000);
        testService.callDatabase();
        return "welcome/page1";
    }

    @RequestMapping("/page2")
    public String page2(Model model) {
        testService.makeInternalCalls(10);
        return "welcome/page2";
    }

    @RequestMapping("/page3")
    public String page3(Model model) {
        testService.fastCall();
        return "welcome/page3";
    }

    @RequestMapping("/page4")
    public String page4() {
        testService.testInheritance();
        return "welcome/page4";
    }

}
