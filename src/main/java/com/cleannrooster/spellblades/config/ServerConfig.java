package com.cleannrooster.spellblades.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "server")
public class ServerConfig  implements ConfigData {
    public ServerConfig(){}

    @Comment("Damage multiplier for spin attacks (eg: Whirlwind) (Default: 1.0)")
    public  float spin_attack_coeff = 1.0F;
    @Comment("Spellblade passive maximum Strength and Haste(Default: 10")
    public  int passive = 10;
}
