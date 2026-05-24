package learning.project.userloginservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import learning.project.userloginservice.model.OAuthProvider;

public interface OAuthProviderRepository extends JpaRepository<OAuthProvider,String>{
    @Query("SELECT p FROM OAuthProvider p WHERE p.providerId = ?1 AND p.oAuthProvider = ?2 AND p.tokenStatus = ?3")
    OAuthProvider findByProviderIdAndOAuthProviderAndTokenStatus(String id,String providerName,String tokenStatus);

    @Query("select p.accessToken from OAuthProvider p WHERE p.oAuthUser.id = ?1 AND p.oAuthProvider = ?2 AND p.tokenStatus = ?3")
    String findAccessTokenByOAuthUserIdAndOAuthProviderAndTokenStatus(String id,String providerName,String tokenStatus);
}
