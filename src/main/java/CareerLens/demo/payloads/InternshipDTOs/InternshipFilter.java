package CareerLens.demo.payloads.InternshipDTOs;

import lombok.Data;

@Data
public class InternshipFilter {
    private String category;
    private String location;
    private String state;
    private String search;
    private String skill;

    public InternshipFilter(String category, String location, String state, String search, String skill) {
        this.category = category;
        this.location = location;
        this.state = state;
        this.search = search;
        this.skill = skill;
    }

    public InternshipFilter() {
        // Default constructor
    }

    public boolean hasFilters() {
        return category != null || location != null || state != null || search != null || skill != null;
    }
}