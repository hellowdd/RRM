package com.bocom.controller.rest;


import com.bocom.dto.OperationDto;
import com.bocom.service.OperationService;
import com.bocom.util.PageUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manager/operation")
public class OperationController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OperationService operationService;

    /**
     * 查询操作列表，上传下载，带分页
     * @param map
     * @return
     */
    @GetMapping("/queryOperation")
    public Map queryOperation(String operationType) {
        PageInfo pageInfo = null;
        Map<String,Object> map=new HashMap();
        try {
            map.put("operationType",operationType);
            logger.info("queryOperation==============================>" + operationType);
            PageUtil.setParams(request, map);
            List<OperationDto> list = operationService.queryOperation(map);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            logger.info("queryResource==============> 出错" + e);
        }
        return PageUtil.covertMap(new Object[]{"page"},
                new Object[]{pageInfo});
    }

}
