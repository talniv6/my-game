package com.talniv.game.ui;


import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.talniv.game.Game;
import com.talniv.game.GameObjects.Item;
import com.talniv.game.GameObjects.Player;

public class ItemsRenderer extends UiElement{
    private com.talniv.game.utils.CyclicArray<Option> items;
    private Rectangle position = new Rectangle(Game.V_WIDTH/3, Game.V_HEIGHT/3, Game.V_WIDTH/3, Game.V_HEIGHT/3);
    private SelectionBoxRenderer selectionBoxRenderer;

    public ItemsRenderer() {
        items = new com.talniv.game.utils.CyclicArray<Option>();
        selectionBoxRenderer = new SelectionBoxRenderer(position);
    }

    @Override
    public void tick(float dt) {
        if (!active)
            return;
        selectionBoxRenderer.tick();
        if (selectionBoxRenderer.isChoiceMade()){
            selectionBoxRenderer.finish();
            active = false;

            Item selected = null;
            if (items.size() > 0)
                selected = ((ItemsListEntity)items.get(items.getCurrentIndex())).getItem();
            if (selected != null)
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
        items.clear();
        for (Item item : Player.items){
            items.add(new ItemsListEntity(item));
        }
        items.selectFirst();
        selectionBoxRenderer.setOptions(items);
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
