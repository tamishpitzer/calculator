package com.example.store;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

@Component
public class VariableStore {
    private final Map<String, Long> variables;

    public VariableStore() {
        this.variables = new HashMap<>();
    }

    public void set(String name, long value) {
        variables.put(name, value);
    }

    public Long get(String name) {
        return variables.get(name);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        TreeMap<String, Long> sorted = new TreeMap<>(variables);
        boolean first = true;
        for (Map.Entry<String, Long> entry : sorted.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }
}
