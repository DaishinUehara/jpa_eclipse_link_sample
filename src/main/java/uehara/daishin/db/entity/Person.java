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

//@Data // Dataアノテーションだと循環参照をおこすのでやめておいかほうが良い(@ToStringと@EqualsAndHashCodeで(exclude={"jobHistoryList"})の指定が必要になる。)
@Setter
@Getter
@Entity
@NamedQueries({
	@NamedQuery(name = "findPerson.All", query = "SELECT p FROM Person p"),
	@NamedQuery(name = "findPerson.ByFirstName", query = "SELECT p FROM Person p WHERE p.firstName LIKE :fname")
})
public class Person {

	@Id
	@GeneratedValue
	private Integer id;
	private String firstName;

	// 楽観的排他制御に用いるバージョンフィールドの指定
	@Version
	private Integer version;

	// @Setter(lombok.AccessLevel.NONE)
	@OneToMany(mappedBy = "person", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<JobHistory> jobHistoryList = new ArrayList<JobHistory>();

	/*
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public List<JobHistory> getJobHistoryList() {
		return jobHistoryList;
	}

	public void setJobHistoryList(List<JobHistory> jobHistoryList) {
		this.jobHistoryList = jobHistoryList;
	}
	*/

}