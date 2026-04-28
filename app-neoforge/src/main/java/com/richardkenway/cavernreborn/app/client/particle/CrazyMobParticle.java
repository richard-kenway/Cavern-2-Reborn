package com.richardkenway.cavernreborn.app.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class CrazyMobParticle extends PortalParticle {
    private CrazyMobParticle(ClientLevel level, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(level, x, y, z, motionX, motionY, motionZ);
        float f = this.random.nextFloat() * 0.5F + 0.4F;
        float color = 0.65F * f * 0.8F;
        this.setColor(color, color, color);
    }

    @OnlyIn(Dist.CLIENT)
    public static final class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType type,
            ClientLevel level,
            double x,
            double y,
            double z,
            double motionX,
            double motionY,
            double motionZ
        ) {
            CrazyMobParticle particle = new CrazyMobParticle(level, x, y, z, motionX, motionY, motionZ);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }
}
