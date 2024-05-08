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


package cn.rtast.yeeeesmotd.mixin;

import cn.rtast.yeeeesmotd.utils.OnQueryListenerKt;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.query.QueryRequestC2SPacket;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.network.ServerQueryNetworkHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerQueryNetworkHandler.class)
public class ServerQueryNetworkHandlerMixin {

    @Shadow
    @Final
    private ClientConnection connection;

    @Shadow
    @Final
    private ServerMetadata metadata;

    @Inject(method = "onRequest", at = @At("HEAD"), cancellable = true)
    public void onRequest(QueryRequestC2SPacket packet, CallbackInfo ci) {
        var ip = this.connection.getAddress().toString().replace("/", "").split(":")[0];
        var response = OnQueryListenerKt.onQuery(this.metadata, ip);
        this.connection.send(new QueryResponseS2CPacket(response));
        ci.cancel();
    }
}
