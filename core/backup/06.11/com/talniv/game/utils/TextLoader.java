package com.talniv.game.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.talniv.game.ui.Option;
import com.talniv.game.ui.SelectionBox;

public class TextLoader {

    public static Text loadText(String path) {
        ArrayMap<String, SelectionBox> text = new ArrayMap<String, SelectionBox>();
        FileHandle info = Gdx.files.internal(path);
        String[] lines = info.readString().split("\\r\\n");

        for (int i = 0; i < lines.length; i++) {
            if (!lines[i].endsWith("//question")){
                text.put(lines[i], null);
            }
            else {
                String question = lines[i].replace("//question","");
                Array<Option> options = new Array<Option>();
                i++;
                for (int j = i; j < lines.length; j++) {
                    if (lines[j].equals("//end question")){
                        break;
                    }
                    options.add(new Option(lines[j]));
                    i++;
                }
                text.put(question, new SelectionBox(options));
            }
        }

        return new Text(text);
    }
}
