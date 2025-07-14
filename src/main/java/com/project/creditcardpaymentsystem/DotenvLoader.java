package com.project.creditcardpaymentsystem;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;

@Configuration
public class DotenvLoader {
    @PostConstruct
    public void init() {
        Dotenv.configure().ignoreIfMissing().load();
    }
}
