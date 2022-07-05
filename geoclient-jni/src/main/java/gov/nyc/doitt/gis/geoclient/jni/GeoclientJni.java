/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gov.nyc.doitt.gis.geoclient.jni;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@formatter:off
public class GeoclientJni implements Geoclient {

    final static Logger logger = LoggerFactory.getLogger(GeoclientJni.class);

    private enum BufferType {
        Direct,
        Indirect;
    }

    private enum WorkArea {
        One, Two;

        @Override
        public String toString() {
            return String.format("workArea%s", this.name());
        }
    }

    /**
     * Interface to C language function
     * <code>void callgeo(char *  work_area1, char *  work_area2)</code>.
     * @param work_area1 a direct or array-backed {@link java.nio.ByteBuffer}
     * @param work_area2 a direct or array-backed {@link java.nio.ByteBuffer}
     */
    @Override
    public void callgeo(ByteBuffer work_area1, ByteBuffer work_area2) {

        final boolean work_area1_is_direct = isDirect(WorkArea.One, work_area1);
        final boolean work_area2_is_direct = isDirect(WorkArea.Two, work_area2);

        callgeo1(work_area1_is_direct ? work_area1 : work_area1.array(),
                work_area1_is_direct ? getDirectBufferByteOffset(work_area1)
                        : getIndirectBufferByteOffset(work_area1),
                work_area1_is_direct,
                work_area2_is_direct ? work_area2 : work_area2.array(),
                work_area2_is_direct ? getDirectBufferByteOffset(work_area2)
                        : getIndirectBufferByteOffset(work_area2),
                work_area2_is_direct);
    }

    /**
     *
     * Entry point to C language function
     * <code>void callgeo(char *  work_area1, char *  work_area2)</code>.
     * @param work_area1 direct or array-backed {@link java.nio.ByteBuffer} or a
     * {@code java.lang.byte[]}
     * @param work_area1_byte_offset array offset
     * @param work_area1_is_direct whether the work_area1 arugment is a direct
     * {@link java.nio.ByteBuffer}
     * @param work_area2 direct or array-backed {@link java.nio.ByteBuffer} or a
     * {@code java.lang.byte[]}
     * @param work_area2_byte_offset array offset
     * @param work_area2_is_direct whether the work_area2 arugment is a direct
     * {@link java.nio.ByteBuffer}
     *
     */
    private native void callgeo1(Object work_area1, int work_area1_byte_offset,
            boolean work_area1_is_direct, Object work_area2, int work_area2_byte_offset,
            boolean work_area2_is_direct);

    /**
     *
     * Interface to C language function
     * <code>void callgeo(char *  work_area1, char *  work_area2)</code>.
     * @param work_area1 a {@code java.lang.byte[]}
     * @param work_area1_offset array offset
     * @param work_area2 a {@code java.lang.byte[]}
     * @param work_area2_offset array offset
     *
     */
    @Override
    public void callgeo(byte[] work_area1, int work_area1_offset, byte[] work_area2,
            int work_area2_offset) {

        verifyOffset(WorkArea.One, work_area1, work_area1_offset);
        verifyOffset(WorkArea.Two, work_area2, work_area2_offset);
        callgeo1(work_area1, work_area1_offset, false, work_area2, work_area2_offset,
                false);
    }

    private void verifyOffset(WorkArea workArea, byte[] buffer, int offset) {
        if (buffer != null && buffer.length <= offset) {
            throw new RuntimeException(String.format(
                    "%s byte[] argument offset %d equals or exceeds array length %d",
                    workArea, offset, buffer.length));
        }
    }

    private void logBufferType(WorkArea workArea, BufferType bufferType, boolean isNull) {
        if (isNull) {
            logger.debug("{} buffer type: {}, however it is null ", workArea, bufferType);
        }
        else {
            logger.debug("{} buffer type: {}", workArea, bufferType);
        }
    }

    private boolean isDirect(WorkArea workArea, ByteBuffer buffer) {
        if (buffer == null) {
            logBufferType(workArea, BufferType.Direct, true);
            return true;
        }
        boolean result = buffer.isDirect();
        BufferType bufferType = result ? BufferType.Direct : BufferType.Indirect;
        logBufferType(workArea, bufferType, false);
        return result;
    }

    private int getDirectBufferByteOffset(ByteBuffer buffer) {
        if (buffer == null) {
            return 0;
        }
        return buffer.position();
    }

    private int getIndirectBufferByteOffset(ByteBuffer buffer) {
        if (buffer == null) {
            return 0;
        }
        int pos = buffer.position();
        return pos + buffer.arrayOffset();
    }

    // --- Begin CustomJavaCode .cfg declarations
    static {
        String libBaseName = JniContext.getSharedLibraryBaseName();
        String jvmTempDir = JniContext.getJvmTempDir();
        try {
            new NativeLibraryLoader(libBaseName)
                    .loadLibrary(jvmTempDir);
                        logger.info(
                            "Successfully loaded {} library from {}:",
                            libBaseName, jvmTempDir);
        }
        catch (IOException ioe) {
            logger.error("Error loading {} library from {}:", libBaseName, jvmTempDir);
            logger.error(ioe.getMessage());
            throw new RuntimeException(ioe);
        }
        //
        // System.loadLibrary(String): uses the paths specified in Java System variable
        // 'java.library.path'
        // System.load(String): uses platform-dependant absolute path
        //
        // Loads
        // libgeoclientjni.so (on Linux)
        // geoclientjni.dll (on Windows)
        //
        // TODO: Confirm the following:
        // Both methods add any paths defined by the standard platform-specific lib paths
        // (LD_LIBRARY, PATH, etc.)
        // to the overall library path that were defined by the environment owning the
        // parent Java process.
        //
        // System.loadLibrary("geoclientjni");
    }
    // ---- End CustomJavaCode .cfg declarations

}
//@formatter:on