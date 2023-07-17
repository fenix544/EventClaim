package me.fenix;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Session;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Mod(modid = "eventclaim", version = "1.0")
public class EventClaim {

    private static EventClaim instance;

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private Minecraft minecraft;
    private int cooldown;

    public static EventClaim getInstance() {
        if (instance == null) {
            instance = new EventClaim();
        }
        return instance;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }

    public void setMinecraft(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    public void scheduleTask(Runnable task, int milliseconds) {
        this.executor.schedule(task, milliseconds, java.util.concurrent.TimeUnit.MILLISECONDS);
    }

    public void shutdownExecutor() {
        this.executor.shutdown();
    }

    public void printChatMessage(String chatMessage) {
        this.minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(chatMessage.replace("&", "\u00a7")));
    }

    public void setUsername(String username) {
        try {
            Field sessionField = Minecraft.class.getDeclaredField("session");
            sessionField.setAccessible(true);
            Session o = (Session) sessionField.get(this.minecraft);

            Field usernameField = o.getClass().getDeclaredField("username");
            usernameField.setAccessible(true);
            usernameField.set(o, username);

            Field type = o.getClass().getDeclaredField("sessionType");
            type.setAccessible(true);
            type.set(o, Session.Type.LEGACY);

            sessionField.set(this.minecraft, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
