package eu.nyerel.panda.web.controller.calltree;

import eu.nyerel.panda.monitoringresult.MonitoringResultServiceImpl;
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode;
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
		List<CallTreeNode> callTree = MonitoringResultServiceImpl.getInstance().getCallTree();
		model.addAttribute("callTree", callTree);
		return "call-tree/view";
	}

}
