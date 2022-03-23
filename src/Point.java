public class Point {

    private int type;
    private Point next;
    private boolean moved;
    private int velocity = 1;

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getType() {
        return type;
    }

    public Point getNext() {
        return next;
    }

    public void setNext(Point next) {
        this.next = next;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public void changeVelocity(int value){
        velocity+=value;
    }

    public void move() {
        if(!moved && next.getType() == 0 && type == 1){
            type = 0;
            next.clicked();
            moved = true;
            next.setMoved(true);
        }
    }

    public void clicked() {
        type = 1;
    }

    public void clear() {
        type = 0;
    }
}