package io.github.mylyed.shiropower.controller;

import io.github.mylyed.shiropower.cache.CustomSpringCacheManager;
import io.github.mylyed.shiropower.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lilei on 2018/8/14.
 */
@RequestMapping("ct")
@Controller
public class CacheTestController {


    @Autowired
    HomeService homeService;


//    @Autowired
////    @Lazy
//            CustomSpringCacheManager customSpringCacheManager;


    @GetMapping("/")
    @ResponseBody
    public String cacheTest(String str) {
        System.out.println("访问cache端点");
        return homeService.home(str);
    }


//    @GetMapping("/data")
//    @ResponseBody
//    public Object cacheData() {
//        return customSpringCacheManager.allcache;
//    }


}
