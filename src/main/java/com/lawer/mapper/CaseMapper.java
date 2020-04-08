package com.lawer.mapper;

import com.lawer.pojo.CaseFile;

import java.util.List;


public interface CaseMapper {

	List<CaseFile> getFileAll(String caseId);
	CaseFile getFileById(String fileid);
	void deleteFileById(String fileid);
}
