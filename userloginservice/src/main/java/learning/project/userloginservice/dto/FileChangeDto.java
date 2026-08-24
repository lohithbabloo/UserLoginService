package learning.project.userloginservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileChangeDto {
    private String filename;
    private String status;
    private String blob_url;
    private int additions;
    private int deletions;
    private String patch;
}
