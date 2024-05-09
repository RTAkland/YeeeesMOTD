/*
 * Copyright 2024 RTAkland
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */


package cn.rtast.yeeesmotd.listeners;

import cn.rtast.yeeesmotd.utils.OnQueryListenerKt;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;

public class ProxyPingEventListener {

    @Subscribe
    public void onProxyPingEvent(ProxyPingEvent event) {
        var pong = OnQueryListenerKt.onQuery(event.getPing(), event.getConnection().getRemoteAddress().getHostString());
        event.setPing(pong);
    }
}
