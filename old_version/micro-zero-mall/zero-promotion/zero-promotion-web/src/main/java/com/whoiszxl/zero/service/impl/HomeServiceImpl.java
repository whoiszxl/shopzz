package com.whoiszxl.zero.service.impl;

import com.whoiszxl.zero.bean.MyPage;
import com.whoiszxl.zero.dao.HomeBannerDao;
import com.whoiszxl.zero.dao.RecommendDao;
import com.whoiszxl.zero.entity.HomeBanner;
import com.whoiszxl.zero.entity.Recommend;
import com.whoiszxl.zero.entity.dto.HomeBannerDTO;
import com.whoiszxl.zero.entity.dto.RecommendDTO;
import com.whoiszxl.zero.entity.params.RecommendListParams;
import com.whoiszxl.zero.enums.BannerTypeEnum;
import com.whoiszxl.zero.service.HomeService;
import com.whoiszxl.zero.enums.StatusEnum;
import com.whoiszxl.zero.utils.BeanCopierUtils;
import com.whoiszxl.zero.utils.JpaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 主页服务实现
 *
 * @author whoiszxl
 * @date 2021/4/9
 */
@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private HomeBannerDao homeBannerDao;

    @Autowired
    private RecommendDao recommendDao;


    @Override
    public List<HomeBannerDTO> getBannerByType(BannerTypeEnum bannerType) {
        List<HomeBanner> appBanner = homeBannerDao.findAllByTypeAndStatusOrderBySortDesc(bannerType.getCode(), StatusEnum.OPEN.getCode());
        return BeanCopierUtils.copyListProperties(appBanner, HomeBannerDTO::new);
    }

    @Override
    public MyPage<Recommend> recommendList(RecommendListParams recommendListParams) {
        //按sort字段降序分页查询
        Sort sort = JpaUtils.getSort(-1, "sort");
        Pageable pageable = JpaUtils.getPageable(recommendListParams.getPageNumber(), recommendListParams.getPageSize(), sort);

        Specification<Recommend> spec = getSpecification(recommendListParams);
        Page<Recommend> productPage = recommendDao.findAll(spec, pageable);
        return JpaUtils.convertMyPage(productPage);
    }

    /**
     * 组装查询条件
     * @param recommendListParams 查询条件
     * @return
     */
    private Specification<Recommend> getSpecification(RecommendListParams recommendListParams) {
        Specification<Recommend> specification = (Specification<Recommend>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(recommendListParams.getRecommendType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("type"), recommendListParams.getRecommendType()));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };

        return specification;
    }

}
