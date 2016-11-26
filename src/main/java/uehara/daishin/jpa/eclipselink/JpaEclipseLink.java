package uehara.daishin.jpa.eclipselink;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import lombok.val;
import uehara.daishin.db.entity.Job;
import uehara.daishin.db.entity.JobHistory;
import uehara.daishin.db.entity.Person;

public class JpaEclipseLink {

	public static void main(String[] args) {
		insertPerformanceCheck(500);
	}

	/**
	 * インサートパフォーマンスチェック
	 * @param recordCount インサートレコードパラメータ
	 */
	private static void insertPerformanceCheck(int recordCount){


		val emf = Persistence.createEntityManagerFactory("jpaEclipseLink");
		val em = emf.createEntityManager();

		// ▼インサートデータ生成
		System.out.println("インサートデータ生成開始");
		List<Person> pl = new ArrayList<Person>();
		List<Job> jl = new ArrayList<Job>();
		List<JobHistory> jhl1=new ArrayList<JobHistory>();
		for(int i=0; i< recordCount; i++){
			List<JobHistory> jhl0 = new ArrayList<JobHistory>();
			// マッピングから作成
			val j =job("JobName"+i, jhl0);
			val p=person("PersonName"+i, jhl0);
			jl.add(j);
			pl.add(p);
			val jh = jobHistory(j,p);
			// エンティティ同士の相互参照
			jhl1.add(jh);
		}
		System.out.println("インサートデータ生成完了");
		// ▲インサートデータ生成


		// ▼データインサート
		val tx=em.getTransaction();
		try {
			tx.begin();
			System.out.println("トランザクション開始");
			for(val p: pl){
				em.persist(p);
			}
			for(val j: jl){
				em.persist(j);
			}
			for(val jh: jhl1){
				em.persist(jh);
			}
			tx.commit();
			System.out.println("トランザクションコミット");
		} catch (Exception e) {
			e.printStackTrace();
			if(tx!=null && tx.isActive()){
				tx.rollback();
			}
			System.out.println("トランザクションロールバック");
		}
		// ▲データインサート

		em.close();
		emf.close();


	}

	private static Person person(String firstName, List<JobHistory> jobHistoryList) {
		val p = new Person();
		p.setFirstName(firstName);
		p.setJobHistoryList(jobHistoryList);
		return p;
	}

	private static Job job(String jobName, List<JobHistory> jobHistoryList) {
		val j = new Job();
		j.setJobName(jobName);
		j.setJobHistoryList(jobHistoryList);
		return j;
	}

	private static JobHistory jobHistory(Job job, Person person) {
		val jh = new JobHistory();
		jh.setJob(job);
		jh.setPerson(person);
		return jh;
	}

}
