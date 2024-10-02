package fr.hashtek.spigot.breakffa.death;

import org.bukkit.ChatColor;
import org.bukkit.event.entity.EntityDamageEvent;

public enum DeathReason
{

    VOID        (ChatColor.WHITE        + "࿓",  "est tombé",        "à cause de"    ),
    EXPLODE     (ChatColor.RED          + "✴",  "a explosé",        "à cause de"    ),
    INTOXICATED (ChatColor.DARK_GREEN   + "☣",  "a été intoxiqué",  "par"           ),
    MELEE       (ChatColor.DARK_RED     + "⚔",  "a été poignardé",  "par"           ),
    FIRE        (ChatColor.GOLD         + "♨",  "a fondu",          "à cause de"    ),
    SHOOT       (ChatColor.YELLOW       + "➵",  "a été fusillé",    "par"           ),
    DROWN       (ChatColor.BLUE         + "〰",  "s'est noyé",       "à cause de"    ),
    SUFFOCATION (ChatColor.DARK_AQUA    + "♻",  "a suffoqué",       "à cause de"    ),
    SQUASHED    (ChatColor.BLACK        + "⏍",  "a été écrasé",     "par"           ),
    THORNS      (ChatColor.DARK_AQUA    + "✴",  "a été transpercé", "par"           ),
    OTHER       (ChatColor.DARK_GRAY    + "✖",  "est mort",         "à cause de"    );


    private final String symbol;
    private final String reason;
    private final String by;


    /**
     * Creates a new Death reason.
     *
     * @param   symbol  Death symbol
     * @param   reason  Death reason
     * @param   by      Death author
     */
    DeathReason(String symbol, String reason, String by)
    {
        this.symbol = symbol;
        this.reason = reason;
        this.by = by;
    }


    /**
     * "Converts" a {@link org.bukkit.event.entity.EntityDamageEvent.DamageCause}
     * to a {@link DeathReason}.
     *
     * @param   cause   Damage cause
     * @return  Converted DeathReason.
     */
    public static DeathReason fromDamageCause(EntityDamageEvent.DamageCause cause)
    {
        return switch (cause) {
            case FIRE, FIRE_TICK -> DeathReason.FIRE;
            case THORNS -> DeathReason.THORNS;
            default -> DeathReason.VOID;
        };
    }


    /**
     * @return  Death symbol
     */
    public String getSymbol()
    {
        return this.symbol;
    }

    /**
     * @return  Death reason
     */
    public String getReason()
    {
        return this.reason;
    }

    /**
     * @return  Death author
     */
    public String getBy()
    {
        return this.by;
    }

}
