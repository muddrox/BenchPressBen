package effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import helpers.Alarm;
import scenes.GameWorld;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by muddr on 7/12/2017.
 */

public class EffectExplode extends Sprite {

    private GameWorld gameWorld;

    private Animation animation;
    private TextureAtlas explodeAtlas;
    private TextureRegion currentFrame;

    private float x;
    private float y;

    private float timePassed;

    private float angle;

    private boolean destroyed;

    public EffectExplode(String name, GameWorld gameWorld, float x, float y){
        super ( new Texture(Gdx.files.internal("spr_explode.png")) , 0, 0, 100, 100 );

        this.gameWorld = gameWorld;

        explodeAtlas = new TextureAtlas(Gdx.files.internal(name));
        animation = new Animation(1/18f, explodeAtlas.getRegions());

        setOriginCenter();

        this.x = x - getWidth()/2;
        this.y = y - getHeight()/2;

        setX(this.x);
        setY(this.y);

        timePassed = 0;

        angle = random(360f);

        destroyed = false;
    }

    public void updateFrames(){
        timePassed += Gdx.graphics.getDeltaTime();
        currentFrame = (TextureRegion) animation.getKeyFrame(timePassed);

        if ( animation.isAnimationFinished(timePassed) ){
            destroyed = true;
        }
    }

    public TextureRegion getCurrentFrame() { return currentFrame; }

    public float getAngle() { return angle; }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        destroyed = true;
    }
}
