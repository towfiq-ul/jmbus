/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 * JNA mirror of libmbus's {@code mbus_frame_data} C struct — the decoded contents of an M-Bus
 * telegram, in either variable- or fixed-length format. Populated by
 * {@link LibMbus#mbus_frame_data_parse} and consumed by {@link LibMbus#mbus_frame_data_xml}.
 */
@FieldOrder({"data_var", "data_fix", "type", "error"})
public class mbus_frame_data extends Structure {
    /** Populated when {@link #type} indicates variable-length data format; unused otherwise. */
    public io.github.towfiqul.jmbus.mbus.mbus_data_variable data_var;
    /** Populated when {@link #type} indicates fixed-length data format; unused otherwise. */
    public io.github.towfiqul.jmbus.mbus.mbus_data_fixed data_fix;
    /** Which of {@link #data_var} / {@link #data_fix} is populated, per libmbus's
     *  {@code MBUS_DATA_TYPE_*} constants. */
    public int type;
    /** Non-zero if parsing failed; see libmbus's {@code MBUS_DATA_ERROR_*} constants. */
    public int error;

    /** No-arg constructor, required by JNA for reflective {@link Structure} construction. */
    public mbus_frame_data() {
    }
}
