package com.cleannrooster.spellblades.config;

import net.fabricmc.fabric.api.networking.v1.ServerConfigurationNetworking;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.network.ServerPlayerConfigurationTask;
import net.spell_engine.config.ServerConfig;
import net.spell_engine.network.Packets;

import java.util.function.Consumer;

public  record ConfigurationTask(
        com.cleannrooster.spellblades.config.ServerConfig config) implements ServerPlayerConfigurationTask {
    public static final String name = "spellbladenext:config";
    public static final ServerPlayerConfigurationTask.Key KEY = new ServerPlayerConfigurationTask.Key("spell_engine:config");

    public ConfigurationTask(com.cleannrooster.spellblades.config.ServerConfig config) {
        this.config = config;
    }

    public ServerPlayerConfigurationTask.Key getKey() {
        return KEY;
    }

    public void sendPacket(Consumer<Packet<?>> sender) {
        ConfigSync packet = new ConfigSync(this.config);
        sender.accept(ServerConfigurationNetworking.createS2CPacket(packet));
    }

    public com.cleannrooster.spellblades.config.ServerConfig config() {
        return this.config;
    }
}
