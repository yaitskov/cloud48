package org.cc.response;

/**
 * Daneel Yaitskov
 */
public class InvalidField {

    private String name;
    private String error;

    public InvalidField(String name, String error) {
        this.name = name;
        this.error = error;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
