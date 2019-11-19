package com.framework.admin.test;

import com.alibaba.fastjson.JSON;
import com.framework.core.modules.sys.entity.Menu;
import com.framework.core.modules.sys.service.MenuService;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @author:Sun.Han
 * @since:2017年04月05日
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    @Value("${spring.datasource.url}")
    private String dataSourceUrl;
    @Value("${spring.datasource.username}")
    private String datasourceUsername;
    @Value("${spring.datasource.password}")
    private String datasourcePassword;
    @Value("${spring.datasource.type}")
    private String datasourceType;
    @Value("${spring.datasource.driver-class-name}")
    private String datasourceDriverClassName;

    @Autowired
    private MenuService menuService;

    @Test
    public void test() {
       List<Menu> menu = menuService.getMenuByUrl("admin/sys/dict/list.html");

        System.out.println("-------------------->"+JSON.toJSONString(menu));
    }



    @Test
    public void test1() {

        Map<String, Object> beansWithAnnotationMap =
                this.applicationContext.getBeansWithAnnotation(PreAuthorize.class);


        Class<? extends Object> clazz = null;
        for(Map.Entry<String, Object> entry : beansWithAnnotationMap.entrySet()){
            clazz = entry.getValue().getClass();//获取到实例对象的class信息
            PreAuthorize preAuthorize = clazz.getAnnotation(PreAuthorize.class);
            String permission = preAuthorize.value()
                    .replace("hasAuthority('","")
                    .replace("')","");
            List<Menu> menuList = menuService.selectByMap(ImmutableMap.of("permission", permission));
            if (menuList.size() > 0){

            }else{

            }

        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public   void test2(){
        System.out.println(URLDecoder.decode("%E7%86%8A"));
    }



}
