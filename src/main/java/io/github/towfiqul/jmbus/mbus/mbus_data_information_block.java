/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 * JNA mirror of libmbus's {@code mbus_data_information_block} C struct — the Data Information
 * Block (DIB) of one data record, encoding the record's storage number, function, and data
 * length/type. Part of {@link mbus_data_record_header}.
 */
@FieldOrder({"dif", "dife", "ndife"})
public class mbus_data_information_block extends Structure {
    /** Data Information Field byte. */
    public byte dif;
    /** Data Information Field extension bytes, present when {@link #dif} sets the extension bit. */
    public byte[] dife = new byte[10];
    /** Number of valid bytes in {@link #dife}. */
    public long ndife;

    /** No-arg constructor, required by JNA for reflective {@link Structure} construction. */
    public mbus_data_information_block() {
    }
}
