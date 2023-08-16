package com.hawolt.rms.data.subject;

import com.hawolt.rms.data.impl.payload.GenericRiotMessagePayload;

/**
 * Created: 22/01/2023 12:32
 * Author: Twitter @hawolt
 **/

public interface IPayloadListener<T extends GenericRiotMessagePayload> {
    void onPayload(T t);
}
