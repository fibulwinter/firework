package net.examplefibulwinter.newpaint;

import net.examplefibulwinter.firework.RandUtils;
import net.examplefibulwinter.firework.V;

public class Emitters {
    public static Emitter at(final int age, final Emitter emitter) {
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                if (master.getAge() == age) {
                    emitter.emit(master, particles);
                }
            }
        };
    }

    public static Emitter at(final int minAge, final int maxAge, final Emitter emitter) {
        final int age = RandUtils.rand(minAge, maxAge);
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                if (master.getAge() == age) {
                    emitter.emit(master, particles);
                }
            }
        };
    }

    public static Emitter ending(final Emitter emitter) {
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                if (master.isRemove()) {
                    emitter.emit(master, particles);
                }
            }
        };
    }

    public static Emitter repeat(final int count, final Emitter emitter) {
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                for (int i = 0; i < count; i++) {
                    emitter.emit(master, particles);
                }
            }
        };
    }

    public static Emitter timeToLive(final int ttl) {
        return at(ttl, new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                master.die();
            }
        });
    }

    public static Emitter explode(final Painters painters, final int color) {
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                V velocity = RandUtils.randomVelocity(5);
                velocity.add(master.getVelocity());
                Particle particle = new Particle(new V(master.getPosition()), velocity);
                particle.setPainter(painters.small(RandUtils.randomSubColor(color)));
                particle.add(timeToLive(RandUtils.rand(3, 5)));
                particles.add(particle);
            }
        };
    }

    public static Emitter shot(final Painter painter) {
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                Particle particle = new Particle(VirtualScreen.getRelative(0.5f, 1f),
                        new V(RandUtils.rand(-2.5f, 2.5f), RandUtils.rand(-30, -25), 0));
                particle.add(Emitters.timeToLive(20));
                particle.setPainter(painter);
                particles.add(particle);
            }
        };
    }

    public static Emitter explode(final float speed, final Painter painter) {
        return new Emitter() {
            @Override
            public void emit(Particle master, Particles particles) {
                V velocity = RandUtils.randomVelocity(speed);
                velocity.add(master.getVelocity());
                Particle particle = new Particle(new V(master.getPosition()),
                        velocity);
                particle.add(Emitters.timeToLive(RandUtils.rand(15, 20)));
                particle.setPainter(painter);
                particles.add(particle);
            }
        };
    }
}
