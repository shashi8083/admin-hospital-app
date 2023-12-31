package org.jsp.adminhospital.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.jsp.adminhospital.dto.Admin;
import org.jsp.adminhospital.dto.Hospital;

public class HospitalDao {
	private EntityManager manager = Persistence.createEntityManagerFactory("dev").createEntityManager();
//								  Hospital id
							   //Hospital name
							// Hospital founder
	public Hospital saveHospital(Hospital hospital, int admin_id) {
		Admin a = manager.find(Admin.class, admin_id);// admin details
		if (a != null) {
			a.getHospitals().add(hospital);//all details of  hospital has been added by admin i.e.,hospital column
			hospital.setAdmin(a);// for accessing admin_id-1
			manager.persist(hospital);//inserting the hospital details in hospital table
			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();
			transaction.commit();
			return hospital;
		}
		return null;
	}

	public Hospital updateHospital(Hospital hospital) {
		Hospital dbHospital = manager.find(Hospital.class, hospital.getId());
		if (dbHospital != null) {
			dbHospital.setFounder(hospital.getFounder());
			dbHospital.setName(hospital.getName());
			dbHospital.setGst_number(hospital.getGst_number());
			dbHospital.setYear_of_estb(hospital.getYear_of_estb());

			EntityTransaction transaction = manager.getTransaction();
			transaction.begin();
			transaction.commit();
			return dbHospital;
		}
		return null;
	}

	public List<Hospital> findHospitalByAdminId(int admin_id) {
		Query q = manager.createQuery("select a.hospitals from Admin a where a.id=?1");
		// select h from Hospital h where h.admin.id=?1
		q.setParameter(1, admin_id);
		return q.getResultList();
	}

	public List<Hospital> findHospital(long phone, String password) {
		Query q = manager.createQuery("select a.hospitals from Admin a where a.phone=?1 and a.password=?2");
		q.setParameter(1, phone);
		q.setParameter(2, password);
		return q.getResultList();
	}

	public List<Hospital> findHospital(String email, String password) {
		Query q = manager.createQuery("select a.hospitals from Admin a where a.email=?1 and a.password=?2");
		q.setParameter(1, email);
		q.setParameter(2, password);
		return q.getResultList();
	}
}
