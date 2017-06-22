package com.jenkins.reporter;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "build_run_details")
public class build_run_details {
	@Id
	@Column(name = "seq_id")
	private int seq_id;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "job_name")
	private String job_name;

	@Column(name = "job_url")
	private String job_url;

	@Column(name = "job_type")
	private String job_type;

	@Column(name = "product_name")
	private String product_name;

	@Column(name = "release_ver")
	private String release_ver;

	@Column(name = "patch_ver")
	private String patch_ver;

	@Column(name = "utility_ver")
	private String utility_ver;

	@Column(name = "cscr_ver")
	private String cscr_ver;

	@Column(name = "created_dtm")
	private Date created_dtm;

	@Column(name = "user_name")
	private String user_name;

	@Column(name = "repo_url")
	private String repo_url;

	@Column(name = "branch")
	private String branch;

	public build_run_details() {
		super();
	}

	public build_run_details(int seq_id, String fullname, String job_name, String job_url, String job_type,
			String product_name, String release_ver, String patch_ver, String utility_ver, String cscr_ver,
			Date created_dtm, String user_name, String repo_url, String branch) {
		super();
		this.seq_id = seq_id;
		this.fullname = fullname;
		this.job_name = job_name;
		this.job_url = job_url;
		this.job_type = job_type;
		this.product_name = product_name;
		this.release_ver = release_ver;
		this.patch_ver = patch_ver;
		this.utility_ver = utility_ver;
		this.cscr_ver = cscr_ver;
		this.created_dtm = created_dtm;
		this.user_name = user_name;
		this.repo_url = repo_url;
		this.branch = branch;
	}

	public int getSeq_id() {
		return seq_id;
	}

	public void setSeq_id(int seq_id) {
		this.seq_id = seq_id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public String getJob_url() {
		return job_url;
	}

	public void setJob_url(String job_url) {
		this.job_url = job_url;
	}

	public String getJob_type() {
		return job_type;
	}

	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getRelease_ver() {
		return release_ver;
	}

	public void setRelease_ver(String release_ver) {
		this.release_ver = release_ver;
	}

	public String getPatch_ver() {
		return patch_ver;
	}

	public void setPatch_ver(String patch_ver) {
		this.patch_ver = patch_ver;
	}

	public String getUtility_ver() {
		return utility_ver;
	}

	public void setUtility_ver(String utility_ver) {
		this.utility_ver = utility_ver;
	}

	public String getCscr_ver() {
		return cscr_ver;
	}

	public void setCscr_ver(String cscr_ver) {
		this.cscr_ver = cscr_ver;
	}

	public Date getCreated_dtm() {
		return created_dtm;
	}

	public void setCreated_dtm(Date created_dtm) {
		this.created_dtm = created_dtm;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getRepo_url() {
		return repo_url;
	}

	public void setRepo_url(String repo_url) {
		this.repo_url = repo_url;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "build_run_details [seq_id=" + seq_id + ", fullname=" + fullname + ", job_name=" + job_name
				+ ", job_url=" + job_url + ", job_type=" + job_type + ", product_name=" + product_name
				+ ", release_ver=" + release_ver + ", patch_ver=" + patch_ver + ", utility_ver=" + utility_ver
				+ ", cscr_ver=" + cscr_ver + ", created_dtm=" + created_dtm + ", user_name=" + user_name + ", repo_url="
				+ repo_url + ", branch=" + branch + "]";
	}

}
