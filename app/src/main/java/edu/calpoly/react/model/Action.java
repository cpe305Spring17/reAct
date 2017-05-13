package edu.calpoly.react.model;

import java.security.InvalidParameterException;

import edu.calpoly.react.model.database.DBConnection;

/**
 * Created by Nishanth on 4/27/17.
 */

public class Action {
    private Long id;
    private String name;
    private Category category;

    /* CONSTRUCTORS */

    public Action() {
        // empty constructor for serialization
    }

    public Action(String name) {
        this(name, null);
    }

    public Action(String name, Category category) {
        setName(name);
        this.category = category;
    }

    /* GETTERS/SETTERS */

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidParameterException("Actions must have a name");
        }
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isValid() {
        return DBConnection.getInstance().getActivity(name) == null;
    }
}
