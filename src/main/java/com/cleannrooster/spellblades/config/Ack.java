package com.cleannrooster.spellblades.config;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.spell_engine.network.Packets;

public  record Ack(String code) implements CustomPayload {
    public static Identifier ID = Identifier.of("spellbladenext", "ack");
    public static final CustomPayload.Id<Ack> PACKET_ID;
    public static final PacketCodec<PacketByteBuf, Ack> CODEC;

    public Ack(String code) {
        this.code = code;
    }

    public void write(PacketByteBuf buffer) {
        buffer.writeString(this.code);
    }

    public static Ack read(PacketByteBuf buffer) {
        String code = buffer.readString();
        return new Ack(code);
    }

    public CustomPayload.Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

    public String code() {
        return this.code;
    }

    static {
        PACKET_ID = new CustomPayload.Id(ID);
        CODEC = PacketCodec.of(Ack::write, Ack::read);
    }
}