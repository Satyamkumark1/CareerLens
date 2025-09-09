package CareerLens.demo.payloads.ApplicationDTOs;

import CareerLens.demo.model.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateApplicationStatusRequest {

    @NotNull(message ="Status is requires" )
    private ApplicationStatus status;
}
