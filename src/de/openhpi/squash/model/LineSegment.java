package de.openhpi.squash.model;

public class LineSegment {
    private static final float EPSILON = 1E-5f;
    public Point pointA;
    public Point pointB;

    public LineSegment(Point pointA, Point pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    /* https://www.geeksforgeeks.org/check-if-two-given-line-segments-intersect/
       returns true if 'this' and 'other' line segments intersect
    */
    public boolean isIntersectingWith(LineSegment other1, LineSegment other2){
        return this.isIntersectingWith(other1)
            || this.isIntersectingWith(other2);
    }

    public boolean isIntersectingWith(LineSegment other) {
        return isIntersecting(this.pointA, this.pointB, other.pointA, other.pointB);
    }

    public boolean isIntersectingWith(Point pointC, Point pointD) {
        return isIntersecting(this.pointA, this.pointB, pointC, pointD);
    }

    public static boolean isIntersecting(Point pointA, Point pointB, Point pointC, Point pointD) {
        // Find the four orientations needed for general and
        // special cases
        int o1 = getOrientation(pointA, pointB, pointC);
        int o2 = getOrientation(pointA, pointB, pointD);
        int o3 = getOrientation(pointC, pointD, pointA);
        int o4 = getOrientation(pointC, pointD, pointB);
     
        // General case
        if (o1 != o2 && o3 != o4)
            return true;
     
        // Special Cases
        // pointA, pointB and pointC are collinear and pointC lies on segment "this"
        if (o1 == 0 && onSegment(pointA, pointC, pointB)) return true;
     
        // pointA, pointB and pointD are collinear and pointD lies on segment "this"
        if (o2 == 0 && onSegment(pointA, pointD, pointB)) return true;
     
        // pointC, pointD and pointA are collinear and pointA lies on segment "other"
        if (o3 == 0 && onSegment(pointC, pointA, pointD)) return true;
     
        // pointC, pointD and pointB are collinear and pointB lies on segment "other"
        if (o4 == 0 && onSegment(pointC, pointB, pointD)) return true;
     
        return false; // Doesn't fall in any of the above cases
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    private static int getOrientation(Point p, Point q, Point r) {
        // formula:  https://www.geeksforgeeks.org/orientation-3-ordered-points/
        float val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
    
        if (val > EPSILON) return 1;      // clock wise
        else if (val < -EPSILON) return 2; // counterclock wise
        else return 0;              // collinear

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
