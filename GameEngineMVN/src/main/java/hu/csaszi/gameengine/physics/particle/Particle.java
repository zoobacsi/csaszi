package hu.csaszi.gameengine.physics.particle;

import hu.csaszi.gameengine.physics.objects.RenderObject;
import hu.csaszi.gameengine.physics.objects.Transform;
import hu.csaszi.gameengine.physics.world.World;
import hu.csaszi.gameengine.render.core.gl.Sprite;
import hu.csaszi.gameengine.render.core.gl.renderer.Camera;
import hu.csaszi.gameengine.render.core.gl.shaders.Shader;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Particle extends RenderObject {

    private Vector3f speed;
    private float scale;
    private Vector3f rotation;
    /**
     * Time to live for particle in milliseconds.
     */
    private long ttl;

    public Particle(Sprite[] sprites, Transform transform, Vector3f speed, long ttl) {
        super(sprites, transform, "box");
        this.speed = new Vector3f(speed);
        this.ttl = ttl;
    }

    public Particle(Particle baseParticle) {
        super(baseParticle.getSprites(), baseParticle.getTransform(), "box");
        Vector3f aux = new Vector3f(baseParticle.getTransform().getPosition());
        setPosition(aux.x, aux.y, aux.z);
        aux = baseParticle.getRotation();
        setRotation(aux.x, aux.y, aux.z);
        setScale(baseParticle.getScale());
        this.speed = new Vector3f(baseParticle.speed);
        this.ttl = baseParticle.geTtl();
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation = new Vector3f(x, y, z);
    }

    public void setPosition(float x, float y, float z) {
        this.transform.getPosition().set(x, y, z);
    }

    public Vector3f getPosition() {
        return this.transform.pos;
    }

    public Vector3f getSpeed() {
        return speed;
    }

    public void setSpeed(Vector3f speed) {
        this.speed = speed;
    }

    public long geTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    /**
     * Updates the Particle's TTL
     *
     * @param elapsedTime Elapsed Time in milliseconds
     * @return The Particle's TTL
     */
    public long updateTtl(long elapsedTime) {
        this.ttl -= elapsedTime;
        return this.ttl;
    }

    @Override
    public void render(Shader shader, Camera camera, World world) {

        if (isDestroyed) {
            return;
        }

        Matrix4f target = camera.getProjection();
        target.mul(world.getWorldMatrix());
        shader.bind();
        shader.setUniform("sampler", 1);
        shader.setUniform("projection", transform.getProjection(target).rotateZ(-camera.getTargetRotate()));

        if (sprites != null && sprites.length > useSprite) {
            sprites[useSprite].bind(direction.getId(), 1);
        }
        model.render();
    }
}