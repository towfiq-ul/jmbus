/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.mbus;


import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 * JNA mirror of libmbus's {@code mbus_data_fixed} C struct — a telegram's payload when it uses
 * the older, fixed-length data format (as opposed to the more common variable-length format,
 * see {@link mbus_data_variable}).
 */
@FieldOrder({"id_bcd", "tx_cnt", "status", "cnt1_type", "cnt2_type", "cnt1_val", "cnt2_val"})
public class mbus_data_fixed extends Structure {
    /** Meter identification number, BCD-encoded. */
    public byte[] id_bcd = new byte[4];
    /** Transmission counter, incremented by the meter on every telegram sent. */
    public byte tx_cnt;
    /** Status byte (counter coding, error flags). */
    public byte status;
    /** Type/unit of {@link #cnt1_val}. */
    public byte cnt1_type;
    /** Type/unit of {@link #cnt2_val}. */
    public byte cnt2_type;
    /** First counter value (typically the current reading), BCD-encoded. */
    public byte[] cnt1_val = new byte[4];
    /** Second counter value (typically a historic reading), BCD-encoded. */
    public byte[] cnt2_val = new byte[4];

    /** No-arg constructor, required by JNA for reflective {@link Structure} construction. */
    public mbus_data_fixed() {
    }
}
