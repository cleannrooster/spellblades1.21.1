package com.cleannrooster.spellblades.mixin;

import com.cleannrooster.spellblades.SpellbladesAndSuch;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.internals.SpellRegistry;
import net.spell_engine.internals.casting.SpellCasterEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.cleannrooster.spellblades.SpellbladesAndSuch.MOD_ID;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(at = @At("HEAD"), method = "extinguish", cancellable = true)

    public void extinguishSB(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if(entity instanceof LivingEntity living && living.getStatusEffect(SpellbladesAndSuch.PHOENIXCURSE) != null){
            ci.cancel();
        }
    }


    @Inject(at = @At("HEAD"), method = "extinguishWithSound", cancellable = true)

    public void extinguish2SB(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if(entity instanceof LivingEntity living && living.getStatusEffect(SpellbladesAndSuch.PHOENIXCURSE) != null){
            ci.cancel();
        }
    }
    @Inject(at = @At("HEAD"), method = "isSwimming", cancellable = true)

    public void isCrawlingSB2(CallbackInfoReturnable<Boolean> booleanCallbackInfoReturnable) {
        Entity entity = (Entity) (Object) this;
        if(entity instanceof LivingEntity living){
            if(living.getStatusEffect(SpellbladesAndSuch.SUNDERED)!= null){
                booleanCallbackInfoReturnable.setReturnValue(true);
            }
        }
    }
    @Inject(at = @At("HEAD"), method = "isCrawling", cancellable = true)

    public void isCrawlingSB3(CallbackInfoReturnable<Boolean> booleanCallbackInfoReturnable) {
        Entity entity = (Entity) (Object) this;
        if(entity instanceof LivingEntity living){
            if(living.getStatusEffect(SpellbladesAndSuch.SUNDERED)!= null){
                booleanCallbackInfoReturnable.setReturnValue(true);
            }
        }
    }
    @Inject(at = @At("HEAD"), method = "isOnFire", cancellable = true)

    public void isOnFireSB(CallbackInfoReturnable<Boolean> ci) {
        Entity entity = (Entity) (Object) this;
        if(entity instanceof LivingEntity living && living.getStatusEffect(SpellbladesAndSuch.PHOENIXCURSE) != null){
            ci.setReturnValue(true);
        }
    }

}