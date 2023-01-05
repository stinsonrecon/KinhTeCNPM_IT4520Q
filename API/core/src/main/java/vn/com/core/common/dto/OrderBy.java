package vn.com.core.common.dto;

import javax.validation.constraints.NotEmpty;

public class OrderBy {

    @NotEmpty(message = "Property field must be not null")
    private String property;

    @NotEmpty(message = "Property direction must be not null")
    private String direction;

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
