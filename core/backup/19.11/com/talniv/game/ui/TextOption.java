package com.talniv.game.ui;

public class TextOption extends Option{
    private Text text;

    public TextOption(String name, Text text) {
        super(name);
        this.text = text;
    }

    public Text getText() {
        return text;
    }
}
