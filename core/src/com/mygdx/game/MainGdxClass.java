package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;


public class MainGdxClass extends ApplicationAdapter {

	public float dT, camPos, angle;
	public Environment environment;
	public PerspectiveCamera cam;
	public ModelBatch modelBatch;
	public ModelBuilder modelBuilder;
	public Color dirLights;
	public float camFact, rotFact;
	public Vector3 touchPoint;
	public int selP;
	public SpriteBatch spriteBatch;

	private Texture buttonPlay;
	private Texture buttonPause;

	public Body Sun, Earth, Mars, Moon;
	public ArrayList<Body> bodies = new ArrayList<Body>();

	private static final int PLAY_BUTTON_WIDTH = 100;
	private static final int PLAY_BUTTON_HEIGHT = 50;
	private static final int PAUSE_BUTTON_WIDTH = 100;
	private static final int PAUSE_BUTTON_HEIGHT = 50;

	private static final int PLAY_BUTTON_Y = 100;
	private static final int PAUSE_BUTTON_Y = 100;

	private boolean play;
	private boolean pause;


	@Override
	public void create() {

		spriteBatch = new SpriteBatch();

		//Buttons
		buttonPlay = new Texture("play_button.png");
		buttonPause = new Texture("pause_button.png");

		// Model batch to draw all stars/bodies
		modelBatch = new ModelBatch();
		// Builds each object
		modelBuilder = new ModelBuilder();


		dT = 0;
		angle = 0;
		camPos = 5000f;
		dirLights = new Color(Color.WHITE);
		camFact = 5000f;
		rotFact = 10;
		CameraSet();
		cam.position.set(0, camPos, camPos);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 500000f;
		cam.update();
		touchPoint = new Vector3();
		selP = 0; /* Sun */


		PlanetsCreate();
		bodies.add(Sun);
		bodies.add(Earth);
		bodies.add(Moon);
		bodies.add(Mars);


		play = true;
		pause = false;
	}



	@Override
	public void render() {


		dT = Gdx.graphics.getDeltaTime();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		cam.lookAt(bodies.get(selP).x, bodies.get(selP).y, bodies.get(selP).z);
		cam.update();
		DrawBodies();
		Orbit();
		spriteBatch.begin();


		if (Gdx.input.getX() < 100 + PAUSE_BUTTON_WIDTH && Gdx.input.getX() > 100 && Gdx.graphics.getHeight() - Gdx.input.getY() < PAUSE_BUTTON_Y + PAUSE_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > PAUSE_BUTTON_Y && !pause) {
			//spriteBatch.draw(buttonPause, 100, PAUSE_BUTTON_Y, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				pause = true;
				//spriteBatch.draw(buttonPlay, 100, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			}
		}

		if (Gdx.input.getX() < 100 + PLAY_BUTTON_WIDTH && Gdx.input.getX() > 100 && Gdx.graphics.getHeight() - Gdx.input.getY() < PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT && Gdx.graphics.getHeight() - Gdx.input.getY() > PLAY_BUTTON_Y && !play && pause) {
			//spriteBatch.draw(buttonPlay, 100, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				//Gdx.app.exit();
				play = true;
				pause = false;
				//spriteBatch.draw(buttonPause, 100, PAUSE_BUTTON_Y, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
			}
		}




		if (play){
			spriteBatch.draw(buttonPause, 100, PAUSE_BUTTON_Y, PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT);
		}

		if (pause) {
			spriteBatch.draw(buttonPlay, 100, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
		}

		update();

		spriteBatch.end();
	}




