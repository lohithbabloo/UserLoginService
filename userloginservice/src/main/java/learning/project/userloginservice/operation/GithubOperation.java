package learning.project.userloginservice.operation;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    public ResponseEntity<?> getUserInfoFromGithub(HttpServletRequest request){
        String accessToken = devSyncCommonCode.getGithubUserName(request).get("accessToken");
        
        String userInfo = githubService.getUserInfoFromGithub(accessToken);
        return ResponseEntity.ok(userInfo);
    }

    public List<GithubRepoDto> getUserRepositoryData(HttpServletRequest request){
        String accessToken = devSyncCommonCode.getGithubUserName(request).get("accessToken");
        List<GithubRepoDto> userRepoData = githubService.getUserRepoData(accessToken);
        return userRepoData;  
    }

    public List<CommitDto> getRepoCommitDetails(HttpServletRequest request,String repoName){
        Map<String,String> userDetails = devSyncCommonCode.getGithubUserName(request);
        String accessToken = userDetails.get("accessToken");
        String githubUserName = userDetails.get("githubUserName");
        List<CommitDto> commitDataOnRepo = githubService.getUserCommitData(githubUserName, repoName, accessToken);
        return commitDataOnRepo;
    }

    public List<CommitDto> getcommitData(HttpServletRequest request,String repoName,List<String> shaValue){
        List<CommitDto> commitFileChanges = new ArrayList<>();
        Map<String,String> userDetails = devSyncCommonCode.getGithubUserName(request);
        String accessToken = userDetails.get("accessToken");
        String githubUserName = userDetails.get("githubUserName");
        if(!ObjectUtils.isEmpty(shaValue)) {
            for(String sha: shaValue){
                githubService.getSpecificUserCommitData(githubUserName, repoName, accessToken, sha,commitFileChanges);
            }
        }
        return commitFileChanges;
    }
}
