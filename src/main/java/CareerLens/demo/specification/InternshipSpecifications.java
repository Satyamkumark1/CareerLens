package CareerLens.demo.specification;


import CareerLens.demo.model.Internship;
import org.springframework.data.jpa.domain.Specification;

public class InternshipSpecifications {

    public static Specification<Internship> isActive() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isActive"), true);
    }

    public static Specification<Internship> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(criteriaBuilder.lower(root.get("category")), category.toLowerCase());
    }

    public static Specification<Internship> hasLocation(String location) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }

    public static Specification<Internship> hasState(String state) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("state")), "%" + state.toLowerCase() + "%");
    }

    public static Specification<Internship> containsText(String text) {
        return (root, query, criteriaBuilder) -> {
            String searchText = "%" + text.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), searchText),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchText),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("organization")), searchText)
            );
        };
    }

    public static Specification<Internship> hasSkill(String skill) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isMember(skill, root.get("requiredSkills"));
    }
}