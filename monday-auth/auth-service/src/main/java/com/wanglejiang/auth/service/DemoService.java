package com.wanglejiang.auth.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/demo")
@Api(tags = "DemoService")
public interface DemoService {

    @GetMapping("/echo/{echo}")
    String echo(@PathVariable("echo") String echo);

}
