package com.github.rxyor.carp.delayjob.samples;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *<p>
 *
 *</p>
 *
 * @author liuyang
 * @date 2019/10/6 周日 13:44:00
 * @since 1.0.0
 */
@SpringBootTest(classes = {StartupApplication.class})
@ExtendWith(SpringExtension.class)
public class SpringWithJUnit5IT extends JUnit5IT {

    protected static Logger log = LoggerFactory.getLogger(SpringWithJUnit5IT.class);

}
