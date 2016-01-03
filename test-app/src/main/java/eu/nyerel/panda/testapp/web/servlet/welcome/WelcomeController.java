package eu.nyerel.panda.testapp.web.servlet.welcome;

import eu.nyerel.panda.testapp.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
@RestController
public class WelcomeController {

    @Autowired
    private TestService testService;

    @RequestMapping
    @ResponseBody
    public String page1(Model model) {
        testService.waitFor(1000);
        testService.callDatabase();
        return "page1";
    }

    @RequestMapping("/page2")
    @ResponseBody
    public String page2(Model model) {
        testService.makeInternalCalls(10);
        return "page2";
    }

    @RequestMapping("/page3")
    @ResponseBody
    public String page3(Model model) {
        testService.fastCall();
        return "page3";
    }

    @RequestMapping("/page4")
    @ResponseBody
    public String page4() {
        testService.testInheritance();
        return "/page4";
    }

}