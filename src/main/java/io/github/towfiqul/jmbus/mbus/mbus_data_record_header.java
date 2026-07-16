/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 * JNA mirror of libmbus's {@code mbus_data_record_header} C struct — the header of one data
 * record in variable-length format, combining the Data Information Block (what kind of value
 * this is) and Value Information Block (what unit it's in). Part of {@link mbus_data_record}.
 */
@FieldOrder({"dib", "vib"})
public class mbus_data_record_header extends Structure {
    /** Data Information Block: function, storage number, and data length/type. */
    public io.github.towfiqul.jmbus.mbus.mbus_data_information_block dib;
    /** Value Information Block: unit and scale of the record's value. */
    public io.github.towfiqul.jmbus.mbus.mbus_value_information_block vib;

    /** No-arg constructor, required by JNA for reflective {@link Structure} construction. */
    public mbus_data_record_header() {
    }
}
