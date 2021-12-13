package de.openhpi.squash.model;

public abstract class IMovableRectangle extends IPositionableRectangle {
    protected boolean modelChanged;
    private Speed distancePerSecond = new Speed();
    private Point newTopLeft     = new Point();
    private Point newTopRight    = new Point();
    private Point newBottomRight = new Point();
    private Point newBottomLeft  = new Point();
    // clockwise arrangement of new corners    
    private Point[] newCorners = {newTopLeft,newTopRight,newBottomRight,newBottomLeft};

    public IMovableRectangle(float width,  float height,
                             float posX,   float posY,
                             float speedX, float speedY){
        super(width, height, posX, posY);
        this.distancePerSecond.set(speedX, speedY);
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

    public boolean finalizeMove() {
        this.modelChanged = ! super.topLeft.equals(this.newTopLeft);
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

    public void checkCollison(IMovableRectangle other) {
        checkCollison(other.getPositionableRectangle());
    }

    public void checkCollison(IPositionableRectangle other) {
        int maxIntersectCount = 0;
        int maxIntersectCountIndex = -1;
        int[] sideIntersectCounts = new int[other.sides.length];

        this.distancePerSecond = this.getDistancePerSecond();
        if (this.distancePerSecond.x==0 && this.distancePerSecond.y==0){
            return;
        }

		for (int i=0; i<other.sides.length;i++) {
			sideIntersectCounts[i] = 0;
			for (int j=0; j<super.corners.length;j++)
				sideIntersectCounts[i] += 
                    other.sides[i].isIntersectingWith(super.corners[j], this.newCorners[j]) ? 1 : 0;
			if (maxIntersectCount < sideIntersectCounts[i]){
				maxIntersectCount = sideIntersectCounts[i];
				maxIntersectCountIndex = i;
			}
		}
		if (maxIntersectCount > 0){
            // top or bottom
			if (maxIntersectCountIndex == 0 || maxIntersectCountIndex == 2) {
				if (this.distancePerSecond.y>0)
                    this.newTopLeft.y = other.sides[maxIntersectCountIndex].pointA.y-this.height-Point.EPSILON;
				else if (this.distancePerSecond.y<0)
                    this.newTopLeft.y = other.sides[maxIntersectCountIndex].pointA.y+Point.EPSILON;
				this.distancePerSecond.negateY();
			} else {
                // right or left
				if (this.distancePerSecond.x>0)
                    this.newTopLeft.x = other.sides[maxIntersectCountIndex].pointA.x-(this.width+Point.EPSILON);
				else if (this.distancePerSecond.x<0)
                    this.newTopLeft.x = other.sides[maxIntersectCountIndex].pointA.x+Point.EPSILON;
				this.distancePerSecond.negateX();
			}
            calculateNewCorners();
			this.justCollided = true;
            other.justCollided = true;
		}
	}

}
