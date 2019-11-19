package com.framework.admin.controller;

import com.framework.core.modules.sys.entity.Menu;
import com.framework.core.modules.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
/**
 * 页面跳转Controller
 *
 * @author sunhan
 * @since 2017年02月14日
 */
@Controller
public class WebMvcController extends WebMvcConfigurerAdapter {

    @Autowired
    private MenuService menuService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("admin/index.html");
        registry.addViewController("/login").setViewName("login");
    }
    @RequestMapping(value = "/admin/**/*.html", method = RequestMethod.GET)
    public String view(HttpServletRequest request,ModelMap modelMap,
                             @RequestParam(required = false) Map<String, Object> param) {
        String path = getUri(request);
        // 面包屑导航
        List<Menu> breadcrumbs = menuService.getMenuByUrl(path);
        if (path.contains("."))
            path = path.split("[.]")[0];
        modelMap.put("param", param);
        modelMap.put("breadcrumbs",breadcrumbs);
        return path;
    }

    public String getUri(HttpServletRequest request) {
        String path = request.getRequestURI();
        String content = request.getContextPath();
        if (path.startsWith(content)) {
            path = path.substring(content.length() + 1);
        }
        return path;
    }

}
