package com.poseidon.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

//파일 저장하는 클래스
@Component
public class FileSave {
	//Spring에서 제공하는 fileCopyUtil을 이용해서 파일 저장하기
	public String save(String realPath, MultipartFile files) throws IOException {
		File file = new File(realPath);
		if(!file.exists()) {//경로가 있는지 질의
			file.mkdirs();//부모 폴더까지 생성하기
		}
		//UUID 유니크  a.png   a.png
		String fileName = UUID.randomUUID().toString();
		fileName = fileName + "_" + files.getOriginalFilename();
		//           d4fe-fefe-fwae-afee_a.png
		//System.out.println("만들어진 fileName : " + fileName);
		
		//파일 올리기
		file = new File(file, fileName);
		FileCopyUtils.copy(files.getBytes(), file);
		
		return fileName;
	}
	
	
	//multipart에서 제공하는 업로드로 저장하기
	public String save2(String realPath, MultipartFile files) throws IllegalStateException, IOException {
		File file = new File(realPath);
		if(!file.exists()) {
			file.mkdirs();
		}
		String fileName = UUID.randomUUID().toString();
		fileName = fileName + "_" + files.getOriginalFilename();
		file = new File(file, fileName);
		files.transferTo(file);
		return fileName;
	}
	
	
	
	
	
	
	
	
	
}
