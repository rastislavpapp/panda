package eu.nyerel.panda.ijplugin.runner

import com.intellij.openapi.diagnostic.Logger

import java.io.*

/**
 * @author Rastislav Papp (rastislav.papp@gmail.com)
 */
object JarUtils {

    private val LOGGER = Logger.getInstance(JarUtils::class.java)
    private val PANDA_AGENT_JAR_FILE_NAME = "panda-agent.jar"

    private val tempDir: File
        get() = File(System.getProperty("java.io.tmpdir"))

    fun geUnpackedResourcePath(resourcePath: String): String {
        val inputStream = JarUtils::class.java.getResourceAsStream(resourcePath)
                ?: throw IllegalArgumentException("Resource not found on path " + resourcePath)
        val tempFile = getAsTempFile(inputStream)
        return tempFile.absolutePath
    }

    private fun getAsTempFile(inputStream: InputStream): File {
        var os: OutputStream? = null
        val tempFile = File(tempDir.absolutePath, PANDA_AGENT_JAR_FILE_NAME)
        try {
            if (!tempFile.isFile) {
                val success = tempFile.createNewFile()
                if (!success) {
                    throw IOException("Unable to create file " + tempFile.absolutePath)
                }
            }
            os = FileOutputStream(tempFile)
            copy(inputStream, os)
        } catch (e: Exception) {
            LOGGER.error(e)
        } finally {
            try {
                inputStream.close()
                if (os != null) {
                    os.close()
                }
            } catch (e: Exception) {
                LOGGER.error(e)
            }

        }
        return tempFile
    }

    private fun copy(inputStream: InputStream, os: OutputStream) {
        inputStream.copyTo(os)
    }

}
