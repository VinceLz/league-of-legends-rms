package com.hawolt.rms;

import com.hawolt.io.Core;
import com.hawolt.logger.Logger;
import com.hawolt.rms.data.GenericRiotMessageEvent;
import com.hawolt.rms.data.PayloadType;
import com.hawolt.rms.data.subject.RiotMessageSubjectHandler;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

/**
 * Created: 22/01/2023 01:21
 * Author: Twitter @hawolt
 **/

public class VirtualRiotMessageClient extends WebSocketClient {
    private final RiotMessageSubjectHandler handler = new RiotMessageSubjectHandler();
    private final IRiotMessageServiceConnectionCallback callback;

    private boolean gzipCompression;
    private long expireTimestamp;

    public VirtualRiotMessageClient(URI serverUri, IRiotMessageServiceConnectionCallback callback) {
        super(serverUri);
        this.callback = callback;
        RiotMessageSubjectHandler subjectHandler = getHandler();
        subjectHandler.addListener(PayloadType.GZIP, payload -> setGZIP(payload.getRAW().getBoolean("enabled")));
        getHandler().addListener(PayloadType.RSO, payload -> setExpireTimestamp(payload.getRAW().getLong("expire_ts")));
        getHandler().addListener(PayloadType.SESSION, payload -> {
            JSONObject response = new JSONObject();
            response.put("id", UUID.randomUUID());
            response.put("subject", "rms:gzip");
            response.put("type", "request");
            JSONObject content = new JSONObject();
            content.put("enable", "true");
            response.put("payload", content);
            send(response.toString());
        });
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        callback.onOpen(serverHandshake);
    }

    @Override
    public void onMessage(String s) {
        JSONObject object = new JSONObject(s);
        GenericRiotMessageEvent event = new GenericRiotMessageEvent(object);
        handler.onGenericRiotMessageEvent(event);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);
        byte[] b = bytes.array();
        String message = null;
        if (gzipCompression) {
            try {
                GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(b));
                message = Core.read(gis).toString();
            } catch (IOException e) {
                callback.onError(new IOException("GZIP decompression error"));
            }
        } else {
            message = new String(b);
        }
        if (message == null) return;
        Logger.debug("[rms-in] {}", message);
        onMessage(message);
    }

    @Override
    public void send(String text) {
        super.send(text);
        Logger.debug("[rms-out] {}", text);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        callback.onClose(i, s, b);
    }

    @Override
    public void onError(Exception e) {
        callback.onError(e);
    }

    private void setGZIP(boolean enabled) {
        this.gzipCompression = enabled;
        Logger.debug("[rms] gzipCompression: {}", enabled);
    }

    private void setExpireTimestamp(long timestamp) {
        this.expireTimestamp = timestamp;
        Logger.debug("[rms] expire_ts: {}", timestamp);
    }

    public RiotMessageSubjectHandler getHandler() {
        return handler;
    }

}
