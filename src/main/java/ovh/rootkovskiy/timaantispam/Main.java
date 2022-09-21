package ovh.rootkovskiy.timaantispam;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener
{
    private HashMap<UUID, Long> cooldown;

    public void onEnable() {
        cooldown = new HashMap<UUID, Long>();
        Bukkit.getPluginManager().registerEvents((Listener)this, (Plugin)this);
        saveDefaultConfig();
        System.out.println(Console.ANSI_GREEN+"#-#-#-#-#-#-#-#-#"+Console.ANSI_RESET);
        System.out.println(Console.ANSI_CYAN+   "TimaAntiSpam "+getDescription().getVersion()+" Loaded and Enabled!"+Console.ANSI_RESET);
        System.out.println(Console.ANSI_CYAN+   "MC Version: "+getServer().getBukkitVersion()+Console.ANSI_RESET);
        System.out.println(Console.ANSI_CYAN+   "Author: Timur Rootkovskiy (Adminov)"+Console.ANSI_RESET);
        System.out.println(Console.ANSI_CYAN+   "VK: @timurroot"+Console.ANSI_RESET);
        System.out.println(Console.ANSI_GREEN+"#-#-#-#-#-#-#-#-#"+Console.ANSI_RESET);
    }

    public void onDisable() {
        System.out.println(Console.ANSI_GREEN+"#-#-#-#-#-#-#-#-#"+Console.ANSI_RESET);
        System.out.println(Console.ANSI_CYAN+   "TimaAntiSpam "+getDescription().getVersion()+" Disabled!"+Console.ANSI_RESET);
        System.out.println(Console.ANSI_CYAN+   "MC Version: "+getServer().getBukkitVersion()+Console.ANSI_RESET);
        System.out.println(Console.ANSI_CYAN+   "Author: Timur Rootkovskiy (Adminov)"+Console.ANSI_RESET);
        System.out.println(Console.ANSI_CYAN+   "VK: @timurroot"+Console.ANSI_RESET);
        System.out.println(Console.ANSI_CYAN+   "Goodbye ;p"+Console.ANSI_RESET);
        System.out.println(Console.ANSI_GREEN+"#-#-#-#-#-#-#-#-#"+Console.ANSI_RESET);
    }

    @EventHandler
    public void onChat(final AsyncPlayerChatEvent e) {
        final UUID uuid = e.getPlayer().getUniqueId();
        final Player p = e.getPlayer();
        if (!p.hasPermission("timaantispam.bypass")) {
            if (cooldown.containsKey(uuid)) {
                final int time = (int)((System.currentTimeMillis() - cooldown.get(uuid)) / 1000L);
                if (time < getConfig().getInt("COOLDOWN")) {
                    final String noSpam = getConfig().getString("SPAM_MESSAGE").replace("&", "§");
                    e.setCancelled(true);
                    p.sendMessage(noSpam);
                }
                else {
                    cooldown.put(uuid, System.currentTimeMillis());
                }
            }
            else {
                cooldown.put(uuid, System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void onLeave(final PlayerQuitEvent e) {
        cooldown.remove(e.getPlayer().getUniqueId());
    }
}
