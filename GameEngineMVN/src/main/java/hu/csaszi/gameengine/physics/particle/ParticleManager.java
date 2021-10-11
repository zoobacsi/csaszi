package hu.csaszi.gameengine.physics.particle;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.physics.objects.RenderObject;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class ParticleManager {

    private Shader shader;
    private List<IParticleEmitter> particleEmitters = new ArrayList<>();

    public void addParticleEmitter(IParticleEmitter particleEmitter) {
        particleEmitters.add(particleEmitter);
    }

    public void removeParticleEmitter(IParticleEmitter particleEmitter) {
        particleEmitters.remove(particleEmitter);
    }

    public void update(float delta, GameManager gameManager) {
        for (IParticleEmitter particleEmitter : particleEmitters) {
            particleEmitter.update((long)delta*60);
        }
    }

    public ParticleManager() {
        shader = new Shader("particle");

        shader.setUniform("projectionMatrix", new Matrix4f());
        shader.setUniform("texture_sampler", 0);
    }

    public void renderParticles(Camera camera, World world) {

        shader.setUniform("texture_sampler", 0);
        Matrix4f projectionMatrix = camera.getProjection();
        shader.setUniform("projectionMatrix", projectionMatrix);

        shader.bind();

//        Matrix4f viewMatrix = transformation.getViewMatrix();
//        IParticleEmitter[] emitters = scene.getParticleEmitters();
//        int numEmitters = emitters != null ? emitters.length : 0;

        for (IParticleEmitter particleEmitter : particleEmitters) {

            for(RenderObject particle : particleEmitter.getParticles()) {
                particle.render(shader, camera, world);
            }
//            mesh.renderList((emitter.getParticles()), (GameItem gameItem) -> {
//                        Matrix4f modelViewMatrix = transformation.buildModelViewMatrix(gameItem, viewMatrix);
//                        particlesShaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
//                    }
//            );
        }
    }
}
