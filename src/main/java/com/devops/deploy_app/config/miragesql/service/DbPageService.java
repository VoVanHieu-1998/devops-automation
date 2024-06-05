/*******************************************************************************
 * Class        ：AbstractCommonService
 * Created date ：2020/12/09
 * Lasted date  ：2020/12/09
 * Author       ：taitt
 * Change log   ：2020/12/09：01-00 taitt create a new
 ******************************************************************************/
package com.devops.deploy_app.config.miragesql.service;

import com.devops.deploy_app.config.miragesql.enumdef.AbstractSortEnum;
import com.devops.deploy_app.config.miragesql.utils.DbColumnUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * AbstractCommonService
 * 
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public interface DbPageService {

    public default Pageable buildPageable(Pageable pageable, Class<?> clazz, String alias) throws Exception {
        Sort sort = DbColumnUtils.buildSortAlias(pageable.getSort(), clazz, alias);
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
    }

    public default <T extends AbstractSortEnum> Pageable buildPageableByEnums(Pageable pageable, Class<?> clazz,
                                                                              String alias, T[] enumsDatas) throws Exception {
        Sort sortForEumns = DbColumnUtils.buildSortEnums(pageable.getSort(), enumsDatas, clazz, alias);

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortForEumns);
    }
}
