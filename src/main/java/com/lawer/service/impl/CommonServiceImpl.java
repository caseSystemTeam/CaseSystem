package com.lawer.service.impl;

import java.util.List;

import com.lawer.mapper.CommonMapper;
import com.lawer.pojo.CaseFile;
import com.lawer.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommonServiceImpl implements CommonService {
	
	@Autowired
	private CommonMapper commonMapper;

	@Override
	public void addFile(CaseFile file) {
		commonMapper.addFile(file);
		
	}

		//这个还没写了
	@Override
	public void deleteFileUrlByFileName(String filename) {
		commonMapper.deleteFileUrlByFileName(filename);
		
	}
	
}
