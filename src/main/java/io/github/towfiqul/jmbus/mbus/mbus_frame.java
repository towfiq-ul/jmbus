/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 * JNA mirror of libmbus's {@code mbus_frame} C struct — the raw M-Bus telegram frame, before
 * its data records have been decoded. Populated by {@link LibMbus#mbus_parse}.
 *
 * <p>Field order and types must match the native struct exactly (see the class-level note on
 * {@link mbus_data_variable} for why); do not reorder or retype these without checking against
 * the current {@code mbus/mbus-protocol.h} in
 * <a href="https://github.com/rscada/libmbus">libmbus</a>.
 */
@FieldOrder({"start1", "length1", "length2", "start2", "control", "address", "control_information", "checksum", "stop",
        "data", "data_size", "type", "timestamp", "next"})
public class mbus_frame extends Structure {
    /** First start byte of the telegram (0x68 for long/control frames). */
    public byte start1;
    /** First length byte (long frames only; repeats {@link #length2}). */
    public byte length1;
    /** Second length byte (long frames only; repeats {@link #length1}). */
    public byte length2;
    /** Second start byte, repeating {@link #start1}. */
    public byte start2;
    /** Control field, identifying the frame/message type. */
    public byte control;
    /** Address of the slave (meter) this frame is for or from. */
    public byte address;
    /** Control information field, identifying the application-layer message type. */
    public byte control_information;
    /** Checksum over the frame's data field. */
    public byte checksum;
    /** Stop byte (0x16). */
    public byte stop;
    /** Raw application-layer payload bytes; decoded by {@link LibMbus#mbus_frame_data_parse}. */
    public byte[] data = new byte[252];
    /** Number of valid bytes in {@link #data}. */
    public long data_size;
    /** Frame type, as one of libmbus's {@code MBUS_FRAME_TYPE_*} constants. */
    public int type;
    /** Unix timestamp recorded when this frame was parsed. */
    public long timestamp;
    /** Pointer to the next frame, for multi-telegram replies; {@code null} otherwise. */
    public Pointer next;

    /** No-arg constructor, required by JNA for reflective {@link Structure} construction. */
    public mbus_frame() {
    }
}
