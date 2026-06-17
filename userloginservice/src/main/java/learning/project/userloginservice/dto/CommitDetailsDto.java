package learning.project.userloginservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitDetailsDto {
    private String message;
    private CommitterDto committer;
}
