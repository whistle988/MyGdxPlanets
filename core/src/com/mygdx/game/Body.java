package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;

public class Body {
    public Vector2 vector2;
    public Vector2 vector21;

    public String name;
    public float x, y, z, r, d, o;
    public int div;
    public Color c;
    public Model m;
    public ModelInstance i;


    public Body(Vector2 vector2, Vector2 vector21) {
        this.vector2 = vector2;
        this.vector21 = vector21;
    }

    public void update() {
        vector2.add(vector21);
        vector21.scl(Gdx.graphics.getDeltaTime(),  Gdx.graphics.getDeltaTime());
    }
}
