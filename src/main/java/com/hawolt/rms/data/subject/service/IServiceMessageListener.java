package com.hawolt.rms.data.subject.service;

/**
 * Created: 22/01/2023 14:07
 * Author: Twitter @hawolt
 **/

public interface IServiceMessageListener<T extends RiotMessageServiceMessage> {
    void onMessage(T t);
}
