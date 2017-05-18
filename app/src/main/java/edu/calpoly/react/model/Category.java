package edu.calpoly.react.model;

import edu.calpoly.react.model.database.DBConnection;

/**
 * Created by Nishanth on 4/27/17.
 */

public class Category implements Comparable<Category> {

    private Long id;
    private String name;

    /* CONSTRUCTORS */

    public Category() {
        // empty constructor for serialization
    }

    public Category(String name) {
        this.name = name;
    }

    /* GETTERS/SETTERS */

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new NullPointerException("Categories must have a name");
        }
        this.name = name;
    }

    public boolean isValid() {
        return DBConnection.getInstance().getCategory(name) == null;
    }

    public int compareTo(Category other) {
        return name.compareTo(other.getName());
    }
}