	public void CameraSet(){
		cam = new PerspectiveCamera(50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}




	private void PlanetsCreate() {

		Sun = new Body(new Vector2(0,0), new Vector2(0,0));
		Sun.name = "Sun";
		Sun.x = 0;
		Sun.y = 0;
		Sun.z = 0;
		Sun.o = 0;
		Sun.div = 80;
		Sun.c = new Color(Color.ORANGE.add(Color.YELLOW));
		Sun.r = 700f;
		Sun.m = modelBuilder.createSphere(Sun.r, Sun.r, Sun.r, Sun.div, Sun.div,
				new Material(ColorAttribute.createDiffuse(Sun.c)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		Sun.i = new ModelInstance(Sun.m);
		Sun.i.transform.translate(Sun.x, Sun.y, Sun.z);

		//Earth
		Earth = new Body(new Vector2(0,0), new Vector2(0,0));
		Earth.name = "Earth";
		Earth.d = Sun.r + 500f;
		Earth.r = 130f;
		Earth.c = new Color(Color.CYAN);
		Earth.x = 0;
		Earth.y = Earth.d;
		Earth.z = 0;
		Earth.o = (float) (365.25 / 365.25);
		Earth.div = 40;
		Earth.m = modelBuilder.createSphere(Earth.r, Earth.r, Earth.r, Earth.div, Earth.div,
				new Material(ColorAttribute.createDiffuse(Earth.c)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		Earth.i = new ModelInstance(Earth.m);
		Earth.i.transform.translate(Earth.x, Earth.y, Earth.z);

		//Moon
		Moon = new Body(new Vector2(0,0), new Vector2(0,0));
		Moon.name = "Earth's Moon";
		Moon.d = Earth.r + 10f;
		Moon.r = 20f;
		Moon.c = new Color(Color.LIGHT_GRAY);
		Moon.x = Earth.x;
		Moon.y = Earth.y + Moon.d;
		Moon.z = Earth.z;
		Moon.o = (float) (28.5 / 365.25);
		Moon.div = 40;
		Moon.m = modelBuilder.createSphere(Moon.r, Moon.r, Moon.r, Moon.div, Moon.div,
				new Material(ColorAttribute.createDiffuse(Moon.c)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		Moon.i = new ModelInstance(Moon.m);
		Moon.i.transform.translate(Moon.x, Moon.y, Moon.z);

		//Mars
		Mars = new Body(new Vector2(0,0), new Vector2(0,0));
		Mars.name = "Mars";
		Mars.d = Sun.r + 1000f;
		Mars.r = 130f;
		Mars.c = new Color(Color.RED);
		Mars.x = Mars.d;
		Mars.y = 0;
		Mars.z = 0;
		Mars.o = (float) (686.98 / 365.25);
		Mars.div = 40;
		Mars.m = modelBuilder.createSphere(Mars.r, Mars.r, Mars.r, Mars.div, Mars.div,
				new Material(ColorAttribute.createDiffuse(Mars.c)),
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		Mars.i = new ModelInstance(Mars.m);
		Mars.i.transform.translate(Mars.x, Mars.y, Mars.z);

	}

	private void Orbit()
	{
		if (play && !pause) {

			angle = angle + (1 / rotFact);
			angle = (float) Math.toRadians(angle);
			for (Body a : bodies) {
				if (a == Moon) {
					float newX = (float) Math.cos((1 / a.o) * angle) * (a.x - Earth.x) - (float) Math.sin((1 / a.o) * angle) * (a.y - Earth.y) + Earth.x;
					float newY = (float) Math.sin((1 / a.o) * angle) * (a.x - Earth.x) + (float) Math.cos((1 / a.o) * angle) * (a.y - Earth.y) + Earth.y;
					a.i = null;
					a.i = new ModelInstance(a.m);
					a.i.transform.translate(newX, newY, 0);
					a.x = newX;
					a.y = newY;
				} else if (a != Sun) {
					float newX = (float) Math.cos((1 / a.o) * angle) * (a.x - Sun.x) - (float) Math.sin((1 / a.o) * angle) * (a.y - Sun.y) + Sun.x;
					float newY = (float) Math.sin((1 / a.o) * angle) * (a.x - Sun.x) + (float) Math.cos((1 / a.o) * angle) * (a.y - Sun.y) + Sun.y;
					a.i = null;
					a.i = new ModelInstance(a.m);
					a.i.transform.translate(newX, newY, 0);
					// Moon must ALSO translate based on Earth
					if (a == Earth) {
						Moon.x += newX - a.x;
						Moon.y += newY - a.y;
						Moon.i = null;
						Moon.i = new ModelInstance(Moon.m);
						Moon.i.transform.translate(Moon.x, Moon.y, 0);
					}
					a.x = newX;
					a.y = newY;
				}
			}
		}
	}

	public void DrawBodies(){
		modelBatch.begin(cam);
		for (Body a : bodies){
			modelBatch.render(a.i, environment);}
		modelBatch.end();
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		bodies.clear();
		for (Body a : bodies){
			a.m.dispose();}
		spriteBatch.dispose();
		buttonPause.dispose();

	}

	@Override
	public void resize(int width, int height) {
	}

	public void update() {

		Earth.update();
		Moon.update();
		Mars.update();

	}

	@Override
	public void pause() {

		Earth = new Body(new Vector2(Earth.x, Earth.y), new Vector2(0, 0));
		Moon = new Body(new Vector2(Moon.x, Moon.y), new Vector2(0, 0));
		Mars = new Body(new Vector2(Mars.x, Mars.y), new Vector2(0, 0));


		play = false;


	}

	@Override
	public void resume() {


	}



}
