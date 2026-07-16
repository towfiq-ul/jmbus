/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 * JNA mirror of libmbus's {@code mbus_value_information_block} C struct — the Value Information
 * Block (VIB) of one data record, encoding the record's unit and scale (e.g. "energy, kWh").
 * Part of {@link mbus_data_record_header}.
 */
@FieldOrder({"vif", "vife", "nvife", "custom_vif"})
public class mbus_value_information_block extends Structure {
    /** Value Information Field byte. */
    public byte vif;
    /** Value Information Field extension bytes, present when {@link #vif} sets the extension bit. */
    public byte[] vife = new byte[10];
    /** Number of valid bytes in {@link #vife}. */
    public long nvife;
    /** Manufacturer-specific VIF text, when {@link #vif} indicates a custom/plaintext unit. */
    public byte[] custom_vif = new byte[128];

    /** No-arg constructor, required by JNA for reflective {@link Structure} construction. */
    public mbus_value_information_block() {
    }
}
