package hu.csaszi.gameengine.physics.particle;

import hu.csaszi.gameengine.physics.objects.RenderObject;

import java.util.List;

public interface IParticleEmitter {

    void update(long ellapsedTime);
    void cleanup();

    Particle getBaseParticle();

    List<RenderObject> getParticles();
}