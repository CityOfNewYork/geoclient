package gov.nyc.doitt.gis.gradle.tomcat

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.InvalidUserDataException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.plugins.WarPlugin
import org.gradle.api.tasks.TaskAction


/**
 * Cut, pasted (and edited by me to be less good) from the gradle-tomcat-plugin.
 *
 * @see https://github.com/bmuschko/gradle-tomcat-plugin
 */
class TomcatStopTask extends DefaultTask {

    Integer stopPort

    String stopKey

    @TaskAction
    void stop() {
        if(!getStopPort()) {
            throw new InvalidUserDataException('Please specify a valid port')
        }
        if(!getStopKey()) {
            throw new InvalidUserDataException('Please specify a valid stopKey')
        }

        try {
            Socket s = new Socket(InetAddress.getByName('127.0.0.1'), getStopPort())
            s.setSoLinger(false, 0)

            OutputStream out = s.outputStream
            out.write((getStopKey() + '\r\nstop\r\n').bytes)
            out.flush()
            // Wait for the socket to be closed after the server is stopped.
            // No response is actually written, so this always returns -1.
            s.inputStream.read()
            s.close()
        }
        catch(ConnectException e) {
            logger.info 'Tomcat not running!'
        }
        catch(Exception e) {
            logger.error 'Exception during stopping', e
        }
    }
}
