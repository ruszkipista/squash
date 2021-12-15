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
        for (int i=0; i<this.corners.length; i++)
            this.newCorners[i].copyAndMove(this.corners[i], 
                                           this.distancePerSecond, 
                                           lapsedTimeInSecond);
    }

    public boolean finalizeMove() {
        this.modelChanged = false;
        for (int i=0; i<this.corners.length; i++){
            if (!this.corners[i].equals(this.newCorners[i]))
                this.modelChanged = true;
            // overwrite corner with newCorner
            this.corners[i].copy(this.newCorners[i]);
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
            // top or bottom
			if (maxIntersectCountIndex == 0 || maxIntersectCountIndex == 2) {
                this.distancePerSecond.negateY();
			} else {
                // right or left
  				this.distancePerSecond.negateX();
			}
            for (int i=0; i<this.corners.length; i++)
                this.newCorners[i].copy(this.corners[i]);            
			this.justCollided = true;
            other.justCollided = true;
		}
	}

}
