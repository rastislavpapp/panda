package eu.nyerel.panda.testapp.web.servlet.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
@Controller
@RequestMapping("/")
public class WelcomeController {

	private TestService testService = new TestService();

	@RequestMapping
	public String page1(Model model) {
		testService.waitFor(1000);
		return "welcome/page1";
	}

	@RequestMapping("/page2")
	public String page2(Model model) {
		testService.makeInternalCalls(10, true);
		return "welcome/page2";
	}

	@RequestMapping("/page3")
	public String page3(Model model) {
		testService.makeInternalCalls(100, false);
		return "welcome/page3";
	}

}
