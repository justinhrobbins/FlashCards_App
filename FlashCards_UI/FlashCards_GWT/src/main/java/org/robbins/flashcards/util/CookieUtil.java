
package org.robbins.flashcards.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class CookieUtil {

    private CookieUtil() {
    }

    public static final int dayInSeconds = 86400;

    public static Cookie findCookie(final HttpServletRequest request, final String name) {
        final Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }

    public static String getCookieValue(final HttpServletRequest request,
            final String name) {
        Cookie cookie = findCookie(request, name);

        return cookie != null ? cookie.getValue() : null;
    }

    public static void resetCookie(final HttpServletRequest request,
            final HttpServletResponse response, final String name) {
        Cookie cookie = findCookie(request, name);
        if (cookie != null) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    public static void setCookie(final HttpServletRequest request,
            final HttpServletResponse response, final String cookieName,
            final String cookieValue) {
        Cookie cookie = findCookie(request, cookieName);

        if (cookie == null) {
            cookie = new Cookie(cookieName, cookieValue);
        } else {
            cookie.setValue(cookieValue);
        }

        cookie.setMaxAge(dayInSeconds); // one day
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
