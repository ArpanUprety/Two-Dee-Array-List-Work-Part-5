public class Space {
    public LivingThing occupant;
    public Space(){
        occupant = null;
        cache = null;
    }

    public Space(Treasure treasure){
        occupant = null;
        cache = treasure;
    }

    public Treasure getCache() {
        return cache;
    }

    public void setCache(Treasure cache) {
        this.cache = cache;
    }

    public Treasure cache;
    public Space(LivingThing occupant){
        this.occupant = occupant;
    }
    public LivingThing getOccupant() {
        return occupant;
    }
    public void setOccupant(LivingThing occupant) {
        this.occupant = occupant;
    }
    public String getConsoleString(){
        return (occupant == null) ? "- " : occupant.getConsoleStr();
    }
    public String getConsoleString(boolean reveal){
        return occupant == null  ? (reveal ? (getCache() == null ? "- " : getCache().getConsoleStr()) : "- ") : getOccupant().getConsoleStr();
    }
}
