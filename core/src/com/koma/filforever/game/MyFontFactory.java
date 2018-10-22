package com.koma.filforever.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Locale;


public class MyFontFactory {
    public static final String RUSENGUA_CHARACTERS =
            "abcdefghijklmnopqrstuvwxyz"
                    + "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "їЇіІєЄґҐ"
                    + "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
                    + "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
                    + "1234567890.,:;_¡!¿?\"'+-*/()[]={}";

    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    private FreeTypeFontGenerator generator;

    private String LastFontName;

    public MyFontFactory() {
        LastFontName = "";
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    }

    public BitmapFont getFont(String name, float size) {
        if (LastFontName.compareTo(name) != 0) {
            LastFontName = name;
            generator = new FreeTypeFontGenerator(Gdx.files.internal(name));
        }

        parameter.size = (int) (size);
        parameter.characters = RUSENGUA_CHARACTERS;

        return generator.generateFont(parameter);
    }
}
