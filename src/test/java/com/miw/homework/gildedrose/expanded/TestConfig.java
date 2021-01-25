package com.miw.homework.gildedrose.expanded;

import com.miw.homework.gildedrose.expanded.security.SecurityTestConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan("com.miw.homework.gildedrose.expanded")
@EnableWebMvc
@Import(SecurityTestConfig.class)
public class TestConfig {}
