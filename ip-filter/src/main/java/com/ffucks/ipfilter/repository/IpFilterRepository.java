package com.ffucks.ipfilter.repository;

import com.ffucks.ipfilter.models.IpFilterModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IpFilterRepository extends JpaRepository<IpFilterModel, Long> {

    IpFilterModel findBySourceIpAndDestinationIp(String sourceIp, String destinationIp);

    //void deleteByIp(String ip);

    void deleteBySourceIpAndDestinationIp(String sourceIp, String destinationIp);

}
