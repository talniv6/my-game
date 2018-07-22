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

    private boolean polygonAndRectangle(Polygon polygon, Rectangle rectangle){
        float[] vers = polygon.getTransformedVertices();
        for (int i = 0; i < polygon.getVertices().length; i+=2) {
            if (rectangle.contains(vers[i], vers[i+1])){
                return true;
            }
        }
        return polygon.contains(rectangle.x, rectangle.y) ||
                polygon.contains(rectangle.x+rectangle.width, rectangle.y) ||
                polygon.contains(rectangle.x+rectangle.width, rectangle.y+rectangle.height) ||
                polygon.contains(rectangle.x, rectangle.y+rectangle.height);
        // return Intersector.overlapConvexPolygons(rPoly, polygon);
    }

    private boolean polygonAndPolygon(Polygon polygon1, Polygon polygon2){
        float[] vers = polygon1.getTransformedVertices();
        for (int i = 0; i < polygon1.getVertices().length; i+=2) {
            if (polygon2.contains(vers[i], vers[i+1])){
                return true;
            }
        }
        vers = polygon2.getTransformedVertices();
        for (int i = 0; i < polygon2.getVertices().length; i+=2) {
            if (polygon1.contains(vers[i], vers[i+1])){
                return true;
            }
        }
        return false;
        // return Intersector.overlapConvexPolygons(polygon1, polygon2);
    }

    private boolean rectangleAndRectangle(Rectangle rec1, Rectangle rect2){
        return Intersector.overlaps(rec1, rect2);
    }

    private boolean ellipseAndRectangle(Rectangle rec, Ellipse ellipse){
        Rectangle boundingBox = new Rectangle(ellipse.x, ellipse.y, ellipse.width, ellipse.height);
        if (!rectangleAndRectangle(boundingBox, rec)){
            return false;
        }
        float centerX = boundingBox.x + boundingBox.width/2;
        float centerY = boundingBox.y + boundingBox.height/2;
        // left edge on the left of the bounding origin and right edge on the right
        if (rec.x <= centerX && rec.x + rec.width >= centerX){
            return true;
        }
        // top edge above the bounding origin and bottom edge below
        if (rec.y <= centerY && rec.y + rec.height >= centerY){
            return true;
        }
        // else, all of rec intersect only one quarter of bounding box
        if (rec.x <= centerX){ // QTL or QBL
            if (rec.y <= centerY){   // QBL
                return isPointInEllipse(ellipse, rec.x+rec.width, rec.y+rec.height);
            }
            else {  // QTL
                return isPointInEllipse(ellipse, rec.x + rec.width, rec.y);
            }
        }
        else { // QTR or QBR
            if (rec.y <= centerY){  // QBR
                return isPointInEllipse(ellipse, rec.x, rec.y+rec.height);
            }
            else {  // QTR
                return isPointInEllipse(ellipse, rec.x, rec.y);
            }
        }
    }

    private boolean isPointInEllipse(Ellipse e, float px, float py){
        float x = px;
        float y = py;
        float h = e.x + e.width / 2;
        float k = e.y + e.height / 2;
        float rx = e.width / 2;
        float ry = e.height / 2;

        if( ((x-h)*(x-h)) / (rx*rx) + ((y-k)*(y-k)) / (ry*ry) <= 1){
            return true;
        }
        return false;
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
                    case ELLIPSE:
                        return ellipseAndRectangle((Rectangle)shape, (Ellipse)box.getShape());
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
            case ELLIPSE:
                switch (boxType){
                    case RECTANGLE:
                        return ellipseAndRectangle((Rectangle)box.getShape(), (Ellipse)shape);
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

    public float getX(){
        switch (type){
            case RECTANGLE:
                return ((Rectangle)shape).x;
            case POLYGON:
                return ((Polygon)shape).getX();
            case ELLIPSE:
                return ((Ellipse)shape).x;
        }
        return -1;
    }

    public float getY(){
        switch (type){
            case RECTANGLE:
                return ((Rectangle)shape).y;
            case POLYGON:
                return ((Polygon)shape).getY();
            case ELLIPSE:
                return ((Ellipse)shape).y;
        }
        return -1;
    }

}
