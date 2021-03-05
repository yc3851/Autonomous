
public class Moveable extends Item{
    public Moveable(int x, int y, char token, String name) {
        this.x = x;
        this.y = y;
        this.token = token;
        this.name = name;
    }
    
    public boolean bumped(int dx, int dy, Item[][] map) {
        
        if(!isValid(x+dx, y+dy, map)) {
            return false;
        }
        
        Item item = map[x+dx][y+dy];
        if(item instanceof Immovable) {
            return false;
        }
        
        boolean canMove = true;
        if(item instanceof Moveable) {
            canMove = ((Moveable) item).bumped(dx, dy, map);
        } else if (item instanceof Autonomous) {
            canMove = ((Autonomous) item).bumped(dx, dy, map);
        }
        if(canMove) {
            this.x += dx;
            this.y += dy;
            map[x][y] = this;
            return true;
        }
        return false;
    }
}
