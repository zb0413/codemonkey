package com.codemonkey.service;

import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.codemonkey.domain.UploadEntry;


public interface UploadEntryService extends GenericService<UploadEntry> {

	UploadEntry doSaveFile(HttpSession session , MultipartFile file);
	
	UploadEntry doSaveFile(HttpSession session , MultipartFile file ,String desc);
	
}
