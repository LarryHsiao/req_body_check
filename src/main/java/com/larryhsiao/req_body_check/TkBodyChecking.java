package com.larryhsiao.req_body_check;

import org.takes.Response;
import org.takes.facets.fork.RqRegex;
import org.takes.facets.fork.TkRegex;
import org.takes.rs.RsWithStatus;

import javax.json.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * TkRegex check the request body in same path of request path.
 */
public class TkBodyChecking implements TkRegex {
    private final File root;

    public TkBodyChecking(File root) {this.root = root;}

    @Override
    public Response act(RqRegex req) throws IOException {
        checkJsonObject(
            Json.createReader(req.body()).readObject(),
            Json.createReader(
                new FileInputStream(
                    req.matcher().group("path")
                )
            ).readObject()
        );
        return new RsWithStatus(204);
    }

    private void checkJsonObject(JsonObject remote, JsonObject local) {
        local.forEach((key, jsonValue) -> {
            if (!remote.containsKey(key)) {
                throw new RuntimeException("key not exist: " + key);
            }
            if (remote.get(key).getValueType() != jsonValue.getValueType()) {
                throw new RuntimeException("key type not match:" + key);
            } else {
                heckJsonObjectByKey(key, remote, local);
            }
        });
    }

    private void heckJsonObjectByKey(String key, JsonObject remote, JsonObject local) {
        if (remote.get(key).getValueType() == JsonValue.ValueType.OBJECT) {
            checkJsonObject(
                remote.getJsonObject(key),
                local.getJsonObject(key)
            );
        } else if (remote.get(key).getValueType() == JsonValue.ValueType.ARRAY) {
            try {
                checkJsonArray(
                    remote.getJsonArray(key),
                    local.getJsonArray(key)
                );
            } catch (IOException e) {
                throw new RuntimeException("Json array type not match: " + key, e);
            }
        }
    }

    private void checkJsonArray(JsonArray remote, JsonArray local) throws IOException {
        if (remote.get(0).getValueType() != local.get(0).getValueType()) {
            throw new RuntimeException(
                "Content of Json array not match, client:"
                    + remote.get(0).getValueType().name()
                    + " \nserver: " + local.get(0).getValueType().name()
            );
        } else {
            if (remote.get(0).getValueType() == JsonValue.ValueType.OBJECT) {
                checkJsonObject(
                    remote.getJsonObject(0),
                    local.getJsonObject(0)
                );
            } else if (remote.get(0).getValueType() == JsonValue.ValueType.ARRAY) {
                try {
                    checkJsonArray(
                        remote.getJsonArray(0),
                        local.getJsonArray(0)
                    );
                } catch (IOException e) {
                    throw new RuntimeException("Json array type not match", e);
                }
            }
        }
    }
}
