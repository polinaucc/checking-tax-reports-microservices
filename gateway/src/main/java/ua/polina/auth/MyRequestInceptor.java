package ua.polina.auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.Setter;
import org.springframework.stereotype.Component;

//@Component
//@Setter
//class MyRequestInterceptor implements RequestInterceptor {
//
//
//    private String jwt;
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        requestTemplate.header("Authorization ", String.format("%s%s", "Bearer ", this.jwt));
//    }
//}