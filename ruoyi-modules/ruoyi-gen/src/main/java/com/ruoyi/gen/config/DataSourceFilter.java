package com.ruoyi.gen.config;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.ruoyi.common.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class DataSourceFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(DataSourceFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String ds = request.getHeader("dataSource");
        if (StringUtils.isNotEmpty(ds)) {
            DynamicDataSourceContextHolder.push(ds);
            filterChain.doFilter(servletRequest, servletResponse);
            DynamicDataSourceContextHolder.clear();
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
