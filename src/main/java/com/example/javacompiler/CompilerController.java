package com.example.javacompiler;

import org.springframework.web.bind.annotation.*;

@RestController
public class CompilerController {

    @PostMapping("/run")
    public String runCode(@RequestBody CodeRequest request) {
        return JavaCompilerService.compileAndRun(
                request.getClassName(),
                request.getCode(),
                request.getInput()
        );
    }
}
