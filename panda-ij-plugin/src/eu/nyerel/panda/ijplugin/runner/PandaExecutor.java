package eu.nyerel.panda.ijplugin.runner;

import com.intellij.execution.Executor;
import com.intellij.icons.AllIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class PandaExecutor extends Executor {

	public static final String EXECUTOR_ID = "Panda Executor";

	public PandaExecutor() {
	}

	public String getToolWindowId() {
		return this.getId();
	}

	public Icon getToolWindowIcon() {
		return AllIcons.Toolwindows.ToolWindowRun;
	}

	@NotNull
	public Icon getIcon() {
		return AllIcons.Actions.Execute;
	}

	public Icon getDisabledIcon() {
		return AllIcons.Process.DisabledRun;
	}

	public String getDescription() {
		return "Run application with Panda agent";
	}

	@NotNull
	public String getActionName() {
		return "Run with Panda";
	}

	@NotNull
	public String getId() {
		return EXECUTOR_ID;
	}

	@NotNull
	public String getStartActionText() {
		return "Run with Panda";
	}

	public String getContextActionId() {
		return this.getId() + " context-action-does-not-exist";
	}

	public String getHelpId() {
		return null;
	}

}
