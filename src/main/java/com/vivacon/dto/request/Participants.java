package com.vivacon.dto.request;

import java.util.Set;

public class Participants {

    private Set<String> usernames;

    public Set<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(Set<String> usernames) {
        this.usernames = usernames;
    }
}
