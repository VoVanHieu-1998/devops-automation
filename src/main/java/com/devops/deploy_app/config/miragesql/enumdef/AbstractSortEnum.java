/*******************************************************************************
 * Class        ：AbstractSortEnum
 * Created date ：2023/01/06
 * Lasted date  ：2023/01/06
 * Author       ：TaiTM
 * Change log   ：2023/01/06：01-00 TaiTM create a new
******************************************************************************/
package com.devops.deploy_app.config.miragesql.enumdef;

/**
 * The Interface AbstractSortEnum.
 */
public interface AbstractSortEnum {

	/**
     * Gets the value.
     *
     * @return the value
     */
	String getValue();

	/**
     * Gets the value mapping.
     *
     * @return the value mapping
     */
	String getValueMapping();

	/**
     * Checks if is reverse.
     *
     * @return true, if is reverse
     */
	default boolean isReverse() {
		return false;
	}
}
