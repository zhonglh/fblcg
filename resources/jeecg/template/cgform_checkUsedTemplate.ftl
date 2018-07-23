/*
 * Copyright (c) 2016. www.fullbloom.com Inc. All rights reserved.
 */

package com.fullbloom.interfaces.checkused.${prodName};

import com.fullbloom.interfaces.checkused.IBaseUseService;

/**   
 * @Title: CheckUsed , 检查${ftl_description}  是否使用 的接口  
 * @Description: ${ftl_description}
 * @author onlineGenerator
 * @date ${ftl_create_time}
 * @version V1.0
 */
public interface I${entityName}CheckUsed {


    /**
     * 检查是否使用
     * @param Id
     * @return
     */
    public String ${entityName?uncap_first}CheckUsed(String Id);

    /**
     * 根据需要，自动删除使用该对象的数据
     * @param id
     */
    public void ${entityName?uncap_first}AutoDeleteById(String id);


}