/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 * JNA mirror of libmbus's {@code mbus_data_record} C struct — one decoded data record from a
 * variable-length-format telegram (e.g. one meter reading). Records form a singly linked list,
 * reachable via {@link mbus_data_variable#record}.
 */
@FieldOrder({"drh", "data", "data_len", "timestamp", "next"})
public class mbus_data_record extends Structure {
    /** This record's header: what the value represents and what unit it's in. */
    public io.github.towfiqul.jmbus.mbus.mbus_data_record_header drh;
    /** Raw, still-encoded value bytes; interpreted according to {@link #drh}. */
    public byte[] data = new byte[234];
    /** Number of valid bytes in {@link #data}. */
    public long data_len;
    /** Unix timestamp recorded when this record was parsed. */
    public long timestamp;
    /** Pointer to the next record in the list, or {@code null} if this is the last one. */
    public Pointer next;

    /** No-arg constructor, required by JNA for reflective {@link Structure} construction. */
    public mbus_data_record() {
    }
}
