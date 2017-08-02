package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.utils.Array;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch sb;
	private Sprite sprite;
	private World world;
	private Box2DDebugRenderer rend;
	private Body ball;
	private Array<Body> tmp;
	private OrthographicCamera cam;
	private Vector2 move = new Vector2();
	private static float PPM = 10f;
	private float speed = 6f;
	
	private float x, y;
	
	@Override
	public void create () {
		sb = new SpriteBatch();
		world = new World(new Vector2(0, -9.81f), true);
		//world.setGravity(new Vector2(0, 0));
		rend = new Box2DDebugRenderer();
		sprite = new Sprite(new Texture("p.jpg"));
		tmp = new Array<Body>();
		cam = new OrthographicCamera();
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		
		PolygonShape box = new PolygonShape();
		box.setAsBox(5f/PPM, 0.5f/PPM);
		
		bdef.type = BodyType.StaticBody;
		
		fdef.shape = box;
		
		//ball = world.createBody(bdef);
		//ball.createFixture(fdef);
		
		PolygonShape wall = new PolygonShape();
		wall.setAsBox(20.5f/PPM, 0.5f/PPM);
		bdef.position.set(-10/PPM, -10/PPM);
		
		fdef.shape = wall;
		
		ball = world.createBody(bdef);
		ball.createFixture(fdef);
		wall.setAsBox(0.5f/PPM, 100/PPM);
		bdef.position.set(-10/PPM, -10/PPM);
		
		fdef.shape = wall;
		
		ball = world.createBody(bdef);
		ball.createFixture(fdef);
		
		bdef.position.set(10/PPM, -10/PPM);
		
		ball = world.createBody(bdef);
		ball.createFixture(fdef);
		
		PolygonShape box2 = new PolygonShape();
		box2.setAsBox(2f/PPM, 2f/PPM);
		
		wall.setAsBox(100f/PPM, 0.5f/PPM);
		bdef.position.set(10/PPM, 10/PPM);
		
		ball = world.createBody(bdef);
		ball.createFixture(fdef);
		
		bdef.type = BodyType.DynamicBody;
		bdef.position.set(0.1f, 8/PPM);
		//bdef.active = true;
		
		fdef.shape = box2;
		fdef.density = 5f;
		
		ball = world.createBody(bdef);
		ball.createFixture(fdef);
		
		
		sprite.setSize(4f/PPM, 4f/PPM);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		ball.setUserData(sprite);
		
		CircleShape bigBall = new CircleShape();
		
		bdef.type = BodyType.DynamicBody;
		fdef.restitution = 0.4f;
		
		bigBall.setRadius(1f/PPM);
		bdef.position.set(-8f/PPM, -.9f/PPM);
		
		fdef.shape = bigBall;
		fdef.density = 0;
		fdef.friction = .05f;
		
		ball = world.createBody(bdef);
		ball.createFixture(fdef);
		
		sprite = new Sprite(new Texture("Ball.png"));
		sprite.setSize(2f/PPM, 2f/PPM);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		ball.setUserData(sprite);
		
		//ball.applyLinearImpulse(1.5f, 3.7f, ball.getPosition().x, ball.getPosition().y, true);
		//ball.applyAngularImpulse(0.5f, true);
		
		
		Gdx.input.setInputProcessor(new InputProcessor(){
			@Override
			public boolean keyDown(int k) {
				if(k == Keys.D){
					move.x = speed;
				}
				if(k == Keys.A){
					move.x = -speed;
				}
				if(k == Keys.W){
					move.y = speed + 15f;
				}
				if(k == Keys.S){
					move.y = -speed;
				}
				return true;
			}

			@Override
			public boolean keyUp(int k) {
				if(k == Keys.D){
					move.x = 0;
				}
				if(k == Keys.A){
					move.x = -0;
				}
				if(k == Keys.W){
					move.y = 0;
				}
				if(k == Keys.S){
					move.x = 0;
				}
				return true;
			}

			@Override
			public boolean keyTyped(char character) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int x1, int y1, int pointer,
					int button) {
				x = x1;
				y = y1;
				move.set(x1 - ball.getPosition().x, y1 - ball.getPosition().y);
				return false;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button) {
				move.set(ball.getPosition().x, ball.getPosition().y);
				return false;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int screenX, int screenY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				// TODO Auto-generated method stub
				return false;
			}
			
		});
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(cam.combined);
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		ball.applyForceToCenter(move, true);
		sb.begin();
		world.getBodies(tmp);
		for(Body body : tmp){
			if(body.getUserData() != null && body.getUserData() instanceof Sprite){
				Sprite sprite2 = (Sprite) body.getUserData();
				sprite2.setPosition((body.getPosition().x-sprite2.getWidth()/2), body.getPosition().y-sprite2.getHeight()/2);
				sprite2.draw(sb);
			}
		}
		sb.end();
		
		rend.render(world, cam.combined);
	}
}
