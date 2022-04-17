package com.vivacon.dto.request;

import com.vivacon.common.validation.EmailExistsConstraint;
import com.vivacon.common.validation.FieldMatching;
import com.vivacon.common.validation.Password;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@FieldMatching.List({
        @FieldMatching(firstField = "Password", secondField = "MatchingPassword", message = "The matching password is not correct !")
})
public class RegistrationRequest {

    @NotEmpty
    @Email
    @EmailExistsConstraint
    private String email;

    @NotEmpty
    @Size(min = 1, max = 50)
    private String fullName;

    @NotEmpty
    @Password
    private String password;

    @NotEmpty
    private String matchingPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
