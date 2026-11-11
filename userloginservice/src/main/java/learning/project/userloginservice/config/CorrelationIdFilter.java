package learning.project.userloginservice.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component 
public class CorrelationIdFilter extends OncePerRequestFilter{

    private static final Logger logger = LoggerFactory.getLogger(CorrelationIdFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        logger.info("Checking out co-relation id from the request");
        String corelationId = request.getHeader("X-REQUEST-ID");
        
        if(ObjectUtils.isEmpty(corelationId)){
            logger.info("Corelation id not found generating a new ID");

            corelationId = UUID.randomUUID().toString();
            logger.info("Generated random UUID: "+corelationId);
        }

        MDC.put("requestId", corelationId);

        try{
            response.setHeader("X-REQUEST-ID", corelationId);

            filterChain.doFilter(request,response);
        }finally{
            MDC.clear();
        }

        
        
    }
    
}
