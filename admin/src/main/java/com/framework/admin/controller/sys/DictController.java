package com.framework.admin.controller.sys;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.framework.admin.controller.GenericController;
import com.framework.core.modules.sys.entity.Dict;
import com.framework.core.modules.sys.service.DictService;
import com.framework.core.modules.sys.service.RoleService;
import com.framework.core.po.IdText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author SunHan
 * @since 2017-01-21
 */
@RestController
@RequestMapping("/sys/dict")
@PreAuthorize("hasAuthority('sys:dict')")
public class DictController extends GenericController<Dict,Integer> {


    @Autowired
    private DictService dictService;

    @Autowired
    private RoleService roleService;

    @Override
    protected DictService getService() {
        return dictService;
    }

    @GetMapping(value = "/type/{type}")
    public List<IdText<String>> typeList(@PathVariable String type) {
        Map<String, Object> param = new HashMap<>();
        param.put("type", type);
        return getKeyValues(getService().selectByMap(param));
    }

    /**
     * 字典表，通过autocomplete获取数据
     * @param type 字典表类型
     * @param text 匹配字段
     */
    @GetMapping(value = "/auto/{type}")
    public List<IdText<String>> typeAuto(@PathVariable String type, String text) {
        EntityWrapper<Dict> ew = new EntityWrapper<>();
        ew.and("type={0}", type).and("label like CONCAT('%', {0}, '%')", text);
        return getKeyValues(getService().selectList(ew));
    }


    @GetMapping(value = "/type/{type}/{value}")
    public String getLabel(@PathVariable String type, @PathVariable String value) {

        EntityWrapper<Dict> ew = new EntityWrapper<>();

        ew.and("type={0}", type).and("value={0}", value);

        Dict dict = getService().selectOne(ew);
        if (dict == null) {
            return "";
        }
        return dict.getLabel();

    }

    @GetMapping(value = "/other/{type}")
    public List<IdText> other(@PathVariable String type) {
        switch (type) {
            case "role":
                return roleService.getRoleKv();
        }
        return null;
    }

    private List<IdText<String>> getKeyValues(List<Dict> list) {
        List<IdText<String>> keyValues = new ArrayList<>();
        for (Dict dict : list) {
            IdText<String> kv = new IdText<>();
            kv.setId(dict.getValue().toString());
            kv.setText(dict.getLabel());
            keyValues.add(kv);
        }
        return keyValues;
    }


}
