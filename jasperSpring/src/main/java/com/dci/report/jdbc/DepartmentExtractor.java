package com.dci.report.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dci.report.bean.Client;
import com.dci.report.bean.Department;

public class DepartmentExtractor implements ResultSetExtractor {
	@Override
	public List<Client> extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {

		int ctr = 0;
		int clientctr = 0;
		Client dciclient = new Client();
		Department department = new Department();
		List<Client> clientcollection = new ArrayList<Client>();
		List<Department> deptcollection = new ArrayList<Department>();
		while (resultSet.next()) {

			if (ctr == 0) {

				dciclient.setClientname(resultSet.getString("FCLIENTNAME"));
				department.setDepartmentName(resultSet.getString("FDEPTNAME"));
				department.setDepartmentID(resultSet.getInt("FDEPTID"));
				deptcollection.add(department);
				ctr++;

			} else {
				if (resultSet.getString("FCLIENTNAME").equals(
						dciclient.getClientname())) {

					department = new Department();
					department.setDepartmentName(resultSet
							.getString("FDEPTNAME"));
					department.setDepartmentID(resultSet.getInt("FDEPTID"));
					deptcollection.add(department);
					ctr++;

				} else

				{
					dciclient.setDepartments(deptcollection);
					clientcollection.add(clientctr, dciclient);
					clientctr++;
					dciclient = new Client();
					deptcollection = new ArrayList<Department>();
					department = new Department();

					dciclient.setClientname(resultSet.getString("FCLIENTNAME"));
					department.setDepartmentName(resultSet
							.getString("FDEPTNAME"));
					department.setDepartmentID(resultSet.getInt("FDEPTID"));
					deptcollection.add(department);
					ctr++;
				}
			}
		}

		dciclient.setDepartments(deptcollection);
		clientcollection.add(clientctr, dciclient);

		return clientcollection;

	}
}
