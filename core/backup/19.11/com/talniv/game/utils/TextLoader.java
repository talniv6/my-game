package com.talniv.game.utils;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.talniv.game.ui.Option;
import com.talniv.game.ui.SelectionBox;

public class TextLoader {

    private static Text parse(Array<String> lines, ArrayMap<String, SelectionBox> text){
        String curLine, nextLine;

        while (lines.size > 0 && !lines.get(0).endsWith("->") && !lines.get(0).endsWith("}")){
            curLine = lines.get(0).replaceAll("\t", "");
            // not last line
            if (lines.size > 1){
                nextLine = lines.get(1).replaceAll("\t", "");
                if (nextLine.equals("{")){
                    lines.removeIndex(0);
                    lines.removeIndex(0);
                    Array<Option> options = new Array<Option>();
                    while (!lines.get(0).endsWith("}")) {
                        String optionName = lines.get(0).replace("->", "").replaceAll("\t", "");
                        lines.removeIndex(0);
                        TextOption option = new TextOption(optionName, parse(lines, new ArrayMap<String, SelectionBox>()));
                        options.add(option);
                    }
                    lines.removeIndex(0);
                    SelectionBox selectionBox = new SelectionBox(options);
                    text.put(curLine, selectionBox);
                }
                else {
                    text.put(curLine, null);
                    lines.removeIndex(0);
                }
            }
            // last  line
            else
            {
                text.put(curLine, null);
                lines.removeIndex(0);
            }
        }
        return new Text(text);
    }

    public static Text loadText(String path) {
        ArrayMap<String, SelectionBox> text = new ArrayMap<String, SelectionBox>();
        FileHandle info = Gdx.files.internal(path);
        String[] lines = info.readString().split("\\r\\n");

        return parse(new Array<String>(lines), text);
    }
}
