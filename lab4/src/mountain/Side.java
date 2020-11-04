package mountain;

public class Side {
    final private Point p1;
    final private Point p2;

    public Side(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public Point getMiddle(int dev) {
        int pX = (p1.getX() + p2.getX()) / 2;
        int pY = (p1.getY() + p2.getY()) / 2;
        pY += RandomUtilities.randFunc(dev);
        return new Point(pX, pY);
    }

    @Override
    public int hashCode() {
        return p1.hashCode() + p2.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }
}
