package learning.project.userloginservice.dto;


import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommitDto {
    private String sha;
    private CommitDetailsDto commit;
    //files data
    private List<FileChangeDto> files;
}
