/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Library;

/**
 * JNA binding to the native {@code libmbus} shared library (bundled as
 * {@code src/main/resources/libmbus.so}). Method names and signatures mirror the C functions
 * declared in libmbus's {@code mbus/mbus.h} / {@code mbus/mbus-protocol.h} headers exactly —
 * JNA maps each call here directly onto the corresponding native symbol.
 *
 * <p>Loaded via {@code Native.load(path, LibMbus.class)}; see
 * {@link io.github.towfiqul.jmbus.service.impl.MBusServiceImpl} for the decode pipeline built
 * on top of it.
 */
public interface LibMbus extends Library {

    /**
     * Returns the version string of the loaded native libmbus library.
     *
     * @return the libmbus version, e.g. {@code "0.8.6"}
     */
    String mbus_get_current_version();

    /**
     * Converts a hex-encoded telegram (e.g. {@code "68 4D 4D 68 ..."}) into raw bytes.
     *
     * @param buff           destination buffer that receives the decoded bytes
     * @param size_of_buf    capacity of {@code buff}, in bytes
     * @param raw_buf        the hex string to decode, as raw ASCII bytes
     * @param size_of_raw_buf length of {@code raw_buf}, in bytes
     * @return the number of bytes written into {@code buff}
     */
    long mbus_hex2bin(byte[] buff, int size_of_buf, byte[] raw_buf, int size_of_raw_buf);

    /**
     * Parses raw M-Bus telegram bytes into a frame, validating the start/stop bytes and
     * checksum in the process.
     *
     * @param reply   frame structure to populate; typically freshly constructed
     * @param buf     raw telegram bytes, as produced by {@link #mbus_hex2bin}
     * @param buf_len number of valid bytes in {@code buf}
     * @return {@code 0} on success, non-zero if the frame is invalid
     */
    int mbus_parse(io.github.towfiqul.jmbus.mbus.mbus_frame reply, byte[] buf, long buf_len);

    /**
     * Walks a parsed frame's data records and decodes each one's DIF/VIF encoding into
     * meaningful values and units.
     *
     * @param reply      a frame previously populated by {@link #mbus_parse}
     * @param frame_data data structure to populate with the decoded records
     * @return {@code 0} on success, non-zero on error
     */
    int mbus_frame_data_parse(io.github.towfiqul.jmbus.mbus.mbus_frame reply, io.github.towfiqul.jmbus.mbus.mbus_frame_data frame_data);

    /**
     * Prints a human-readable dump of the given frame to the native library's standard output —
     * primarily useful for debugging.
     *
     * @param frame a frame previously populated by {@link #mbus_parse}
     * @return {@code 0} on success, non-zero on error
     */
    int mbus_frame_print(io.github.towfiqul.jmbus.mbus.mbus_frame frame);

    /**
     * Renders decoded frame data as libmbus's "normalized" XML representation (each data record
     * expressed with normalized/SI units rather than the manufacturer's native units).
     *
     * @param frame_data data previously populated by {@link #mbus_frame_data_parse}
     * @return the XML representation, as a native-allocated string
     */
    String mbus_frame_data_xml_normalized(io.github.towfiqul.jmbus.mbus.mbus_frame_data frame_data);

    /**
     * Renders decoded frame data as XML. This is what
     * {@link io.github.towfiqul.jmbus.service.impl.MBusServiceImpl} calls and then converts to
     * JSON.
     *
     * @param frame_data data previously populated by {@link #mbus_frame_data_parse}
     * @return the XML representation, as a native-allocated string
     */
    String mbus_frame_data_xml(io.github.towfiqul.jmbus.mbus.mbus_frame_data frame_data);
}
