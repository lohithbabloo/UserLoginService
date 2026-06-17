package learning.project.userloginservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitDto {
    private String sha;
    private CommitDetailsDto commit;

}
