package de.mayflower.peertopo.app.util;

/**
 * Created by martin on 27.04.14.
 */
public class RouteInfo {
    public String Index;
    public String Name;
    public String Difficulty;
    public String Description;

    public RouteInfo(String Index, String Name, String Difficulty) {
        this.Index = Index;
        this.Name = Name;
        this.Difficulty = Difficulty;
    }

    public String toString() {
        return Index + ": "+Name+" - "+Difficulty+"\n";
    }
    public boolean checkIntegrity() {
        return (Index != null) && (Name != null) && (Difficulty != null);
    }

    public RouteInfo() {

    }

    public void setName(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setDifficulty(String difficulty) {
        Difficulty = difficulty;
    }

    public String getDifficulty() {
        return Difficulty;
    }

    public String getIndex() {
        return Index;
    }

    public void setIndex(String index) {
        Index = index;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
