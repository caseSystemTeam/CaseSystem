package com.lawer.service.impl;



import com.lawer.mapper.CaseMapper;
import com.lawer.pojo.CaseFile;
import com.lawer.service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {

	@Autowired
	CaseMapper mapper;
	@Override
	public List<CaseFile> getFileAll(String caseId) {
		List<CaseFile> list = mapper.getFileAll(caseId);
		return list;
	}

	@Override
	public CaseFile getFileById(String fileid) {
		return null;
	}

	@Override
	public void deleteFileById(String fileid) {

	}
}
