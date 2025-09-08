package CareerLens.demo.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResourceNotFoundException  extends  RuntimeException{
    String fieldName;
    String field;
    Long fieldId;
    String resourceName;

    public ResourceNotFoundException(String fieldName, String field, String resourceName) {
        this.fieldName = fieldName;
        this.field = field;
        this.resourceName = resourceName;
    }


    public ResourceNotFoundException(String user, String userId, Long id) {
        this.fieldName =user;
        this.field = userId;
        this.fieldId = id;

    }
}
