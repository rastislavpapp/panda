package eu.nyerel.panda.agent.instrumentation.jdbc;

import eu.nyerel.panda.agent.instrumentation.AbstractMethodTransformer;

import java.util.Set;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MethodTransformerDefinition {

	private final Set<String> classNames;
	private final AbstractMethodTransformer transformer;

	public MethodTransformerDefinition(Set<String> classNames, AbstractMethodTransformer transformer) {
		this.classNames = classNames;
		this.transformer = transformer;
	}

	public Set<String> getClassNames() {
		return classNames;
	}

	public AbstractMethodTransformer getTransformer() {
		return transformer;
	}

}