package com.framework.admin.controller.sys;

import com.framework.admin.controller.GenericController;
import com.framework.admin.security.model.AuthUser;
import com.framework.core.message.ResponseMessage;
import com.framework.core.modules.sys.entity.Menu;
import com.framework.core.modules.sys.service.MenuService;
import com.framework.core.po.JsTree;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

import static com.framework.core.message.ResponseMessage.created;
import static com.framework.core.message.ResponseMessage.ok;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author SunHan
 * @since 2017-01-21
 */
@RestController
@RequestMapping("sys/menu")
@PreAuthorize("hasAuthority('sys:menu')")
public class MenuController extends GenericController<Menu, Integer> {

    @Resource
    private MenuService menuService;

    @Override
    protected MenuService getService() {
        return menuService;
    }

    /**
     * 初始化菜单
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/nav")
    public List<Menu> getMenuNav() {
        AuthUser user = getCurrentUser();
        return menuService.getMenuNav(user.getId());
    }

    /**
     * Gets menu tree.
     *
     * @return the menu tree
     */
    @PreAuthorize("hasAuthority('view')")
    @GetMapping(value = "/tree")
    public List<Menu> getMenuTree() {
        AuthUser user = getCurrentUser();
        return menuService.getMenuTree(user.getId());
    }

    /**
     * Gets menu list.
     *
     * @return the menu list
     */
    @PreAuthorize("hasAuthority('view')")
    @GetMapping(value = "/list")
    public List<Menu> getMenuList() {
        AuthUser user = getCurrentUser();
        return menuService.getMenuList(user.getId());
    }

    /**
     * Gets menu list.
     *
     * @return the menu list
     */
    @PreAuthorize("hasAuthority('view')")
    @GetMapping(value = "/jstree")
    public List<JsTree> getJsTree(@RequestParam(value = "selectedId", required = false) Integer selectedId,
                                  @RequestParam(value = "disableId", required = false) Integer disableId) {
        AuthUser user = getCurrentUser();
        return menuService.getJsTree(user.getId(), selectedId, disableId);
    }

    /**
     * Gets menu list.
     *
     * @return the menu list
     */
    @PreAuthorize("hasAuthority('view')")
    @GetMapping(value = "/jstree/{roleId}")
    public List<JsTree> getJsTreeRoleId(@PathVariable(required = false) Integer roleId, @RequestParam(value = "selectedId", required = false) Integer selectedId,
                                        @RequestParam(value = "disableId", required = false) Integer disableId) {
        AuthUser user = getCurrentUser();
        return menuService.getJsTree(user.getId(), selectedId, disableId, roleId);
    }

    /**
     * 查询列表,并返回查询结果
     */
    @GetMapping(value = "treeGrid")
    public List<Menu> treeGrid(Menu menu) {
        return menuService.getMenuList(menu);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage add(Menu po) {
        // 初始化默认数据
        if (po.getParentId() == null) {
            po.setParentId(0);
            po.setParentIds(",0,");
            getService().insert(po);
            return created(po);
        }



        Menu parent = getService().selectById(po.getParentId());
        if (parent != null) {
            po.setParentIds(parent.getParentIds() + parent.getId() + ",");
        }else {
            po.setParentId(0);
            po.setParentIds(",0,");
        }
        if (po.getSort() == null) {
            // 获取
            Integer maxSort = getService().getMaxSort(po.getParentId());
            if (maxSort == null){
                po.setSort(0);
            }else {
                po.setSort(maxSort + 10);
            }

        }
        getService().insert(po);
        return created(po);
    }

    @PutMapping(value = "/{id}")
    public ResponseMessage update(@PathVariable("id") Integer id, Menu menu) {
        Menu old = getService().selectById(id);
        assertFound(old, "data is not found!");
        menu.setId(id);
        getService().saveMenu(menu, old.getParentIds());
        return ok();
    }
}
