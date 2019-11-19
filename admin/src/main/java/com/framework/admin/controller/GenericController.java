package com.framework.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.framework.admin.editor.DateEditor;
import com.framework.admin.editor.StringEditor;
import com.framework.core.GenericPo;
import com.framework.core.exception.BusinessException;
import com.framework.core.exception.NotFoundException;
import com.framework.core.message.ResponseMessage;
import com.framework.core.po.DataTablesPo;
import com.framework.core.utils.ClassUtils;
import com.framework.core.utils.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.framework.core.message.ResponseMessage.created;
import static com.framework.core.message.ResponseMessage.ok;


/**
 * 通用控制器,此控制器实现了通用的增删改查功能
 * 需要提供一个实现了{@link IService}接口的实现类
 *
 * @author sunhan
 * @since 2017年02月15日
 */
public abstract class GenericController<PO,PK extends Serializable> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpSession session;

    @Autowired
    protected ServletContext application;

    /**
     * 获取此Controller需要的服务类,由子类实现
     *
     * @return 通用服务类
     */
    protected abstract IService<PO> getService();

    /**
     * 获取PO的类型
     *
     * @return PO类型
     */
    protected final Class<PO> getPOType() {
        return (Class<PO>) ClassUtils.getGenericType(this.getClass(), 0);
    }

    /**
     * 查询列表,并返回查询结果
     */
    /**
     * 查询列表,并返回查询结果
     */
    @PostMapping(value = "page")
    @PreAuthorize("hasAuthority('view')")
    public DataTablesPo<PO> page(PO po) {
        Page<PO> page = getService().selectPage( getPage(), new EntityWrapper<>(po));
        return getTablesData(page);
    }
    /**
     * 根据id（主键）查询数据
     *
     * @param id 主键
     * @return 请求结果
     * @throws NotFoundException 要查询的数据不存在
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('view')")
    public ResponseMessage info(@PathVariable("id") PK id) {
        PO po = getService().selectById(id);
        if (po == null)
            throw new NotFoundException("data is not found!");
        return ok(po);
    }


    /**
     * 根据查询条件，查询数据数量
     *
     * @param po 查询条件
     * @return 请求结果
     */
    @GetMapping(value = "/total")
    @PreAuthorize("hasAuthority('view')")
    public ResponseMessage total(PO po) {
        // 获取条件查询
        EntityWrapper<PO> wrapper = new EntityWrapper<>(po);
        return ok(getService().selectCount(wrapper));
    }

    /**
     * 请求添加数据，请求必须以POST方式
     *
     * @param po 请求添加的对象
     * @return 被添加数据的主键值
     * @throws javax.validation.ValidationException 验证数据格式错误
     */
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('edit')")
    public ResponseMessage add(PO po) {
        getService().insert(po);
        return created(po);
    }

    /**
     * 请求删除指定id的数据，请求方式为DELETE，使用rest风格，如请求 /delete/1 ，将删除id为1的数据
     *
     * @param id 要删除的id标识
     * @return 删除结果
     * @throws NotFoundException 要删除的数据不存在
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('edit')")
    public ResponseMessage delete(@PathVariable("id") PK id) {
        PO old = getService().selectById(id);
        assertFound(old, "要删除的数据不存在!");
        getService().deleteById(id);
        return ok();
    }

    @PostMapping(value = "/batchDelete")
    @PreAuthorize("hasAuthority('edit')")
    public ResponseMessage batchDelete(String ids) {
        getService().deleteBatchIds(Arrays.asList(ids.split(",")));
        return ok();
    }

    /**
     * 根据主键修改数据
     *
     * @param id     要修改数据的主键值
     * @param object 要修改的数据
     * @return 请求结果
     * @throws NotFoundException 要修改的数据不存在
     */
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('edit')")
    public ResponseMessage update(@PathVariable("id") Integer id, PO object) {
        PO old = getService().selectById(id);
        assertFound(old, "要修改的数据不存在!");
        if (object instanceof GenericPo) {
            ((GenericPo) object).setId(id);
        }
        Boolean b = getService().updateById(object);
        return ok(b);
    }

    /**
     * 批量修改数据.
     *
     * @param json 请求修改的数据 json格式
     * @return 被修改数据的条数
     * @throws BusinessException 请求的数据格式错误
     */
    @PutMapping()
    @PreAuthorize("hasAuthority('edit')")
    public ResponseMessage update(String json) {
        Boolean success;
        if (json.startsWith("[")) {
            success = getService().updateBatchById(JSON.parseArray(json, getPOType()));
        } else if (json.startsWith("{")) {
            success = getService().updateBatchById(Arrays.asList(JSON.parseObject(json, getPOType())));
        } else {
            throw new BusinessException("请求数据格式错误!");
        }
        return ok(success);
    }




    /**
     * 判断对象是否为空,如果为空将抛出 {@link NotFoundException}
     *
     * @param obj 要判断的对象
     * @param msg 为null时异常消息
     */
    public void assertFound(Object obj, String msg) {
        if (obj == null) throw new NotFoundException(msg);
    }

    /**
     * <p>
     * 转换为 bootstrap-table 需要的分页格式 JSON
     * </p>
     *
     * @param page 分页对象
     * @return
     */
    protected DataTablesPo<PO> getTablesData(Page<PO> page) {
        DataTablesPo<PO> bo = new DataTablesPo<>();
        bo.setData(page.getRecords());
        bo.setDraw(request.getParameter("draw"));
        bo.setRecordsTotal(page.getTotal());
        bo.setRecordsFiltered(page.getTotal());
        return bo;
    }


    /**
     * <p>
     * 转换为 bootstrap-table 需要的分页格式 JSON
     * </p>
     *
     * @param data 分页对象
     * @return
     */
    protected DataTablesPo<PO> getTablesDataUnPage(List<PO> data) {
        DataTablesPo<PO> bo = new DataTablesPo<>();
        bo.setData(data);
        bo.setDraw(request.getParameter("draw"));
        bo.setRecordsTotal(data.size());
        bo.setRecordsFiltered(data.size());
        return bo;
    }

    /**
     * <p>
     * 获取分页对象
     * </p>
     *
     * @return
     */
    protected <T> Page<T> getPage() {
        int start = 0;
        int length = 10;
        if (request.getParameter(Const.LENGTH) != null) {
            start = Integer.parseInt(request.getParameter(Const.START));
        }
        if (request.getParameter(Const.LENGTH) != null) {
            length = Integer.parseInt(request.getParameter(Const.LENGTH));
        }
        Page<T> page = new Page<>(start / length + 1, length);
        page.setOrderByField("id");
        page.setAsc(false);
        return page;
    }
    protected <T> EntityWrapper<T> getEntityWrapper() {
        EntityWrapper<T> ew = new EntityWrapper<>();
        ew.where("del_flag={0}", Const.DEL_FLAG_NORMAL);
        return ew;
    }

    /**
     * 获取当前登录者对象
     *
     * @param <U> the type parameter
     * @return the current user
     */
    @SuppressWarnings("unchecked")
    public static <U extends UserDetails> U getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (U) authentication.getPrincipal();
    }


    /**
     * 初始化数据绑定
     * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
     * 2. 将字段中Date类型转换为String类型
     *
     * @param binder the binder
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new StringEditor());
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new DateEditor());
    }
}
