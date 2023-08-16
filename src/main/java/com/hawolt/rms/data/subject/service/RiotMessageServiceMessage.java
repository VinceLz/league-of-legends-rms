package com.hawolt.rms.data.subject.service;

import com.hawolt.rms.data.impl.payload.RiotMessageMessagePayload;

/**
 * Created: 22/01/2023 14:06
 * Author: Twitter @hawolt
 **/

public class RiotMessageServiceMessage {
    protected final RiotMessageMessagePayload payload;

    public RiotMessageServiceMessage(RiotMessageMessagePayload payload) {
        this.payload = payload;
    }

    public RiotMessageMessagePayload getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "RiotMessageServiceMessage{" +
                "payload=" + payload +
                '}';
    }
}
