package com.pyxsasys.fc.social.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FeedConfig.
 */

@Document(collection = "feed_config")
public class FeedConfig implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("feed_class")
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

        FeedConfig feedConfig = (FeedConfig) o;

        if ( ! Objects.equals(id, feedConfig.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FeedConfig{" +
            "id=" + id +
            ", feedClass='" + feedClass + "'" +
            '}';
    }
}
