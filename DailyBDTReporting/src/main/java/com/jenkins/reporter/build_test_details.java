package com.jenkins.reporter;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "build_test_details")
public class build_test_details {
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

	@Column(name = "total_tc")
	private String total_tc;

	@Column(name = "passed_tc")
	private String passed_tc;

	@Column(name = "failed_tc")
	private String failed_tc;

	@Column(name = "skipped_tc")
	private String skipped_tc;

	@Column(name = "created_dtm")
	private Date created_dtm;

	@Column(name = "modified_dtm")
	private Date modified_dtm;

	@Column(name = "user_name")
	private String user_name;

	@Column(name = "testing_type")
	private String testing_type;

	public build_test_details() {
		super();
	}

	public build_test_details(int seq_id, String fullname, String job_name, String job_url, String job_type,
			String product_name, String total_tc, String passed_tc, String failed_tc, String skipped_tc,
			Date created_dtm, Date modified_dtm, String user_name, String testing_type) {
		super();
		this.seq_id = seq_id;
		this.fullname = fullname;
		this.job_name = job_name;
		this.job_url = job_url;
		this.job_type = job_type;
		this.product_name = product_name;
		this.total_tc = total_tc;
		this.passed_tc = passed_tc;
		this.failed_tc = failed_tc;
		this.skipped_tc = skipped_tc;
		this.created_dtm = created_dtm;
		this.modified_dtm = modified_dtm;
		this.user_name = user_name;
		this.testing_type = testing_type;
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

	public String getTotal_tc() {
		return total_tc;
	}

	public void setTotal_tc(String total_tc) {
		this.total_tc = total_tc;
	}

	public String getPassed_tc() {
		return passed_tc;
	}

	public void setPassed_tc(String passed_tc) {
		this.passed_tc = passed_tc;
	}

	public String getFailed_tc() {
		return failed_tc;
	}

	public void setFailed_tc(String failed_tc) {
		this.failed_tc = failed_tc;
	}

	public String getSkipped_tc() {
		return skipped_tc;
	}

	public void setSkipped_tc(String skipped_tc) {
		this.skipped_tc = skipped_tc;
	}

	public Date getCreated_dtm() {
		return created_dtm;
	}

	public void setCreated_dtm(Date created_dtm) {
		this.created_dtm = created_dtm;
	}

	public Date getModified_dtm() {
		return modified_dtm;
	}

	public void setModified_dtm(Date modified_dtm) {
		this.modified_dtm = modified_dtm;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getTesting_type() {
		return testing_type;
	}

	public void setTesting_type(String testing_type) {
		this.testing_type = testing_type;
	}

	@Override
	public String toString() {
		return "build_test_details [seq_id=" + seq_id + ", fullname=" + fullname + ", job_name=" + job_name
				+ ", job_url=" + job_url + ", job_type=" + job_type + ", product_name=" + product_name + ", total_tc="
				+ total_tc + ", passed_tc=" + passed_tc + ", failed_tc=" + failed_tc + ", skipped_tc=" + skipped_tc
				+ ", created_dtm=" + created_dtm + ", modified_dtm=" + modified_dtm + ", user_name=" + user_name
				+ ", testing_type=" + testing_type + "]";
	}

}
