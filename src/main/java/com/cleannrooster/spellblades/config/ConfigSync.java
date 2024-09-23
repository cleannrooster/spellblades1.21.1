package com.cleannrooster.spellblades.config;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import com.google.gson.Gson;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record ConfigSync(ServerConfig config) implements CustomPayload {
    public static Identifier ID = Identifier.of(SpellbladesAndSuch.MOD_ID, "config_sync");
    public static final CustomPayload.Id<ConfigSync> PACKET_ID = new CustomPayload.Id<>(ID);
    public static final PacketCodec<PacketByteBuf, ConfigSync> CODEC = PacketCodec.of(ConfigSync::write, ConfigSync::read);
    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

    private static final Gson gson = new Gson();

    public void write(PacketByteBuf buffer) {
        var json = gson.toJson(this.config);
        buffer.writeString(json);
    }

    public static ConfigSync read(PacketByteBuf buffer) {
        var gson = new Gson();
        var json = buffer.readString();
        var config = gson.fromJson(json, ServerConfig.class);
        return new ConfigSync(config);
    }

}
