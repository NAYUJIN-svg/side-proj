//package com.nyg.sideproj.controller;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * 정적 리소스 처리 컨트롤러
// */
//@RestController
//public class StaticResourceController {
//
//    @GetMapping("/.well-known/appspecific/com.chrome.devtools.json")
//    public ResponseEntity<String> chromeDevTools() {
//        return ResponseEntity.ok("{}");
//    }
//}