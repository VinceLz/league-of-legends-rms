package com.hawolt.rms.data.subject.service;

import com.hawolt.rms.data.impl.payload.RiotMessageMessagePayload;

/**
 * Created: 22/01/2023 13:25
 * Author: Twitter @hawolt
 **/

public interface IRiotMessageServiceHandler<T extends RiotMessageServiceMessage> {
    T handle(RiotMessageMessagePayload payload);
}
