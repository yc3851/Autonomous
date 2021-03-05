
public abstract class Item {
    protected String name;
    
    protected char token;
    
    protected int x;
    
    protected int y;
    
    public char getToken() {
        return token;
    }
    
    public boolean isValid(int x, int y, Item[][] map) {
        if(x==map.length || y==map.length || x<0 || y<0) {
            return false;
        }
        return true;
    }
}
