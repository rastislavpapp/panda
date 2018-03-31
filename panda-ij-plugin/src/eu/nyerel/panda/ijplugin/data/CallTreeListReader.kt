package eu.nyerel.panda.ijplugin.data

import eu.nyerel.panda.monitoringresult.calltree.CallTreeList

import java.io.*

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
object CallTreeListReader {

    fun read(inputStream: InputStream): CallTreeList {
        val input: ObjectInput
        try {
            val buffer = BufferedInputStream(inputStream)
            input = ObjectInputStream(buffer)
            return input.readObject() as CallTreeList
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}
