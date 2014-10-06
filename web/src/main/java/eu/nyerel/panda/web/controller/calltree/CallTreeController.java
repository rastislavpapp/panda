package eu.nyerel.panda.web.controller.calltree;

import eu.nyerel.panda.model.CallTreeNode;
import eu.nyerel.panda.monitoring.MonitoringService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
@Controller
@RequestMapping("/")
public class CallTreeController {

	@RequestMapping
	public String renderCallTree(Model model) {
		List<CallTreeNode> monitoringData = MonitoringService.getInstance().getMonitoringData();
		model.addAttribute("monitoringData", monitoringData);
		return "call-tree/view";
	}

}
