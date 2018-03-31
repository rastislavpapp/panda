package eu.nyerel.panda.ijplugin.runner.calltree.model

import com.intellij.ide.util.treeView.NodeDescriptor
import com.intellij.ui.treeStructure.SimpleNode
import eu.nyerel.panda.ijplugin.model.MethodDescriptionParser
import eu.nyerel.panda.ijplugin.runner.PandaSettings
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
class CallTreeNodeDescriptor(val callTreeNode: CallTreeNode,
                             parentDescriptor: NodeDescriptor<*>?)
    : SimpleNode(null, parentDescriptor) {

    init {
        templatePresentation.addText(getDescription(callTreeNode), plainAttributes)
    }

    private fun getDescription(node: CallTreeNode): String {
        return if (PandaSettings.isShowPackageName) {
            node.description
        } else {
            MethodDescriptionParser.parse(node.description).descriptionWithoutPackageName
        }
    }

    override fun getChildren(): Array<SimpleNode> {
        return SimpleNode.NO_CHILDREN
    }

}
