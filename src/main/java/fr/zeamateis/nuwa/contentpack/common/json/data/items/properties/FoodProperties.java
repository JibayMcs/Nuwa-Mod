package fr.zeamateis.nuwa.contentpack.common.json.data.items.properties;

import fr.zeamateis.nuwa.contentpack.common.json.data.EffectObject;
import net.minecraft.item.Food;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

//TODO Implements Effects
public class FoodProperties {

    private int hunger;
    private float saturation;
    private boolean meat;
    private boolean alwaysEdible;
    private boolean fastToEat;

    private List<FoodEffect> effects;

    private String fromItem;

    public FoodProperties() {
        this.hunger = 0;
        this.saturation = 0.0F;
        this.meat = false;
        this.alwaysEdible = false;
        this.fastToEat = false;
    }

    public Food getFood() {
        Food.Builder builder = new Food.Builder();

        builder.hunger(hunger);
        builder.saturation(saturation);
        if (meat) builder.meat();
        if (alwaysEdible) builder.setAlwaysEdible();
        if (fastToEat) builder.fastToEat();

        if (effects != null) {
            effects.forEach(effectObject -> {
                Effect parsedEffect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effectObject.effect.getEffectName()));
                if (parsedEffect != null) {
                    EffectInstance effectInstance = new EffectInstance(parsedEffect, effectObject.effect.getDuration(), effectObject.effect.getAmplifier(), effectObject.effect.isAmbient(), effectObject.effect.isShowParticles());
                    builder.effect(effectInstance, effectObject.probability);
                }
            });
        }

        return fromItem != null ? ForgeRegistries.ITEMS.getValue(new ResourceLocation(fromItem)) != null && ForgeRegistries.ITEMS.getValue(new ResourceLocation(fromItem)).getFood() != null ? ForgeRegistries.ITEMS.getValue(new ResourceLocation(fromItem)).getFood() : builder.build() : builder.build();
    }

    static class FoodEffect {
        private EffectObject effect;
        private float probability;
    }
}
