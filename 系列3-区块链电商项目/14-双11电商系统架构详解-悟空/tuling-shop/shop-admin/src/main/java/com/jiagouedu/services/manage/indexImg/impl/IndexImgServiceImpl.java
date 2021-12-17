/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package com.jiagouedu.services.manage.indexImg.impl;

import com.jiagouedu.core.ServersManager;
import com.jiagouedu.services.manage.indexImg.IndexImgService;
import com.jiagouedu.services.manage.indexImg.bean.IndexImg;
import com.jiagouedu.services.manage.indexImg.dao.IndexImgDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author wukong 图灵学院 QQ:245553999
 */
@Service("indexImgServiceManage")
public class IndexImgServiceImpl extends ServersManager<IndexImg, IndexImgDao> implements
		IndexImgService {

    @Resource(name = "indexImgDaoManage")
    @Override
    public void setDao(IndexImgDao indexImgDao) {
        this.dao = indexImgDao;
    }

	@Override
	public List<IndexImg> getImgsShowToIndex(int i) {
		return dao.getImgsShowToIndex(i);
	}

}
