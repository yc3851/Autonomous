import java.util.Random;

public class Autonomous extends Item{
    private static final int[][] dirs = {{-1,0}, {0,1}, {1,0}, {0,-1}};
    
    private Random ran;
    
    public Autonomous(int x, int y, char token, String name, Random ran) {
        this.x = x;
        this.y = y;
        this.token = token;
        this.name = name;
        this.ran = ran;
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
    
    public void step(Item[][] map) {    
        int n = ran.nextInt(4);
        int dx = dirs[n][0];
        int dy = dirs[n][1];
        if(!isValid(x+dx, y+dy, map)) {
            return;
        }
        Item item = map[x+dx][y+dy];
        if(item instanceof Immovable) {
            return;
        }
        boolean canMove = true;
        if(item instanceof Moveable) {
            canMove = ((Moveable) item).bumped(dx, dy, map);
        } else if (item instanceof Autonomous) {
            canMove = ((Autonomous) item).bumped(dx, dy, map);
        }
        if(canMove) {
            map[x][y] = null;
            this.x = x+dx;
            this.y = y+dy;
            map[x][y] = this;
        }
    }
}
