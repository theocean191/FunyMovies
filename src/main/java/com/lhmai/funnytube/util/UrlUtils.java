package com.lhmai.funnytube.util;


import org.springframework.util.StringUtils;

import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlUtils {
    public static Map<String, String> getQueryParams(String urlString) {
        try {
            URL url = new URL(urlString);
            String query = url.getQuery();
            if (StringUtils.isEmpty(query)) {
                return new HashMap<>();
            }

            Map<String, String> query_pairs = new LinkedHashMap<String, String>();
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                int idx = pair.indexOf("=");
                query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
            }
            return query_pairs;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

}
