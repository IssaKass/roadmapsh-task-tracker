package com.issakass.task_tracker.mapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: abdallah-issakass
 */
public class JSONParser {

    public static String toJson(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        int count = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append("\"").append(escape(entry.getKey())).append("\":");
            sb.append("\"").append(escape(entry.getValue().toString())).append("\"");

            if (++count < map.size()) {
                sb.append(",");
            }
        }

        sb.append("}");
        return sb.toString();
    }

    public static String toJson(List<Map<String, Object>> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(toJson(list.get(i)));
            if (i < list.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static Map<String, Object> parseJsonObject(String json) {
        Map<String, Object> map = new LinkedHashMap<>();
        json = json.trim();
        json = json.substring(1, json.length() - 1);

        String[] pairs = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            String key = unescape(keyValue[0].trim().replaceAll("^\"|\"$", ""));
            String rawValue = keyValue[1].trim();

            Object value;
            if (rawValue.startsWith("\"")) {
                value = unescape(rawValue.replaceAll("^\"|\"$", ""));
            } else if (rawValue.equals("true") || rawValue.equals("false")) {
                value = Boolean.parseBoolean(rawValue);
            } else {
                try {
                    value = Integer.parseInt(rawValue);
                } catch (NumberFormatException e) {
                    value = rawValue; // fallback
                }
            }

            map.put(key, value);
        }

        return map;
    }

    public static List<Map<String, Object>> parseJsonArray(String json) {
        List<Map<String, Object>> list = new ArrayList<>();
        json = json.trim();
        if (!json.startsWith("[") || !json.endsWith("]")) {
            throw new IllegalArgumentException("Invalid JSON array");
        }

        json = json.substring(1, json.length() - 1).trim();
        List<String> objects = extractJsonObjects(json);
        for (String obj : objects) {
            list.add(parseJsonObject(obj));
        }

        return list;
    }

    // Escape string values for JSON
    private static String escape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    // Unescape JSON strings
    private static String unescape(String s) {
        return s.replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }

    private static List<String> extractJsonObjects(String json) {
        List<String> objects = new ArrayList<>();
        int braceCount = 0;
        boolean inQuotes = false;
        int start = 0;

        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            } else if (!inQuotes) {
                if (c == '{') {
                    if (braceCount == 0) start = i;
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        objects.add(json.substring(start, i + 1));
                    }
                }
            }
        }

        return objects;
    }
}
