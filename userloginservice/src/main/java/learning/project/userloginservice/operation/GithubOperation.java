package learning.project.userloginservice.operation;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import jakarta.servlet.http.HttpServletRequest;
import learning.project.userloginservice.dto.CommitDto;
import learning.project.userloginservice.dto.GithubRepoDto;
import learning.project.userloginservice.service.GithubService;
import learning.project.userloginservice.util.DevSyncCommonCode;

@Service
public class GithubOperation {

    @Autowired
    private GithubService githubService;

    @Autowired
    private DevSyncCommonCode devSyncCommonCode;

    private static final Logger logger = LoggerFactory.getLogger(GithubOperation.class);


    public ResponseEntity<?> getUserInfoFromGithub(HttpServletRequest request){
        String accessToken = devSyncCommonCode.getGithubUserName(request).get("accessToken");
        String userInfo = githubService.getUserInfoFromGithub(accessToken);
        logger.info("Accessing loggedin User data");
        return ResponseEntity.ok(userInfo);
    }

    public List<GithubRepoDto> getUserRepositoryData(HttpServletRequest request){
        String accessToken = devSyncCommonCode.getGithubUserName(request).get("accessToken");
        logger.info("Extracting user repo data");
        List<GithubRepoDto> userRepoData = githubService.getUserRepoData(accessToken);
        return userRepoData;  
    }

    public List<CommitDto> getRepoCommitDetails(HttpServletRequest request,String repoName){
        Map<String,String> userDetails = devSyncCommonCode.getGithubUserName(request);
        String accessToken = userDetails.get("accessToken");
        String githubUserName = userDetails.get("githubUserName");
        logger.info("Extracting commit details");
        List<CommitDto> commitDataOnRepo = githubService.getUserCommitData(githubUserName, repoName, accessToken);
        return commitDataOnRepo;
    }

    public List<CommitDto> getcommitData(HttpServletRequest request,String repoName,List<String> shaValue){
        List<CommitDto> commitFileChanges = new ArrayList<>();
        Map<String,String> userDetails = devSyncCommonCode.getGithubUserName(request);
        String accessToken = userDetails.get("accessToken");
        String githubUserName = userDetails.get("githubUserName");
        logger.info("Extracting specific commit details");
        if(!ObjectUtils.isEmpty(shaValue)) {
            for(String sha: shaValue){
                githubService.getSpecificUserCommitData(githubUserName, repoName, accessToken, sha,commitFileChanges);
            }
        }
        return commitFileChanges;
    }

    public Map<String,Long> getRepoLanguageDensity(String repoName,HttpServletRequest request){
        Map<String,String> userDetails = devSyncCommonCode.getGithubUserName(request);
        String accessToken = userDetails.get("accessToken");
        String githubUserName = userDetails.get("githubUserName");
        logger.info("Extracting a repo specific language density");
        return githubService.getRepositoryLanguageDensity(githubUserName, repoName, accessToken);
    }
}
