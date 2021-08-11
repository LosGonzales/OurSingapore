package sg.edu.rp.c346.id20013676.myndpsongs;

import java.io.Serializable;

public class Island implements Serializable {

    private int _id, area, stars;
    private int count = 0;
    private String name, description;

    public Island(String name, String description, int area, int stars) {
        this.area = area;
        this.stars = stars;
        this.name = name;
        this.description = description;
        this._id = count++;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name + "\n" + description + " - " + area + "\n" + toStars();
    }

    public String toStars() {
        String filled =  new String(new char[stars]).replace("\0", "★");
        String empty = new String(new char[5-stars]).replace("\0", "☆");
        return filled + empty;

//        return stars + "★";
    }
}

