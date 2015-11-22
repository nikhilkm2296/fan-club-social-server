package com.pyxsasys.fc.social.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Feed.
 */

@Document(collection = "feed")
public class Feed implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("link")
    private String link;

    @Field("image")
    private String image;

    @NotNull
    @Field("source")
    private String source;

    @NotNull
    @Field("publication_date")
    private ZonedDateTime publicationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public ZonedDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(ZonedDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Feed feed = (Feed) o;

        if (!Objects.equals(id, feed.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Feed{" + "id=" + id + ", title='" + title + "'" + ", description='" + description + "'" + ", link='"
                + link + "'" + ", image='" + image + "'" + ", source='" + source + "'" + ", publicationDate='"
                + publicationDate + "'" + '}';
    }
}
