package eu.nyerel.panda.ijplugin.data

import com.intellij.openapi.diagnostic.Logger
import eu.nyerel.panda.Constants
import eu.nyerel.panda.monitoringresult.calltree.CallTreeNode

import java.io.*

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
object DumpFileReader {

    private val LOG = Logger.getInstance(DumpFileReader::class.java)

    private val dumpFile: File
        get() = File(tempDir, Constants.DUMP_FILE_NAME)

    private val tempDir: File
        get() = File(System.getProperty("java.io.tmpdir"))

    init {
        LOG.debug("Clearing previous dump file")
        clear()
    }

    fun read(): List<CallTreeNode> {
        return if (dumpFile.exists()) {
            readCallTree(dumpFile)
        } else {
            emptyList()
        }
    }

    private fun readCallTree(file: File): List<CallTreeNode> {
        try {
            FileInputStream(file).use {
                val list = CallTreeListReader.read(it)
                return list.nodes
            }
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun clear() {
        if (dumpFile.exists()) {
            if (!dumpFile.delete()) {
                LOG.warn("Failed to delete profiler dump file " + dumpFile.absolutePath)
            }
        }
    }

}
