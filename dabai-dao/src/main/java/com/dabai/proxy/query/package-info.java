/**
 * SELECT a.id, a.parent_user_id, a.`name`, a.id_card, a.mobile, a.bank_card, b.`code`,b.organization_code,
 * c.sign_status, f.`code` as parentCode, e.`name` as parentName,
 * e.mobile as parentMobile, a.ctime, c.utime as signUtime, d.original_identity_tag , d.current_identity_tag,
 * b.original_identity_tag as registerIdentityTag
 *  FROM user_info a LEFT JOIN user_plateform_info b ON a.id = b.user_id
 * LEFT JOIN user_sign_info c ON a.id = c.user_id
 * LEFT JOIN user_tag_change d ON a.id = d.user_id
 * LEFT JOIN user_info e ON a.parent_user_id = e.id
 * LEFT JOIN user_plateform_info f ON a.parent_user_id = f.user_id
 * WHERE
 * b.organization_code = '' AND
 * a.`name` = '' AND
 * a.mobile = '' AND
 * b.`code` = '' AND
 *
 * (a.id_card = '' OR c.sign_status != 1) AND
 * c.sign_status = 1 AND
 *
 * f.`code` = '' AND
 * e.`name` = '' AND
 * e.mobile = '' AND
 * a.ctime BETWEEN '' AND ''
 * a.id_card = '' AND
 *
 * (b.identity_tag = b.original_identity_tag OR b.identity_tag = 0) AND
 * d.original_identity_tag = 0 AND d.current_identity_tag = 1 AND b.identity_tag = 1 AND d.utime BETWEEN '' AND '' AND
 *
 * b.original_identity_tag = ''
 *
 * c.ctime BEGIN '' AND '' AND c.sign_status = 1
 * @author: jinshan.zhu
 * @date: 2022/5/4 11:37
 */
package com.dabai.proxy.query;