package de.openhpi.squash.model;

public abstract class IMovableRectangle extends IPositionableRectangle {
    protected boolean modelChanged;
    private Speed distancePerSecond = new Speed();
    // clockwise arrangement of new corners    
    private Point[] newCorners = {new Point(),new Point(),new Point(),new Point()};

    public IMovableRectangle(float width,  float height,
                             float posX,   float posY,
                             float speedX, float speedY){
        super(width, height, posX, posY);
        this.distancePerSecond.set(speedX, speedY);
    }
    
    public void prepareMove(float lapsedTimeInSecond){
        // calculate new corners
        this.newCorners[0].copyAndMove(this.corners[0], this.distancePerSecond, lapsedTimeInSecond);
        setCorners(newCorners);
    }

    public boolean finalizeMove() {
        this.modelChanged = ! this.corners[0].equals(this.newCorners[0]);
        // overwrite corners with newCorners
        if (this.modelChanged){
            this.corners[0].copy(this.newCorners[0]);
            this.corners[1].copy(this.newCorners[1]);
            this.corners[2].copy(this.newCorners[2]);
            this.corners[3].copy(this.newCorners[3]);
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
        int[] sideIntersectCounts = new int[other.corners.length];

        if (this.distancePerSecond.x==0 && this.distancePerSecond.y==0){
            return;
        }

		for (int i=0; i<other.corners.length;i++) {
			sideIntersectCounts[i] = 0;
			for (int j=0; j<this.corners.length;j++)
				sideIntersectCounts[i] += 
                    Point.isIntersecting(other.corners[i], 
                                         other.corners[(i+1) % other.corners.length],
                                         this.corners[j], 
                                         this.newCorners[j])
                    ? 1 : 0;
            if (maxIntersectCount < sideIntersectCounts[i]){
				maxIntersectCount = sideIntersectCounts[i];
				maxIntersectCountIndex = i;
			}
		}
		if (maxIntersectCount > 0){
            this.newCorners[0].copy(this.corners[0]);
            setCorners(newCorners);
            // top or bottom
			if (maxIntersectCountIndex == 0 || maxIntersectCountIndex == 2) {
                this.distancePerSecond.negateY();
			} else {
                // right or left
  				this.distancePerSecond.negateX();
			}

			this.justCollided = true;
            other.justCollided = true;
		}
	}

}
