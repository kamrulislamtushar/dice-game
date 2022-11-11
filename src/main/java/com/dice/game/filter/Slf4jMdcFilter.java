package com.dice.game.filter;
import com.dice.game.utility.MdcIdUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Slf4jMdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            String token = MdcIdUtil.generateMdcId();
            MdcIdUtil.setMdcId(token);
            response.addHeader("X-Correlation-ID", token);
            chain.doFilter(request, response);
        } finally {
            MdcIdUtil.removeMdcId();
        }
    }
}
