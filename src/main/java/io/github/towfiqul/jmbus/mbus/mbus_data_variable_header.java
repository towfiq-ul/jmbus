/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 * JNA mirror of libmbus's {@code mbus_data_variable_header} C struct — the fixed-size header
 * that precedes a telegram's data records in variable-length format, identifying the meter
 * itself (as opposed to {@link mbus_data_record}, which identifies one reading).
 */
@FieldOrder({"id_bcd", "manufacturer", "version", "medium", "access_no", "status", "signature"})
public class mbus_data_variable_header extends Structure {
    /** Meter identification number, BCD-encoded. */
    public byte[] id_bcd = new byte[4];
    /** Manufacturer code, per the M-Bus manufacturer ID encoding. */
    public byte[] manufacturer = new byte[2];
    /** Meter firmware/hardware version. */
    public byte version;
    /** Medium the meter measures (water, heat, gas, electricity, ...). */
    public byte medium;
    /** Access number, incremented by the meter on every telegram sent. */
    public byte access_no;
    /** Status byte (error flags, etc.). */
    public byte status;
    /** Manufacturer-specific signature bytes. */
    public byte[] signature = new byte[2];

    /** No-arg constructor, required by JNA for reflective {@link Structure} construction. */
    public mbus_data_variable_header() {
    }
}
