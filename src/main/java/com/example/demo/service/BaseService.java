package com.example.demo.service;

import com.example.demo.component.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseService {

    @Autowired
    protected Messages messages;
}
