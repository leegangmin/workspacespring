package com.poseidon.web;

/*
 * 바꿨음. 2021-08-27 sbboardview내용으로 변경함.
 * sb_del은 삭제 유무이기때문에 삭제된 것은 여기 안 나오게 하겠습니다.
 */
public class TestDTO {
	private int sb_no, sm_no, sb_count, sb_cate;
	private String sb_title, sb_content, sb_date, 
					sb_file, sm_id, sm_name, sc_category;
	public int getSb_no() {
		return sb_no;
	}
	public void setSb_no(int sb_no) {
		this.sb_no = sb_no;
	}
	public int getSm_no() {
		return sm_no;
	}
	public void setSm_no(int sm_no) {
		this.sm_no = sm_no;
	}
	public int getSb_count() {
		return sb_count;
	}
	public void setSb_count(int sb_count) {
		this.sb_count = sb_count;
	}
	public int getSb_cate() {
		return sb_cate;
	}
	public void setSb_cate(int sb_cate) {
		this.sb_cate = sb_cate;
	}
	public String getSb_title() {
		return sb_title;
	}
	public void setSb_title(String sb_title) {
		this.sb_title = sb_title;
	}
	public String getSb_content() {
		return sb_content;
	}
	public void setSb_content(String sb_content) {
		this.sb_content = sb_content;
	}
	public String getSb_date() {
		return sb_date;
	}
	public void setSb_date(String sb_date) {
		this.sb_date = sb_date;
	}
	public String getSb_file() {
		return sb_file;
	}
	public void setSb_file(String sb_file) {
		this.sb_file = sb_file;
	}
	public String getSm_id() {
		return sm_id;
	}
	public void setSm_id(String sm_id) {
		this.sm_id = sm_id;
	}
	public String getSm_name() {
		return sm_name;
	}
	public void setSm_name(String sm_name) {
		this.sm_name = sm_name;
	}
	public String getSc_category() {
		return sc_category;
	}
	public void setSc_category(String sc_category) {
		this.sc_category = sc_category;
	}

	
}
