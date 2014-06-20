package nl.tudelft.bw4t.server.eis;


public class IdAndBoolean {
    
    private long id;
    private boolean bool;

    public IdAndBoolean(long id, boolean bool) {
        this.id = id;
        this.bool = bool;
    }

    public long getId() {
        return id;
    }
    
    public boolean getBool() {
        return bool;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = bool ? 3 : 2;
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = !bool ? 3 : 2;
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof IdAndBoolean)) {
            return false; 
        }
        IdAndBoolean other = (IdAndBoolean) obj;
        if (other.getBool() != getBool()) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        return true;
    }
}
