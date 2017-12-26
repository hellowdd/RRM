package com.bocom.service;

import com.bocom.domain.ResourceInfo;
import com.bocom.util.ResponseVo;
import org.springframework.web.multipart.MultipartFile;

public interface TextServcie {

    ResponseVo uploadTextFile(MultipartFile file, ResourceInfo resourceInfo);
}
