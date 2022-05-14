package xyz.minerune.economy;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

    private final Economy plugin;

    public EventListener(Economy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Economy.getInstance().create(event.getPlayer(), 1.0);
    }
}
