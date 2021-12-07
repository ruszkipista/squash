package de.openhpi.squash.model;

public class Collison {
    static int[] sideCollisionCounts = new int[4]; // we deal with rectangles only
    static int maxCount;
    static int maxCountIndex;
    static Speed movSpeed;
    static int i;
    static int j;

    public static void checkMovableVsFixed(IMovableRectangle movable, 
									        IPositionableRectangle fixed) {
        if (movable.justBounced){
            movable.newJustBounced = false;
			return;
		}
        movSpeed = movable.getDistancePerSecond();
        if (movSpeed.x==0 && movSpeed.y==0){
            return;
        }
        maxCount = 0;
        maxCountIndex = -1;
		for (i=0; i<fixed.sides.length;i++) {
			sideCollisionCounts[i] = 0;
			// start from 1, leave out "center" at [0]
			for (j=1; j<movable.corners.length;j++)
				sideCollisionCounts[i] += fixed.sides[i]
                                         .isIntersectingWith(movable.corners[j], movable.newCorners[j])
                                         ? 1 : 0;
			if (maxCount < sideCollisionCounts[i]){
				maxCount = sideCollisionCounts[i];
				maxCountIndex = i;
			}
		}
		if (maxCount > 0){
			if (maxCountIndex == 0 || maxCountIndex == 2) {
				if (movSpeed.y>0)
					movable.setNewPositionY(fixed.sides[maxCountIndex].pointA.y-movable.height);
				else if (movSpeed.y<0)
					movable.setNewPositionY(fixed.sides[maxCountIndex].pointA.y);
				movSpeed.negateY();
			} else {
				if (movSpeed.x>0)
					movable.setNewPositionX(fixed.sides[maxCountIndex].pointA.x-movable.width);
				else if (movSpeed.x<0)
					movable.setNewPositionX(fixed.sides[maxCountIndex].pointA.x);
				movSpeed.negateX();
			}
			movable.newJustBounced = true;
		}
	}
}
