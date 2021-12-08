package com.amr.project.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Veilas on 11/8/2021.
 * Class: ExceptionInfo.java
 */

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Scope("prototype")
public class ExceptionInfo {
    private String info;

    @NotNull
    public static ResponseEntity<Map<String, Object>> getExceptionUserAlreadyLeftReview() {
        Map<String, Object> body = new LinkedHashMap<>();
        List<String> errors = new ArrayList<>();
        body.put("errors",errors);
        errors.add("Вы уже оставляли отзыв на этот товар");
        return ResponseEntity.badRequest().body(body);
    }
}
