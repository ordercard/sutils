package com.spring.sutils.db;

/**
 * @Auther :huiqiang
 * @Description :
 * @Date: Create in 下午3:59 2018/7/20 2018
 * @Modify:
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyBean {

private  final JdbcTemplate jdbcTemplate;

       @Autowired
        public MyBean(JdbcTemplate jdbcTemplate){
        this .jdbcTemplate = jdbcTemplate;
        }

        }
