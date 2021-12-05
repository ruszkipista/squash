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
    public boolean isIntersectingWith(LineSegment other) {
        // Find the four orientations needed for general and
        // special cases
        int o1 = getOrientation(this.pointA, this.pointB, other.pointA);
        int o2 = getOrientation(this.pointA, this.pointB, other.pointB);
        int o3 = getOrientation(other.pointA, other.pointB, this.pointA);
        int o4 = getOrientation(other.pointA, other.pointB, this.pointB);
     
        // General case
        if (o1 != o2 && o3 != o4)
            return true;
     
        // Special Cases
        // this.pointA, this.pointB and other.pointA are collinear and other.pointA lies on segment "this"
        if (o1 == 0 && onSegment(this.pointA, other.pointA, this.pointB)) return true;
     
        // this.pointA, this.pointB and other.pointB are collinear and other.pointB lies on segment "this"
        if (o2 == 0 && onSegment(this.pointA, other.pointB, this.pointB)) return true;
     
        // other.pointA, other.pointB and this.pointA are collinear and this.pointA lies on segment "other"
        if (o3 == 0 && onSegment(other.pointA, this.pointA, other.pointB)) return true;
     
        // other.pointA, other.pointB and this.pointB are collinear and this.pointB lies on segment "other"
        if (o4 == 0 && onSegment(other.pointA, this.pointB, other.pointB)) return true;
     
        return false; // Doesn't fall in any of the above cases
    }

    // To find orientation of ordered triplet (p, q, r).
    // The function returns following values
    // 0 --> p, q and r are collinear
    // 1 --> Clockwise
    // 2 --> Counterclockwise
    static int getOrientation(Point p, Point q, Point r) {
        // formula:  https://www.geeksforgeeks.org/orientation-3-ordered-points/
        float val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
    
        if (val > EPSILON) return 1;      // clock wise
        else if (val < -EPSILON) return 2; // counterclock wise
        else return 0;              // collinear

    }

    // Given three collinear points p, q, r, 
    // the function checks if point q lies on line segment 'pr'
    static boolean onSegment(Point p, Point q, Point r) {
        if (q.x <= Math.max(p.x, r.x) && 
            q.x >= Math.min(p.x, r.x) &&
            q.y <= Math.max(p.y, r.y) && 
            q.y >= Math.min(p.y, r.y))
            return true;
        return false;
    }
}
