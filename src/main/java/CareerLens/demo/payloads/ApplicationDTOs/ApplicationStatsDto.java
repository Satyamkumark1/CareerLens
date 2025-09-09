package CareerLens.demo.payloads.ApplicationDTOs;
import lombok.Data;

@Data
public class ApplicationStatsDto {
    private Long totalApplications;
    private Long pendingCount;
    private Long underReviewCount;
    private Long shortlistedCount;
    private Long acceptedCount;
    private Long rejectedCount;
}