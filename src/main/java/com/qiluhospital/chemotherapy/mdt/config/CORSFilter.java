//package com.qiluhospital.chemotherapy.mdt.config;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
///**
// * 解决ajax跨域问题
// * @author yaoshuo 1234
// * @version v0.1.0
// */
//@Component
//public class CORSFilter implements Filter {
//    public void destroy() {
//    }
//
//    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
//        HttpServletResponse response = (HttpServletResponse) resp;
//        response.setHeader("Access-Control-Allow-Origin","*");
//        response.setHeader("Access-Control-Allow-Method","POST,GET,OPTIONS,DELETE");
//        response.setHeader("Access-Control-Max-Age","3600");
//        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        chain.doFilter(req,resp);
//    }
//
//    public void init(FilterConfig config) {
//    }
//
//}
