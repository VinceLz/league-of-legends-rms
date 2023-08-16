package com.hawolt.rms;

import com.hawolt.rms.data.GenericRiotMessageEvent;
import org.java_websocket.handshake.ServerHandshake;

/**
 * Created: 22/01/2023 11:46
 * Author: Twitter @hawolt
 **/

public interface IRiotMessageServiceConnectionCallback {
    void onRiotMessageEvent(GenericRiotMessageEvent event);

    void onClose(int i, String s, boolean b);

    void onOpen(ServerHandshake handshake);

    void onError(Exception e);
}
