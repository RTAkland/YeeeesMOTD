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

import cn.rtast.yeeeesmotd.YeeeesMOTD;
import cn.rtast.yeeeesmotd.utils.CustomDescriptionUtil;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.c2s.query.QueryRequestC2SPacket;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.network.ServerQueryNetworkHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

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
        var ifShowHead = new Random().nextBoolean();
        var favicon = this.metadata.favicon();
        var description = this.metadata.description();
        if (ifShowHead && YeeeesMOTD.Companion.getSkinManager().exists(ip)) {
            var userData = YeeeesMOTD.Companion.getSkinManager().getHead(ip);
            var skin = Base64.getDecoder().decode(userData.get(2));
            var name = userData.get(0);
            favicon = Optional.of(new ServerMetadata.Favicon(skin));
            var randomDescription = CustomDescriptionUtil.INSTANCE.getDesc().replace("$player", name);
            description = Text.literal(randomDescription).styled(style -> style.withFormatting(Formatting.GREEN));
        } else {
            var randomIcon = YeeeesMOTD.Companion.getIconManager().getRandomIcon();
            if (randomIcon == null) {
                favicon = Optional.empty();
            } else {
                favicon = Optional.of(new ServerMetadata.Favicon(randomIcon));
            }
        }
        var newMetadata = new ServerMetadata(
                description,
                Optional.of(new ServerMetadata.Players(-1, -1, new ArrayList<>())),
                this.metadata.version(),
                favicon,
                this.metadata.secureChatEnforced()
        );
        this.connection.send(new QueryResponseS2CPacket(newMetadata));
        ci.cancel();
    }
}
