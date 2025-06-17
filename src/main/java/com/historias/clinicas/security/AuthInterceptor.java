package com.historias.clinicas.security;

import jakarta.servlet.http.*;
import org.springframework.web.servlet.HandlerInterceptor;

/** Interceptor que protege todas las rutas salvo /login, /logout y estáticos. */
public class AuthInterceptor implements HandlerInterceptor {

    private static final String[] PUBLIC = {
        "/login", "/logout", "/css/", "/js/", "/images/", "/h2-console"
    };

    private boolean isPublic(String path) {
        for (String p : PUBLIC) if (path.startsWith(p)) return true;
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res,
                             Object handler) throws Exception {
        String ctx  = req.getContextPath();
        String path = req.getRequestURI().substring(ctx.length());

        if (isPublic(path)) return true;

        if (req.getSession().getAttribute("USER") == null) {
            res.sendRedirect(ctx + "/login?timeout");
            return false;
        }
        return true;
    }
}
