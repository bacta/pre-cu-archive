package com.ocdsoft.bacta.swg.server.login.serializer;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;
import java.net.InetSocketAddress;

/**
 * A Gson adapter that handles {@link InetSocketAddress}es.
 */
public class ClusterServerSerializer implements JsonSerializer<InetSocketAddress>, JsonDeserializer<InetSocketAddress> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterServerSerializer.class);

    @Override
    public InetSocketAddress deserialize(final JsonElement json, final Type typeOfT,
                                         final JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            throw new JsonParseException("not a JSON object");
        }
        final JsonObject obj = (JsonObject) json;

        final JsonElement address = obj.get("address");
        final JsonElement port = obj.get("udpPort");
        if (address == null || port == null) {
            throw new JsonParseException("address/udpPort missing");
        }
        if (!address.isJsonPrimitive() || !((JsonPrimitive) address).isString()) {
            throw new JsonParseException("address is not a string");
        }
        if (!port.isJsonPrimitive() || !((JsonPrimitive) port).isNumber()) {
            throw new JsonParseException("udpPort is not a number");
        }
        final InetSocketAddress isa = new InetSocketAddress(address.getAsString(), port.getAsInt());
        return isa;
    }

    @Override
    public JsonElement serialize(final InetSocketAddress src, final Type typeOfSrc,
                                 final JsonSerializationContext context) {
        final JsonObject obj = new JsonObject();
        obj.addProperty("address", src.getAddress().getHostAddress());
        obj.addProperty("udpPort", src.getPort());
        return obj;
    }

}
