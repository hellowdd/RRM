package com.bocom.service.api;

import com.bocom.util.ResponseVo;
import org.springframework.web.bind.annotation.RequestParam;

public interface AcService {

    public ResponseVo getTaskInfo(@RequestParam String cityCode, @RequestParam String taskId);
}
