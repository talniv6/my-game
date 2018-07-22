package com.talniv.game.map;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.talniv.game.UnsupportedShapeTypeException;

public class Box {
    public enum ShapeType{
        RECTANGLE, POLYGON, ELLIPSE
    }

    private Shape2D shape;
    private ShapeType type;

    public Box(Shape2D shape) throws UnsupportedShapeTypeException {
        this.shape = shape;
        if(shape instanceof Rectangle){
            type = ShapeType.RECTANGLE;
        }
        else if (shape instanceof Polygon){
            type = ShapeType.POLYGON;
        }
        else if (shape instanceof Ellipse){
            type = ShapeType.ELLIPSE;
        }
        else{
            throw new UnsupportedShapeTypeException();
        }
    }

    private void createTexture(){
        Pixmap pixmap;
        switch (type){
            case RECTANGLE:
                Rectangle rect = (Rectangle)shape;
                pixmap = new Pixmap((int)rect.width, (int)rect.height, Pixmap.Format.Alpha);
                pixmap.drawRectangle((int)rect.x, (int)rect.y, (int)rect.width, (int)rect.height);
                break;
            case POLYGON:

        }
    }

    private boolean polygonAndRectangle(Polygon polygon, Rectangle rectangle){
        Polygon rPoly = new Polygon(new float[] { 0, 0, rectangle.width, 0, rectangle.width,
                rectangle.height, 0, rectangle.height });
        rPoly.setPosition(rectangle.x, rectangle.y);
        return Intersector.overlapConvexPolygons(rPoly, polygon);
    }

    private boolean polygonAndPolygon(Polygon polygon1, Polygon polygon2){
        return Intersector.overlapConvexPolygons(polygon1, polygon2);
    }

    private boolean rectangleAndRectangle(Rectangle rec1, Rectangle rect2){
        return Intersector.overlaps(rec1, rect2);
    }

    public boolean intersect(Box box){
        ShapeType boxType = box.getType();
        switch (type){
            case RECTANGLE:
                switch (boxType){
                    case RECTANGLE:
                        return rectangleAndRectangle((Rectangle) shape, (Rectangle)box.getShape());
                    case POLYGON:
                        return polygonAndRectangle((Polygon)box.getShape(), (Rectangle)shape);
                    default:
                        return false;
                }
            case POLYGON:
                switch (boxType){
                    case RECTANGLE:
                        return polygonAndRectangle((Polygon) shape, (Rectangle)box.getShape());
                    case POLYGON:
                        return polygonAndPolygon((Polygon)box.getShape(), (Polygon)shape);
                    default:
                        return false;
                }
            default:
                    return false;
        }
    }

    public ShapeType getType() {
        return type;
    }

    public Shape2D getShape() {
        return shape;
    }

    public Rectangle getBounds(){
        switch (type){
            case RECTANGLE:
                return (Rectangle)shape;
            case POLYGON:
                return ((Polygon)shape).getBoundingRectangle();
            case ELLIPSE:
                Ellipse ellipse = ((Ellipse)shape);
                return new Rectangle(ellipse.x, ellipse.y, ellipse.width, ellipse.height);
        }
        return null;
    }

    public void translate(float x, float y){
        switch (type){
            case RECTANGLE:
                ((Rectangle)shape).setPosition(x, y);
                break;
            case POLYGON:
                ((Polygon)shape).setPosition(x, y);
                break;
            case ELLIPSE:
                ((Ellipse)shape).setPosition(x, y);
                break;
        }
    }

    public void render(ShapeRenderer shapeRenderer){
        if (type == ShapeType.RECTANGLE) {
            shapeRenderer.rect(((Rectangle) shape).x, ((Rectangle) shape).y, ((Rectangle) shape).width, ((Rectangle) shape).height);
        }
    }
}
