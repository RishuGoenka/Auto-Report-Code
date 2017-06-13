import java.util.Date;

public class BugStatus {

	private String bugId;
	private String status;
	private String fixedIn;
	private Date date;
	private String remark;

	public BugStatus() {
		super();
	}

	public BugStatus(String bugId, String status, String fixedIn, Date date, String remark) {
		super();
		this.bugId = bugId;
		this.status = status;
		this.fixedIn = fixedIn;
		this.date = date;
		this.remark = remark;
	}

	public String getBugId() {
		return bugId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFixedIn() {
		return fixedIn;
	}

	public void setFixedIn(String fixedIn) {
		this.fixedIn = fixedIn;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bugId == null) ? 0 : bugId.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((fixedIn == null) ? 0 : fixedIn.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BugStatus other = (BugStatus) obj;
		if (bugId == null) {
			if (other.bugId != null)
				return false;
		} else if (!bugId.equals(other.bugId))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (fixedIn == null) {
			if (other.fixedIn != null)
				return false;
		} else if (!fixedIn.equals(other.fixedIn))
			return false;
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BugStatus [bugId=" + bugId + ", status=" + status + ", fixedIn=" + fixedIn + ", date=" + date
				+ ", remark=" + remark + "]";
	}

}
