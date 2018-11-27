package com.xhh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class JdbcDemoServiceImpl implements  JdbcDemoService{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void updateJdbc() {
        jdbcTemplate.update("INSERT INTO `user` (`age`, `email`, `name`) VALUES (?, ?, ?);",
                18, "1203502972@qq.com", "饶先龙");
    }
}
