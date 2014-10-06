package eu.nyerel.panda.model;

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
public class MethodNode extends CallTreeNode {

	public MethodNode(long id, CallTreeNode parent, String methodSignature) {
		super(id, parent, methodSignature);
	}

}
