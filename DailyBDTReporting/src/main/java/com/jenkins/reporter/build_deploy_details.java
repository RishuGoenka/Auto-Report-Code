
package com.jenkins.reporter;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "build_deploy_details")
public class build_deploy_details {
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

	@Column(name = "deploy")
	private String deploy;

	@Column(name = "destination_path")
	private String destination_path;

	@Column(name = "destination_server")
	private String destination_server;

	@Column(name = "created_dtm")
	private Date created_dtm;

	@Column(name = "user_name")
	private String user_name;

	public build_deploy_details() {
		super();
	}

	public build_deploy_details(int seq_id, String fullname, String job_name, String job_url, String job_type,
			String product_name, String deploy, String destination_path, String destination_server, Date created_dtm,
			String user_name) {
		super();
		this.seq_id = seq_id;
		this.fullname = fullname;
		this.job_name = job_name;
		this.job_url = job_url;
		this.job_type = job_type;
		this.product_name = product_name;
		this.deploy = deploy;
		this.destination_path = destination_path;
		this.destination_server = destination_server;
		this.created_dtm = created_dtm;
		this.user_name = user_name;
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

	public String getDeploy() {
		return deploy;
	}

	public void setDeploy(String deploy) {
		this.deploy = deploy;
	}

	public String getDestination_path() {
		return destination_path;
	}

	public void setDestination_path(String destination_path) {
		this.destination_path = destination_path;
	}

	public String getDestination_server() {
		return destination_server;
	}

	public void setDestination_server(String destination_server) {
		this.destination_server = destination_server;
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

	@Override
	public String toString() {
		return "build_deploy_details [seq_id=" + seq_id + ", fullname=" + fullname + ", job_name=" + job_name
				+ ", job_url=" + job_url + ", job_type=" + job_type + ", product_name=" + product_name + ", deploy="
				+ deploy + ", destination_path=" + destination_path + ", destination_server=" + destination_server
				+ ", created_dtm=" + created_dtm + ", user_name=" + user_name + "]";
	}

}
