/**
 * @author Towfiqul Islam
 * @since 2/5/23 4:29 PM
 */

package io.github.towfiqul.jmbus.service;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Decodes M-Bus (Meter-Bus) telegrams into structured JSON, via a native
 * {@code libmbus} JNA binding (see {@link io.github.towfiqul.jmbus.mbus.LibMbus}). See the
 * "Usage" section of the project README for a runnable example and sample output.
 */
public interface MBusService {

    /**
     * Decodes the hex telegram found at {@code propertyName} within {@code dataNode}, replacing
     * it in place with the decoded JSON. Useful when a raw telegram arrives as one field of a
     * larger payload (e.g. off a message queue, alongside metadata) that you don't want to lose.
     *
     * @param dataNode     a JSON object containing a hex-telegram field; mutated in place
     * @param propertyName the field name within {@code dataNode} holding the hex telegram
     * @return {@code dataNode}, with the field at {@code propertyName} replaced by its decoded
     *         JSON representation
     */
    JsonNode decodeMessage(JsonNode dataNode, String propertyName);

    /**
     * Decodes a raw M-Bus telegram, given as a hex string, into structured JSON.
     *
     * @param hexValue the hex-encoded telegram, e.g. {@code "68 4D 4D 68 08 ..."}
     * @return the decoded telegram as JSON (slave information plus a list of data records)
     */
    JsonNode decodeMessage(String hexValue);

}
