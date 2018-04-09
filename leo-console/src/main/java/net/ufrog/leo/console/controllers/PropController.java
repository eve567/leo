package net.ufrog.leo.console.controllers;

import net.ufrog.common.Result;
import net.ufrog.common.app.App;
import net.ufrog.common.spring.interceptor.PropertiesInterceptor;
import net.ufrog.common.utils.Strings;
import net.ufrog.common.web.RequestParam;
import net.ufrog.leo.console.forms.TemplateForm;
import net.ufrog.leo.domain.models.Prop;
import net.ufrog.leo.service.PropService;
import net.ufrog.leo.service.beans.Props;
import net.ufrog.leo.service.storages.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性控制器
 *
 * @author ultrafrog, ufrog.net@gmail.com
 * @version 0.1, 2017-08-14
 * @since 0.1
 */
@Controller
@RequestMapping("/prop")
public class PropController {

    private static final String PROP_PREFIX     = "_p_";

    /** 系统属性业务接口 */
    private final PropService propService;

    /** 数据仓储 */
    private final Storage storage;

    /**
     * 构造函数
     *
     * @param propService 系统属性业务接口
     * @param storage 数据仓储
     */
    @Autowired
    public PropController(PropService propService, Storage storage) {
        this.propService = propService;
        this.storage = storage;
    }

    /**
     * 索引
     *
     * @return view for prop/index
     */
    @GetMapping({"", "/", "/index"})
    public String index() {
        return "prop/index";
    }

    /**
     * 配置视图
     *
     * @param view 视图名称
     * @return view for prop/prop_${view}
     */
    @GetMapping("/_{view}")
    public String propView(@PathVariable("view") String view) {
        return "prop/prop_" + view;
    }

    /**
     * 邮件模版视图
     *
     * @return view for prop/mail_template
     */
    @GetMapping("/mail_template")
    public String mailTemplateView() {
        return "prop/mail_template";
    }

    /**
     * 保存属性
     *
     * @return 保存结果
     */
    @PostMapping("/save")
    @ResponseBody
    public Result<List<Prop>> save() {
        List<Prop> lProp = new ArrayList<>();
        RequestParam.current().getParams().entrySet().parallelStream().filter(entry -> entry.getKey().startsWith(PROP_PREFIX)).forEach(entry -> {
            String code = entry.getKey().substring(PROP_PREFIX.length()).trim();
            String value = Strings.empty(entry.getValue()[0], null);
            lProp.add(propService.save(code, value == null ? null : value.trim()));
        });
        PropertiesInterceptor.clear();
        return Result.success(lProp, App.message("prop.save.success"));
    }

    /**
     * 读取邮件模版内容
     *
     * @param code 标识
     * @return 模版内容
     */
    @GetMapping("/mail_template_value/{code}")
    @ResponseBody
    public String getMailTemplateValue(@PathVariable("code") String code) {
        String key = App.config(code);
        if (Strings.empty(key)) {
            key = storage.put("<h1>hello world</h1>".getBytes(Props.getAppCharset()));
            propService.save(code, key);
            PropertiesInterceptor.clear();
        }
        return new String(storage.get(key), Props.getAppCharset());
    }

    /**
     * 保存邮件模版
     *
     * @param tpl 模版对象
     * @return 保存结果
     */
    @PutMapping("/mail_template")
    @ResponseBody
    public Result<?> saveMailTemplate(@RequestBody TemplateForm tpl) {
        String key = App.config(tpl.getCode());
        storage.update(key, tpl.getValue().getBytes(Props.getAppCharset()));
        return Result.success(App.message("mail.template.save.success"));
    }
}
