package com.talniv.game.ui;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.Item;
import com.talniv.game.GameObjects.Player;

public class ItemsRenderer extends UiElement{
    private SelectionBox items;
    private Rectangle position = new Rectangle(Game.V_WIDTH/3, Game.V_HEIGHT/3, Game.V_WIDTH/3, Game.V_HEIGHT/3);
    private SelectionBoxRenderer selectionBoxRenderer;

    @Override
    public void tick(float dt) {
        if (!active)
            return;
        selectionBoxRenderer.tick();
        if (selectionBoxRenderer.isChoiceMade()){
            selectionBoxRenderer.finish();
            active = false;
            Item selected = ((ItemsListEntity)items.getOptions().get(items.getSelected())).getItem();
            System.out.println(selected.getName());
        }
    }

    @Override
    public void render(Batch batch) {
        if (!active)
            return;
        selectionBoxRenderer.render(batch);
    }

    public void start(){
        active = true;
        Array<Option> itemsListEntities = new Array<Option>();
        for (Item item : Player.items){
            itemsListEntities.add(new ItemsListEntity(item));
        }
        items = new SelectionBox(itemsListEntities);
        selectionBoxRenderer = new SelectionBoxRenderer(items, position);
    }
}

class ItemsListEntity extends Option {
    private Item item;

    public ItemsListEntity(Item item) {
        super(item.getName());
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}
