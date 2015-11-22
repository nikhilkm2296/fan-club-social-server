package com.pyxsasys.fc.social.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FixtureConfig.
 */

@Document(collection = "fixture_config")
public class FixtureConfig implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("source_class")
    private String sourceClass;

    @NotNull
    @Field("source_processor_class")
    private String sourceProcessorClass;

    @NotNull
    @Field("active")
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

        FixtureConfig fixtureConfig = (FixtureConfig) o;

        if ( ! Objects.equals(id, fixtureConfig.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FixtureConfig{" +
            "id=" + id +
            ", sourceClass='" + sourceClass + "'" +
            ", sourceProcessorClass='" + sourceProcessorClass + "'" +
            ", active='" + active + "'" +
            '}';
    }
}
