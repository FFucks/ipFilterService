package com.ffucks.ipfilter.services;

import com.ffucks.ipfilter.dto.IpFilterDto;
import com.ffucks.ipfilter.models.IpFilterModel;
import com.ffucks.ipfilter.repository.IpFilterRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IpFilterService {

    @Autowired
    IpFilterRepository ipFilterRepository;

    public boolean verifyIp(String ip) {
        String[] ipList = ip.split("\\.");

        if (ipList.length != 4) {
            return false;
        }

        for (String i : ipList) {
            if (i.length() == 0 || i.length() > 3) {
                return false;
            }
            if (Integer.parseInt(i) < 0 || Integer.parseInt(i) > 255) {
                return false;
            }
        }
        return true;
    }

    public IpFilterModel converter(IpFilterDto ipFilterDto) {
        return new IpFilterModel(ipFilterDto.getName(), ipFilterDto.getSourceIp(), ipFilterDto.getDestinationIp(), ipFilterDto.getRule());
    }

    public IpFilterModel findByIp(String sourceIp, String destinationIp) {
        return ipFilterRepository.findBySourceIpAndDestinationIp(sourceIp, destinationIp);
    }

    public List<IpFilterModel> getAllFilters() {
        return ipFilterRepository.findAll();
    }

    @Transactional
    public IpFilterModel saveIpFilter(IpFilterModel ipFilterModel) {
        IpFilterModel model = this.findByIp(ipFilterModel.getSourceIp(), ipFilterModel.getDestinationIp());
        if (model != null) {
            model.setName(ipFilterModel.getName());
            model.setSourceIp(ipFilterModel.getSourceIp());
            model.setDestinationIp(ipFilterModel.getDestinationIp());
            model.setRule(ipFilterModel.getRule());
            return ipFilterRepository.save(model);
        }
        return ipFilterRepository.save(ipFilterModel);
    }

    @Transactional
    public void delete(String sourceIp, String destinationIp) {
        ipFilterRepository.deleteBySourceIpAndDestinationIp(sourceIp, destinationIp);
    }
}
