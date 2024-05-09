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
import com.mojang.authlib.GameProfile;
import kotlin.text.Charsets;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Base64;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Unique
    private String getTextureContent(GameProfile profile) {
        var texturesBase64 = profile.getProperties().get("textures").toString().split(",")[1].split("=")[1];
        return new String(Base64.getDecoder().decode(texturesBase64), Charsets.UTF_8);
    }

    @Inject(method = "onPlayerConnect", at = @At("HEAD"))
    public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {
        var ip = connection.getAddress().toString().split(":")[0].replace("/", "");
        var uuid = player.getUuid().toString();
        var name = player.getName().getString();
        if (player.server.isOnlineMode()) {
            var textureContent = this.getTextureContent(player.getGameProfile());
            if (!YeeeesMOTD.Companion.getSkinHeadManagerV2().exists(ip)) {
                YeeeesMOTD.Companion.getSkinHeadManagerV2().addHead(name, uuid, ip, textureContent);
            } else {
                YeeeesMOTD.Companion.getSkinHeadManagerV2().updateHead(name, uuid, ip, textureContent);
            }
        }
    }
}
