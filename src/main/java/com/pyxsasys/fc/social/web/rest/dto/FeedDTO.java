package com.pyxsasys.fc.social.web.rest.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * A DTO for the Feed entity.
 */
public class FeedDTO implements Serializable {

    private String id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String link;

    private String image;

    @NotNull
    private String source;

    @NotNull
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

        FeedDTO feedDTO = (FeedDTO) o;

        if (!Objects.equals(id, feedDTO.id))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FeedDTO{" + "id=" + id + ", title='" + title + "'" + ", description='" + description + "'" + ", link='"
                + link + "'" + ", image='" + image + "'" + ", source='" + source + "'" + ", publicationDate='"
                + publicationDate + "'" + '}';
    }
}
