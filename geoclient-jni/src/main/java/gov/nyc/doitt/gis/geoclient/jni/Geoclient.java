package gov.nyc.doitt.gis.geoclient.jni;

import java.nio.ByteBuffer;

public interface Geoclient {

    /**
     * Interface to C language function <code>void callgeo(char *  work_area1, char *  work_area2)</code>.
     *
     * @param work_area1 a direct or array-backed {@link java.nio.ByteBuffer}
     * @param work_area2 a direct or array-backed {@link java.nio.ByteBuffer}
     */
    public void callgeo(ByteBuffer work_area1, ByteBuffer work_area2);

    /**
     * Interface to C language function <code>void callgeo(char *  work_area1, char *  work_area2)</code>.
     *
     * @param work_area1 an array of bytes
     * @param work_area1_offset array offset
     * @param work_area2 an array of bytes
     * @param work_area2_offset array offset
     *
     */
    public void callgeo(byte[] work_area1, int work_area1_offset, byte[] work_area2, int work_area2_offset);

}
