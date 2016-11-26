package uehara.daishin.db.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

/**
 * Jobエンティティ
 *
 * @author d-uehara
 *
 */
//@Data // Dataアノテーションだと循環参照をおこすのでやめておいかほうが良い(@ToStringと@EqualsAndHashCodeで(exclude={"jobHistoryList"})の指定が必要になる。)
@Getter
@Setter
@Entity
@NamedQueries({ @NamedQuery(name = "findAll.Job", query = "Select j from Job j") })
public class Job {
	@Id
	@GeneratedValue
	private Integer id;
	private String jobName;

	// 楽観的排他制御に用いるバージョンフィールドの指定
	@Version
	private Integer version;

	// @Setter(lombok.AccessLevel.NONE)
	@OneToMany(mappedBy = "job", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<JobHistory> jobHistoryList = new ArrayList<JobHistory>();

/*
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public List<JobHistory> getJobHistoryList() {
		return jobHistoryList;
	}

	public void setJobHistoryList(List<JobHistory> jobHistoryList) {
		this.jobHistoryList = jobHistoryList;
	}

*/
}
