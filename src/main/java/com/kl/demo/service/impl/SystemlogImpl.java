package com.kl.demo.service.impl;

import com.kl.demo.domain.Systemlog;
import com.kl.demo.repository.SystemlogRepo;
import com.kl.demo.service.SystemlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("systemlogService")
public class SystemlogImpl implements SystemlogService
{
    @Autowired
    private SystemlogRepo systemlogRepo;


    @Override
    public void save(Systemlog systemlog)
    {
        systemlogRepo.save(systemlog);
    }

    @Override
    public Page<Systemlog> queryDynamic(Map<String, Object> reqMap, Pageable pageable)
    {
        /**
         * 多条件动态查询，
         * 常用查询：equal(等于)，notEqual(不等于)， gt(大于)， ge(大于等于)，lt(小于)， le(小于等于)，between(闭区间中的值)，like(模糊)等
         */
        Specification querySpecifi=new Specification<Systemlog>()
        {
            @Override
            public Predicate toPredicate(Root<Systemlog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)
            {
                List<Predicate> predicates = new ArrayList<>();
                //条件设置部分，查询‘CriteriaBuilder’源码，查询的分类
                if(!reqMap.get("username").toString().equals(""))//账号，精确查询
                {
                    predicates.add(cb.equal(root.get("username"),reqMap.get("username").toString()));
                }
                if(!reqMap.get("operatetype").toString().equals("全部"))//操作类型，精确查询
                {
                    predicates.add(cb.equal(root.get("operatetype"),reqMap.get("operatetype").toString()));
                }
                if(!reqMap.get("operatedesc").toString().equals("全部"))//操作简述，模糊查询
                {
                    predicates.add(cb.like(root.get("operatedesc"),"%"+reqMap.get("operatedesc").toString()+"%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return this.systemlogRepo.findAll(querySpecifi,pageable);//这里利用的是JpaSpecificationExecuto接口的分页查询方法
    }
}
