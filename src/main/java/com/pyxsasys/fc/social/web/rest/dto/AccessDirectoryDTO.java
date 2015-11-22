package com.pyxsasys.fc.social.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.pyxsasys.fc.social.domain.enumeration.FCSocialMemberTypeEnum;

/**
 * A DTO for the AccessDirectory entity.
 */
public class AccessDirectoryDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 100)
    private String email;

    @NotNull
    private String accessCode;

    @NotNull
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

        AccessDirectoryDTO accessDirectoryDTO = (AccessDirectoryDTO) o;

        if ( ! Objects.equals(id, accessDirectoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AccessDirectoryDTO{" +
            "id=" + id +
            ", email='" + email + "'" +
            ", accessCode='" + accessCode + "'" +
            ", memberType='" + memberType + "'" +
            '}';
    }
}
