package com.pyxsasys.fc.social.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the FeedConfig entity.
 */
public class FeedConfigDTO implements Serializable {

    private String id;

    @NotNull
    private String feedClass;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedClass() {
        return feedClass;
    }

    public void setFeedClass(String feedClass) {
        this.feedClass = feedClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeedConfigDTO feedConfigDTO = (FeedConfigDTO) o;

        if ( ! Objects.equals(id, feedConfigDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FeedConfigDTO{" +
            "id=" + id +
            ", feedClass='" + feedClass + "'" +
            '}';
    }
}
