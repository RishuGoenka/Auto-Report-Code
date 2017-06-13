package com.jenkins.reporter;

import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jenkins_build_details")
public class jenkins_build_details {
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "bigint")
	private BigInteger bigint;

	@Column(name = "enddate")
	private Date enddate;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "name")
	private String name;

	@Column(name = "result")
	private String result;

	@Column(name = "startdate")
	private Date startdate;

	@Column(name = "userid")
	private String userid;

	@Column(name = "username")
	private String username;

	@Column(name = "node_url")
	private String node_url;

	public jenkins_build_details() {
		super();
	}

	public jenkins_build_details(String id, BigInteger bigint, Date enddate, String fullname, String name,
			String result, Date startdate, String userid, String username, String node_url) {
		super();
		this.id = id;
		this.bigint = bigint;
		this.enddate = enddate;
		this.fullname = fullname;
		this.name = name;
		this.result = result;
		this.startdate = startdate;
		this.userid = userid;
		this.username = username;
		this.node_url = node_url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getBigint() {
		return bigint;
	}

	public void setBigint(BigInteger bigint) {
		this.bigint = bigint;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNode_url() {
		return node_url;
	}

	public void setNode_url(String node_url) {
		this.node_url = node_url;
	}

	@Override
	public String toString() {
		return "jenkins_build_details [id=" + id + ", bigint=" + bigint + ", enddate=" + enddate + ", fullname="
				+ fullname + ", name=" + name + ", result=" + result + ", startdate=" + startdate + ", userid=" + userid
				+ ", username=" + username + ", node_url=" + node_url + "]";
	}

}
