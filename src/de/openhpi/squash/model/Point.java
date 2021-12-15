package de.openhpi.squash.model;

public class Point {
    public static final float EPSILON = 1E-4f;

    public float x;
    public float y;

    public Point(){
        set(0, 0);
    }
    
    public Point(float x, float y) {
        set(x, y);
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void copy(Point other) {
        this.x = other.x;
        this.y = other.y;
    }

    public void copyAndMove(Point other, float deltaX, float deltaY){
        this.x = other.x + deltaX;
        this.y = other.y + deltaY;
    }

    public void copyAndMove(Point other, Speed speed, float time){
        this.x = other.x + speed.x * time;
        this.y = other.y + speed.y * time;
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Point){
            Point other = (Point) o;
            return this.x == other.x && this.y == other.y;
        } else 
            return false;
    }

    /* https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
       returns true if AB and CD line segments intersect
    */
    public static boolean isIntersecting(Point pointA, Point pointB, Point pointC, Point pointD) {
        // Find the four orientations needed for general and special cases
        int o1 = getOrientation(pointA, pointB, pointC);
        int o2 = getOrientation(pointA, pointB, pointD);
        int o3 = getOrientation(pointC, pointD, pointA);
        int o4 = getOrientation(pointC, pointD, pointB);
     
        // General case
        if (o1 != o2 && o3 != o4)
            return true;
     
        // Special Cases
        // pointA, pointB and pointC are collinear and pointC lies on segment AB
        if (o1 == 0 && onSegment(pointA, pointC, pointB)) return true;
     
        // pointA, pointB and pointD are collinear and pointD lies on segment AB
        if (o2 == 0 && onSegment(pointA, pointD, pointB)) return true;
     
        // pointC, pointD and pointA are collinear and pointA lies on segment CD
        if (o3 == 0 && onSegment(pointC, pointA, pointD)) return true;
     
        // pointC, pointD and pointB are collinear and pointB lies on segment CD
        if (o4 == 0 && onSegment(pointC, pointB, pointD)) return true;
     
        return false; // Doesn't fall in any of the above cases
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    //  0 --> p, q and r are collinear
    //  1 --> Clockwise
    // -1 --> Counterclockwise
    private static int getOrientation(Point p, Point q, Point r) {
        // formula:  https://www.geeksforgeeks.org/orientation-3-ordered-points/
        float val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
    
        if (val > Point.EPSILON) return 1;        // clock wise
        else if (val < -Point.EPSILON) return -1; // counterclock wise
        return 0;                                // collinear
    }

    // Given three collinear points p, q, r, 
    // the function checks if point q lies on line segment 'pr'
    private static boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= Math.max(p.x, r.x) && 
            q.x >= Math.min(p.x, r.x) &&
            q.y <= Math.max(p.y, r.y) && 
            q.y >= Math.min(p.y, r.y))
            return true;
        return false;
    }
}
