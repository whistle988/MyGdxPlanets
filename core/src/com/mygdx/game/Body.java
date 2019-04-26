package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;

import java.util.ArrayList;

public class Body extends ApplicationAdapter {

	public class Planet{
		public String name;
		public float x, y, z, r, d, o;
		public int div;
		public Color c;
		public Model m;
		public ModelInstance i;
	}
	public class Satellite{
		public float x, y, z, d;
		public ModelInstance i;
	}

	public Planet Sol, Earth, Mars;
	public Satellite Moon;

	public ArrayList<Planet> planets = new ArrayList<Planet>();
	public ArrayList<ModelInstance> satellites = new ArrayList<ModelInstance>();

	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
