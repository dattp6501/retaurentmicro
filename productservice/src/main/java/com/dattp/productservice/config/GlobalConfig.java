package com.dattp.productservice.config;

import org.springframework.context.annotation.Configuration;
@Configuration
public class GlobalConfig {
    // @Bean
	// public RestTemplate getRestTemplate(){
	// 	return new RestTemplate();
	// }
	// @Value("${com.dattp.globalconfig.state.default}")
	public static final int DEFAULT_STATE = -1;// chua co trang thai
    public static final int NOT_FOUND_STATE = 0;//entity khong ton tai
    public static final int OK_STATE = 1;// entity da san sang
    public static final int CANCEL_STATE = 2;// da khoa entity


	public static final String[] pathPublic= {
        "/api/product/user/table/get_table",
		"/api/product/user/dish/get_dish"
    };
}