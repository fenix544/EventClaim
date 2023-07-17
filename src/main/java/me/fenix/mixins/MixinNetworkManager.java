package me.fenix.mixins;

import io.netty.channel.ChannelHandlerContext;
import me.fenix.EventClaim;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.server.S45PacketTitle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

    @Inject(method = "channelRead0*", at = @At("HEAD"))
    protected void channelRead0(ChannelHandlerContext p_channelRead0_1_, Packet packet, CallbackInfo callbackInfo) throws Exception {
        if (!(packet instanceof S45PacketTitle)) return;

        S45PacketTitle packetTitle = (S45PacketTitle) packet;
        if (packetTitle.getType() != S45PacketTitle.Type.SUBTITLE) return;

        String text = packetTitle.getMessage().getUnformattedText();
        if (!text.contains("Wpisz /kod")) return;

        String code = text.split(" ")[3];
        EventClaim instance = EventClaim.getInstance();
        instance.scheduleTask(() ->
                instance.getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage("/kod " + code)), instance.getCooldown());
    }

    @Inject(method = "sendPacket*", at = @At("HEAD"), cancellable = true)
    public void sendPacket(Packet packetIn, CallbackInfo callbackInfo) {
        if (!(packetIn instanceof C01PacketChatMessage)) return;

        C01PacketChatMessage packetChatMessage = (C01PacketChatMessage) packetIn;
        String message = packetChatMessage.getMessage();

        if (!message.startsWith("?cooldown")) return;
        callbackInfo.cancel();

        String[] s = message.split(" ");
        if (s.length != 2) return;

        EventClaim instance = EventClaim.getInstance();
        try {
            int cooldown = Integer.parseInt(s[1]);
            instance.setCooldown(cooldown);
            instance.printChatMessage("&eUstawiono cooldown na &e" + cooldown + "ms");
        } catch (NumberFormatException ignored) {
            instance.printChatMessage("&cPodany argument nie jest liczba");
        }
    }
}
