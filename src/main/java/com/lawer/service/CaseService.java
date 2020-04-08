package com.lawer.service;

import com.lawer.pojo.CaseFile;

import java.util.List;

public interface CaseService {

	List<CaseFile> getFileAll(String caseId);
	CaseFile getFileById(String fileid);
	void deleteFileById(String fileid);




}
