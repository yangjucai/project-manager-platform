package cn.edu.ecnu.projectmanager.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@WebFilter(urlPatterns = "/*")
@Order(-99999)
public class CorsFilter extends HttpFilter {


    /**
     *
     */
    private static final long serialVersionUID = 2386571986045107652L;
    private static final String OPTIONS_METHOD = "OPTIONS";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        String origin = req.getHeader(HttpHeaders.ORIGIN);

        if (!StringUtils.isEmpty(origin)) {
            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);

            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
            res.addHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "*");
            res.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");
            res.setHeader("Access-Control-Allow-Headers", "access-control-allow-origin, authority, content-type, version-info, X-Requested-With");
            if (OPTIONS_METHOD.equalsIgnoreCase(req.getMethod())) {
                res.setStatus(HttpServletResponse.SC_NO_CONTENT);
                res.setContentType(MediaType.TEXT_HTML_VALUE);
                res.setCharacterEncoding("utf-8");
                res.setContentLength(0);
                res.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1800");
                return;
            }
        }

        super.doFilter(req, res, chain);
    }
}
