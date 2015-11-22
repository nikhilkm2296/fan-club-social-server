package com.pyxsasys.fc.social.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the FixtureConfig entity.
 */
public class FixtureConfigDTO implements Serializable {

    private String id;

    @NotNull
    private String sourceClass;

    @NotNull
    private String sourceProcessorClass;

    @NotNull
    private Boolean active;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(String sourceClass) {
        this.sourceClass = sourceClass;
    }

    public String getSourceProcessorClass() {
        return sourceProcessorClass;
    }

    public void setSourceProcessorClass(String sourceProcessorClass) {
        this.sourceProcessorClass = sourceProcessorClass;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FixtureConfigDTO fixtureConfigDTO = (FixtureConfigDTO) o;

        if ( ! Objects.equals(id, fixtureConfigDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FixtureConfigDTO{" +
            "id=" + id +
            ", sourceClass='" + sourceClass + "'" +
            ", sourceProcessorClass='" + sourceProcessorClass + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
