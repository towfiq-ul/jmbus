/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.mbus;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Structure.FieldOrder;

/**
 * JNA mirror of libmbus's {@code mbus_data_variable} C struct — a telegram's payload when it
 * uses the more common variable-length data format: one shared header plus a linked list of
 * data records, one per reading.
 *
 * <p><b>Layout note:</b> every {@code *} (pointer) field in the C struct must stay a JNA
 * {@link Pointer} here, never an embedded {@link Structure}. Embedding reserves space for the
 * whole nested struct instead of one pointer-width slot, which silently shifts the native
 * offset of every field after it — and, since {@link mbus_frame_data} embeds this struct first,
 * every field after {@code data_var} there too. This bit a real bug once: see
 * {@code CHANGELOG.md}.
 */
@FieldOrder({"header", "record", "nrecords", "data", "data_len", "more_records_follow", "mdh", "mfg_data", "mfg_data_len"})
public class mbus_data_variable extends Structure {
    /** Meter identification shared by every record in {@link #record}. */
    public io.github.towfiqul.jmbus.mbus.mbus_data_variable_header header;

    /**
     * {@code mbus_data_record *record} in C — the head of the linked list of decoded data
     * records. Deref with {@code record.getPointer()} /
     * {@code Structure.newInstance(mbus_data_record.class, record)} if the list is ever needed
     * from Java; {@code jmbus} itself never reads this field directly, it only round-trips the
     * struct through further native calls.
     */
    public Pointer record;
    /** Number of records reachable from {@link #record}. */
    public long nrecords;

    /**
     * {@code unsigned char *data} in C — the still-encoded record bytes, as a raw buffer. Kept
     * as {@link Pointer} rather than {@code String} since it isn't necessarily null-terminated
     * text.
     */
    public Pointer data;
    /** Number of valid bytes at {@link #data}. */
    public long data_len;
    /** Non-zero if this telegram is one part of a multi-telegram reply. */
    public byte more_records_follow;
    /** Manufacturer data header byte, when {@link #mfg_data} is present. */
    public byte mdh;

    /**
     * {@code unsigned char *mfg_data} in C — manufacturer-specific trailing bytes, as a raw
     * buffer. Kept as {@link Pointer} rather than {@code String} for the same reason as
     * {@link #data}.
     */
    public Pointer mfg_data;
    /** Number of valid bytes at {@link #mfg_data}. */
    public long mfg_data_len;

    /** No-arg constructor, required by JNA for reflective {@link Structure} construction. */
    public mbus_data_variable() {
    }
}
