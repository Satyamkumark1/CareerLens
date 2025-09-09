package CareerLens.demo.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(resourceName + " not found with " + fieldName + ": " + fieldValue);
	}
}
