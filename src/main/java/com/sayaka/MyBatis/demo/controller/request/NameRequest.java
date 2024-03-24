package com.sayaka.MyBatis.demo.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NameRequest {
    @NotBlank(message = "空白を許可しません")
    @Size(max = 20, message = "20文字以内で入力してください")
    private String name;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
