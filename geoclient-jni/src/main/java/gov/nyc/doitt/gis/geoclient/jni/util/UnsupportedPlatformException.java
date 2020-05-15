package gov.nyc.doitt.gis.geoclient.jni.util;

/**
 * Thrown when this library is run on an unsupported platform. Currently, the native <code>Geosupport</code>
 * application is only available from the Department of City Planning on 64-bit Linux and Windows
 * operating systems. Therefore, <code>geoclient-jni</code> only runs on these same platforms.
 *
 * @author mlipper
 * @since 2.0.0
 *
 */
public class UnsupportedPlatformException extends RuntimeException
{
    private static final long serialVersionUID = 1142556847754347807L;


    /**
     * Uses the given operating system name (Linux, Windows, etc.) and CPU architecture (x64, amd64, etc.)
     * to create an informative exception message.
     *
     * @param operatingSystem OS name
     * @param architecture CPU architecture
     */
    public UnsupportedPlatformException(String operatingSystem, String architecture)
    {
        this(String.format("Unsupported JNI platform: OS='%s' ARCH='%s'", operatingSystem, architecture));
    }

    /*
     * @see RuntimeException(String message)
     */
    public UnsupportedPlatformException(String message)
    {
        super(message);
    }
}
