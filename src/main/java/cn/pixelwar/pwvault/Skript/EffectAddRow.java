package cn.pixelwar.pwvault.Skript;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import cn.pixelwar.pwvault.PlayerData.PlayerVaultDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import javax.annotation.Nullable;

public class EffectAddRow  extends Effect {

    private Expression<Player> playerin;
    private Expression<Number> numin;
    static {
        Skript.registerEffect(EffectAddRow.class, new String[] {
                "addvaultrow %player% %number%",
        });
    }
    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parser) {
        this.playerin = (Expression<Player>) expressions[0];
        this.numin = (Expression<Number>) expressions[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "add vault row num to player: " + playerin.toString(event, debug);
    }

    @Override
    protected void execute(Event event) {
        Player player = playerin.getSingle(event);
        int num = numin.getSingle(event).intValue();
        int old = PlayerVaultDataManager.playerVaultDataMap.get(player.getName()).getRowAmount();
        PlayerVaultDataManager playerVaultDataManager = new PlayerVaultDataManager();
        playerVaultDataManager.setRow(player, old+num);


    }
}

