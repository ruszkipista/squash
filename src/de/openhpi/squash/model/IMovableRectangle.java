package de.openhpi.squash.model;

public abstract class IMovableRectangle extends IPositionableRectangle {
    public boolean justBounced = true;
    public boolean newJustBounced = false;
    protected boolean modelChanged;
    private Speed distancePerSecond = new Speed(0,0);
    private Point newTopLeft     = new Point(0,0);
    private Point newTopRight    = new Point(0,0);
    private Point newBottomRight = new Point(0,0);
    private Point newBottomLeft  = new Point(0,0);
    private Point newPosition    = newTopLeft;
    public Point newCenter       = new Point(0,0);
    public Point[] newCorners    = new Point[5];

    public IMovableRectangle(float width,  float height,
                             float posX,   float posY,
                             float speedX, float speedY){
        super(width, height, posX, posY);
        this.distancePerSecond.set(speedX, speedY);
        // clockwise arrangement of new corners
        newCorners[0] = newTopLeft;
        newCorners[1] = newTopRight;
        newCorners[2] = newBottomRight;
        newCorners[3] = newBottomLeft;
    }
    
    public void prepareMove(float lapsedTimeInSecond){
        // calculate new corners
        this.newTopLeft.copyAndMove(super.topLeft, this.distancePerSecond, lapsedTimeInSecond);
        calculateNewCorners();
    }

    public void calculateNewCorners(){
        this.newTopRight.copyAndMove(this.newTopLeft, +super.width, 0);
        this.newBottomRight.copyAndMove(this.newTopLeft, +super.width, +super.height);
        this.newBottomLeft.copyAndMove(this.newTopLeft, 0, +super.height);
    }

    protected void setNewCenter(){
        newCenter.copyAndMove(this.newTopLeft, +super.width/2.0f, +super.height/2.0f);
    }

    public boolean finalizeMove() {
        this.modelChanged = ! super.position.equals(this.newPosition);
        this.justBounced = this.newJustBounced;
        this.newJustBounced = false;
        // overwrite corners with newCorners
        if (this.modelChanged){
            super.topLeft.copy(this.newTopLeft);
            super.topRight.copy(this.newTopRight);
            super.bottomLeft.copy(this.newBottomLeft);
            super.bottomRight.copy(this.newBottomRight);
        }
        return this.modelChanged;
    }

    public Speed getDistancePerSecond(){
        return this.distancePerSecond;
    }

    public void setDistancePerSecond(float x, float y){
        this.distancePerSecond.set(x,y);
    }

    public void changeDistancePerSecond(float x, float y){
        this.distancePerSecond.change(x,y);
    }

    private int[] sideIntersectCounts = new int[4]; // we deal with rectangles only
    private int maxIntersectCount;
    private int maxIntersectCountIndex;
    private int i;
    private int j;

    public void checCollisonVsFixed(IPositionableRectangle other) {
        if (this.justBounced){
            this.newJustBounced = false;
			return;
		}
        this.distancePerSecond = this.getDistancePerSecond();
        if (this.distancePerSecond.x==0 && this.distancePerSecond.y==0){
            return;
        }
        maxIntersectCount = 0;
        maxIntersectCountIndex = -1;
		for (i=0; i<other.sides.length;i++) {
			sideIntersectCounts[i] = 0;
			for (j=0; j<this.corners.length;j++)
				sideIntersectCounts[i] += other.sides[i]
                                         .isIntersectingWith(super.corners[j], this.newCorners[j])
                                         ? 1 : 0;
			if (maxIntersectCount < sideIntersectCounts[i]){
				maxIntersectCount = sideIntersectCounts[i];
				maxIntersectCountIndex = i;
			}
		}
		if (maxIntersectCount > 0){
            // top or bottom
			if (maxIntersectCountIndex == 0 || maxIntersectCountIndex == 2) {
				if (this.distancePerSecond.y>0)
                    this.newPosition.y = other.sides[maxIntersectCountIndex].pointA.y-this.height;
				else if (this.distancePerSecond.y<0)
                    this.newPosition.y = other.sides[maxIntersectCountIndex].pointA.y;
				this.distancePerSecond.negateY();
			} else {
                // right or left
				if (this.distancePerSecond.x>0)
                    this.newPosition.x = other.sides[maxIntersectCountIndex].pointA.x-this.width;
				else if (this.distancePerSecond.x<0)
                    this.newPosition.x = other.sides[maxIntersectCountIndex].pointA.x;
				this.distancePerSecond.negateX();
			}
			this.newJustBounced = true;
            calculateNewCorners();
		}
	}
}
