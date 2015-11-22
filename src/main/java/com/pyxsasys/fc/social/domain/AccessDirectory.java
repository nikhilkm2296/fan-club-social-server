package com.pyxsasys.fc.social.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.pyxsasys.fc.social.domain.enumeration.FCSocialMemberTypeEnum;

/**
 * A AccessDirectory.
 */

@Document(collection = "access_directory")
public class AccessDirectory implements Serializable {

    @Id
    private String id;

    @NotNull
    @Size(max = 100)
    @Field("email")
    private String email;

    @NotNull
    @Field("access_code")
    private String accessCode;

    @NotNull
    @Field("member_type")
    private FCSocialMemberTypeEnum memberType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public FCSocialMemberTypeEnum getMemberType() {
        return memberType;
    }

    public void setMemberType(FCSocialMemberTypeEnum memberType) {
        this.memberType = memberType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccessDirectory accessDirectory = (AccessDirectory) o;

        if ( ! Objects.equals(id, accessDirectory.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AccessDirectory{" +
            "id=" + id +
            ", email='" + email + "'" +
            ", accessCode='" + accessCode + "'" +
            ", memberType='" + memberType + "'" +
            '}';
    }
}
