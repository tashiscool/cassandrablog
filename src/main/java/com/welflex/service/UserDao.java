package com.welflex.service;

import java.util.HashMap;
import java.util.Map;

import io.cassandra.sdk.StatusMessageModel;
import io.cassandra.sdk.column.ColumnAPI;
import io.cassandra.sdk.columnfamily.ColumnFamilyAPI;
import io.cassandra.sdk.constants.APIConstants;
import io.cassandra.sdk.data.DataAPI;
import io.cassandra.sdk.data.DataMapModel;
import io.cassandra.sdk.exception.CassandraIoException;

import org.lacassandra.smooshyfaces.entity.User;

import com.welflex.util.CassandraSetUp;

public class UserDao {
	private DataAPI dataAPI;
	private String keyspaceName;
	private String columnFamilyName;
	
	public String getKeyspaceName() {
		return keyspaceName;
	}

	public void setKeyspaceName(String keyspaceName) {
		this.keyspaceName = keyspaceName;
	}

	public String getColumnFamilyName() {
		return columnFamilyName;
	}

	public void setColumnFamilyName(String columnFamilyName) {
		this.columnFamilyName = columnFamilyName;
	}

	public DataAPI getDataAPI() {
		return dataAPI;
	}

	public void setDataAPI(DataAPI dataAPI) {
		this.dataAPI = dataAPI;
	}

	public void saveUser(User user) throws CassandraIoException
	{
		StatusMessageModel sm = dataAPI.postData(keyspaceName, columnFamilyName, user.getPiId(), convert(user), 12000);
		System.out.println(sm.getMessage() + " | " + sm.getDetail() + " | "
				+ sm.getError());
	}
	
	public User getUser(String id) throws CassandraIoException
	{
		DataMapModel data = dataAPI.getData(keyspaceName, columnFamilyName, id,0, null);
		System.out.println(data.toString());			
		User user = convert(data, id);
		return user;
	}

	private User convert(DataMapModel data, String id) {
		User user = new User(id, data.get(CassandraSetUp.CF_USERS_COL1),data.get(CassandraSetUp.CF_USERS_COL2),data.get(CassandraSetUp.CF_USERS_COL3));
		return user;
	}

	private Map<String, String> convert(User user) {
		Map<String, String> userMap = new HashMap<String, String>();
		userMap.put(CassandraSetUp.CF_USERS_COL1, user.getBookLists());
		userMap.put(CassandraSetUp.CF_USERS_COL2, user.getPrefs());
		userMap.put(CassandraSetUp.CF_USERS_COL3, user.getNonPrefs());
		return userMap;
	}
}
