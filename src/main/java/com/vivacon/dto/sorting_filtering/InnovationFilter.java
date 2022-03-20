package com.vivacon.dto.sorting_filtering;

import com.vivacon.common.enum_type.Status;

import java.util.List;
import java.util.Optional;

public class InnovationFilter {

    private Optional<List<Long>> types;

    private Optional<List<String>> projects;

    private Optional<List<Long>> areas;

    private Optional<List<Status>> statuses;

    private boolean own;

    private boolean active;

    public InnovationFilter(Optional<List<Long>> types,
                            Optional<List<String>> projects,
                            Optional<List<Long>> areas,
                            Optional<List<Status>> status,
                            boolean own,
                            boolean active) {
        this.types = types;
        this.projects = projects;
        this.areas = areas;
        this.active = active;
        this.statuses = status;
        this.own = own;
    }

    public boolean isOwn() {
        return own;
    }

    public Optional<List<Long>> getTypes() {
        return types;
    }

    public Optional<List<String>> getProjects() {
        return projects;
    }

    public Optional<List<Long>> getAreas() {
        return areas;
    }

    public Optional<List<Status>> getStatuses() {
        return statuses;
    }

    public boolean isActive() {
        return active;
    }
}
