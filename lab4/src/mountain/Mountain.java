package mountain;

import fractal.Fractal;
import fractal.TurtleGraphics;

import java.util.HashMap;
import java.util.Map;

public class Mountain extends Fractal {
    private Point a, b, c;
    private int dev;
    Map<Side, Point> map = new HashMap();

    public Mountain(Point a, Point b, Point c, int dev) {
        super();
        this.a = a;
        this.b = b;
        this.c = c;
        this.dev = dev;
    }

    @Override
    public String getTitle() {
        return "Mountain";
    }

    @Override
    public void draw(TurtleGraphics turtle) {
        map.clear();
        turtle.moveTo(a.getX(), a.getY());
        fractalTriangle(turtle, order, dev, a, b, c);
    }

    private void fractalTriangle(TurtleGraphics turtle, int order, int dev, Point a, Point b, Point c) {
        if (order == 0) {
            turtle.moveTo(a.getX(), a.getY());
            turtle.forwardTo(b.getX(), b.getY());
            turtle.forwardTo(c.getX(), c.getY());
            turtle.forwardTo(a.getX(), a.getY());
        }
        else {
            Side ab = new Side(a, b);
            Side bc = new Side(b, c);
            Side ac = new Side(a, c);

            Point abm = map.get(ab);
            if (abm == null) {
                abm = ab.getMiddle(dev);
                map.put(ab, abm);
            }
            else {
                map.remove(ab);
            }
            Point bcm = map.get(bc);
            if (bcm == null) {
                bcm = bc.getMiddle(dev);
                map.put(bc, bcm);
            }
            else {
                map.remove(bc);
            }
            Point acm = map.get(ac);
            if (acm == null) {
                acm = ac.getMiddle(dev);
                map.put(ac, acm);
            }
            else {
                map.remove(ac);
            }

            fractalTriangle(turtle, order - 1, dev/2, a, abm, acm);
            fractalTriangle(turtle, order - 1, dev/2, abm, b, bcm);
            fractalTriangle(turtle, order - 1, dev/2, acm, bcm, c);
            fractalTriangle(turtle, order - 1, dev/2, abm, bcm, acm);
        }
    }
}
